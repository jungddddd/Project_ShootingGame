import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;



public class GameFrame extends JFrame implements KeyListener, Runnable{
	public JLabel text = new JLabel();  

	private ImageIcon userImage = new ImageIcon("image/sangsangBoogie.png");
	private ImageIcon attackItem = new ImageIcon("image/attack.png");
	private ImageIcon enemyIcon =  new ImageIcon("image/alien1.png");
	private ImageIcon enemy2Icon = new ImageIcon("image/alien2.png");
	private ImageIcon lifeIcon =  new ImageIcon("image/lifeheart.png");
	private ImageIcon backIcon = new ImageIcon("image/galaxy.png");
	private ImageIcon failIcon = new ImageIcon("image/failboogie.png");
	private ImageIcon warningIcon = new ImageIcon("image/warning.png");
	private ImageIcon successIcon = new ImageIcon("image/success.png");
	
	private int x; 
	private int y; 
	
	private Image user = userImage.getImage();
	private Image enemyImg = enemyIcon.getImage();
	private Image enemy2Img = enemy2Icon.getImage();
	
	private ArrayList attack_List = new ArrayList();
	private ArrayList enemy_List = new ArrayList();
	private ArrayList enemy2_List = new ArrayList();
	private ArrayList life_List = new ArrayList();
	private ArrayList fireball_List = new ArrayList();
	
	private Image attackImg = attackItem.getImage();
	private Image lifeImg = lifeIcon.getImage();
	private Image backImg = backIcon.getImage();
	private Image failImg = failIcon.getImage();
	private Image warningImg = Toolkit.getDefaultToolkit().createImage("image/warning.gif");  
	private Image fireballImg = Toolkit.getDefaultToolkit().createImage("image/fireball.gif");
	private Image successImg = successIcon.getImage();
	
	private int count;
	private int rand;
	private int enemy_width;
	private int enemy_height;
	private int enemy2_width;
	private int enemy2_height;
	private int attack_width;
	private int attack_height;
	private int user_width;
	private int user_height;
	private int fb_width;
	private int fb_height;
	
	private int lifes;
	private int score;
	private int time;
	private int attackCount=0; 
	private boolean game=false;
	private boolean fail=false;
	private boolean warning = false;
	private boolean attackStop = false;
	
	private Enemy enemy;
	private Enemy enemy2;
	private Attack attack;
	private Life life;
	private Fireball fb;

	
	private boolean KUp =false;
	private boolean KDown=false;
	private boolean KLeft=false;
	private boolean KRight = false;
	private boolean KSpace = false;
	
	private boolean lifeInit = true;
	
	private Thread thread;
	
	private Image bufferImg; 
	private Graphics bufferGraphics;
	
	
	public GameFrame() {
		setTitle("Space Shotting Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(900,700);
		setLocation(200,50);
		init();
		gameStart();
		setVisible(true);
		
	}
	
	
	public void init() {
		x=0;
		y=200;
		lifes = 5;
		score = 0;
		time = 0;
		
		enemy_width = imageWidth("image/alien1.png");
		enemy_height = imageHeight("image/alien1.png");
		
		enemy2_width = imageWidth("image/alien2.png");
		enemy2_height = imageHeight("image/alien2.png");
		
		attack_width = imageWidth("image/attack.png");
		attack_height = imageHeight("image/attack.png");
		
		fb_width=imageWidth("image/fireball.gif");
		fb_height=imageHeight("image/fireball.gif");
		
		user_width = imageWidth("image/sangsangBoogie.png");
		user_height = imageHeight("image/sangsangBoogie.png");
	}

	public void gameStart() {

		addKeyListener(this); 
		thread = new Thread(this);
		thread.start();
	}
	

	public void run() {
		try {
			while(true) {
				
				KeyProcess();
				attackProcess();
				enemyProcess();
				enemy2Process();
				lifeProcess();
				fireballProcess();
				
				repaint();
				Thread.sleep(20);
				count++;
				time++;
				System.out.println(time);
			}
		}catch(Exception e) {
			
		}
	}
	
	public void paint(Graphics g) {
		bufferImg = createImage(900,700);
		bufferGraphics = bufferImg.getGraphics();
		
		update(g);
	}
	
	public void update(Graphics g) {
		drawBackGround();
		drawCanvas();
		drawAttack();
		drawEnemy();
		drawLife();
		drawScore();
		drawWarning();
		if(time>300) {
			drawEnemy2();
		}
		
		if(game==true) {
			drawFail();
			drawSuccess();
		}
		g.drawImage(bufferImg,0,0,this);
	}
	
	public void drawAttack() {
		if(game == false) {
			for(int i = 0; i< attack_List.size(); ++  i) {
				attack=(Attack)(attack_List.get(i));
				bufferGraphics.drawImage(attackImg, attack.x, attack.y,this);
				
			}
		}
	}

	public void drawFireball() {
		if(game==false) {
			for(int i = 0; i< fireball_List.size(); ++i) {
				fb=(Fireball)(fireball_List.get(i));
				bufferGraphics.drawImage(fireballImg, fb.x, fb.y, this);
			}
		}
	}
	
	public void drawEnemy() {
		if(game == false) {
			for(int i=0;i<enemy_List.size();++i) {
				enemy=(Enemy)(enemy_List.get(i));
				bufferGraphics.drawImage(enemyImg,enemy.x,enemy.y,this);
			}
		}
	}
	
	public void drawEnemy2() {
		if(game == false) {
			for(int i=0;i<enemy2_List.size();++i) {
				enemy2=(Enemy)(enemy2_List.get(i));
				bufferGraphics.drawImage(enemy2Img,enemy2.x,enemy2.y,this);
			}
		}

	}

	public void drawLife() { 
		if(game == false) {
			for(int i=0; i<life_List.size();++i) {
				life=(Life)(life_List.get(i));
				bufferGraphics.drawImage(lifeImg,20+i*30,50,this);
			}
		}
	}
	
	public void drawCanvas() {
		if(game == false) {
			bufferGraphics.drawImage(user,x,y,this);
		}
	}

	public void drawBackGround() {
		bufferGraphics.drawImage(backImg,0,0,this);
	}

	public void drawScore() {
		if(game == false) {
			bufferGraphics.setColor(Color.WHITE);
			bufferGraphics.setFont(new Font("Arail",Font.BOLD,50));
			bufferGraphics.drawString(Integer.toString(score),720,80);
		}
	}

	public void drawWarning() {
		if(warning==true) {
			bufferGraphics.drawImage(warningImg,200,200,this);}
	}
	
	public void drawFail() {
		if(game==true && fail==true) {
			bufferGraphics.drawImage(failImg,100,120,this);
			bufferGraphics.setColor(Color.WHITE);
			bufferGraphics.setFont(new Font("Arail",Font.BOLD,150));
			bufferGraphics.drawString("FAIL",400,370);
			bufferGraphics.setFont(new Font("Arail",Font.BOLD,40));
			bufferGraphics.drawString("Score : "+Integer.toString(score),400,420);
			
		}
		
	}

	public void drawSuccess() {
		if(game==true && fail==false) {
			bufferGraphics.drawImage(successImg,20,120,this);
			bufferGraphics.setColor(Color.WHITE);
			bufferGraphics.setFont(new Font("Arail",Font.BOLD,150));
			bufferGraphics.drawString("CLEAR",330,370);
			bufferGraphics.setFont(new Font("Arail",Font.BOLD,40));
			bufferGraphics.drawString("Score : "+Integer.toString(score),400,420);
		}
	}

	
	public void KeyProcess() { 
		if(KUp == true) {
			if(y>10) y -= 7;
		}
		if(KDown == true) {
			if(y<550) y+=7;
		}
		if(KLeft == true) {
			if(x>-10) x-=7;
		}
		if(KRight == true) {
			if(x<800) x+=7;
		}
	}
	
	public void attackProcess(){ 
		if(KSpace == true) {
			if(count%5==0) {
				attack = new Attack(x+100,y+40);
				attack_List.add(attack);
				KSpace = false;
			}
		}
		
		for(int i=0;i<attack_List.size();++i) {
			attack = (Attack)attack_List.get(i);
			attack.move();
			if(attack.x>900-20) {
				attack_List.remove(i);
			}
			for(int j=0;j<enemy_List.size();++j) {
				enemy=(Enemy)enemy_List.get(j);
				if(killEnemy(attack.x,attack.y,enemy.x,enemy.y,attack_width,attack_height,enemy_width,enemy_height)) {
					attack_List.remove(i);
					enemy_List.remove(j);
					score+=100; //�� ��ġ �� ���� up
					System.out.println("score = "+score);
				}
			}
			for(int z=0;z<enemy2_List.size();++z) {
				if(killEnemy(attack.x,attack.y,enemy2.x,enemy2.y,attack_width,attack_height,enemy2_width,enemy2_height)) {
					attack_List.remove(i);
					enemy2_List.remove(z);
					score+=300; //�� ��ġ �� ���� up
					System.out.println("score = "+score);
				}
			}
		}
	}
	
	public void enemyProcess() {
		for(int i = 0; i<enemy_List.size();i++) {
			enemy = (Enemy)(enemy_List.get(i));
			enemy.move();
			if(enemy.x<-200) {
				enemy_List.remove(i);
			}
		}
		
		if(count%100 == 0) {
			rand = (int)(Math.random()*610+20);
			enemy = new Enemy(700+100,rand);
			enemy_List.add(enemy);
		}
	}

	public void enemy2Process() {
		for(int i = 0; i<enemy2_List.size();i++) {
			enemy2 = (Enemy)(enemy2_List.get(i));
			enemy2.move2();
			if(enemy2.x<-200) {
				enemy2_List.remove(i);
			}
		}
		if(time>300) {
			if(count%100 == 5) {
				rand = (int)(Math.random()*650+10);
				enemy2 = new Enemy(700+100,rand);
				enemy2_List.add(enemy2);
			}
		}
	}
	
	public void fireballProcess() {
		for(int i= 0 ;i<fireball_List.size();i++) {
			fb=(Fireball)(fireball_List.get(i));
			fb.move();
			if(fb.x<-200) {
				fireball_List.remove(i);
			}
		}		
	}
	
	public void lifeProcess() {
		
		if(lifeInit==true) {
			for(int i=0;i<5;i++) {
				life = new Life(50,50);
				life_List.add(life);
			}
			lifeInit=false;
		}
		
		for(int j=0;j<enemy_List.size();++j) {
			enemy=(Enemy)enemy_List.get(j);
			if(killEnemy(x,y,enemy.x,enemy.y,user_width,user_height,enemy_width,enemy_height)) { 
				life_List.remove(life_List.size()-1);
				lifes--;
				x=0;
				y=200; 
				if(life_List.size()<=0) {
					
					System.out.println("the end");
					game=true;
					fail=true;
					thread.interrupt();
		
					
				}
			}
		}
		for(int j=0;j<enemy2_List.size();++j) {
			enemy2=(Enemy)enemy2_List.get(j);
			if(killEnemy(x,y,enemy2.x,enemy2.y,user_width,user_height,enemy2_width,enemy2_height)) { 
				life_List.remove(life_List.size()-1);
				lifes--;
				x=0;
				y=200; 
				if(life_List.size()<=0) {
					System.out.println("the end");
					game=true;
					fail=true;
					thread.interrupt();
					
				}
			}
		}
		
		for(int j=0;j<fireball_List.size();++j) {
			fb=(Fireball)fireball_List.get(j);
			if(killEnemy(x,y,fb.x,fb.y,user_width,user_height,fb_width,fb_height)) { 
				life_List.remove(life_List.size()-1);
				fireball_List.remove(j);
				lifes--;
				x=0;
				y=200; 
				if(life_List.size()<=0) {
					System.out.println("the end");
					game=true;
					fail=true;
					thread.interrupt();
				
				}
			}
		}	
	}
	
	
	public boolean killEnemy(int ax,int ay,int ex,int ey,int aw,int ah,int ew,int eh) {
		boolean kill = false;
		
		if(Math.abs((ax+aw/2)-(ex+ew/2))<(ew/2+aw/2) && 
				Math.abs((ay+ah/2)-(ey+eh/2))<(eh/2+ah/2)){
			System.out.println("kill");
			kill=true;
		}
		else {
			kill = false;
		}
		return kill;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			KUp=true;
			break;
		case KeyEvent.VK_DOWN:
			KDown=true;
			break;
		case KeyEvent.VK_LEFT:
			KLeft=true;
			break;
		case KeyEvent.VK_RIGHT:
			KRight=true;
			break;
		case KeyEvent.VK_SPACE:
			if(attackStop == false) {
				System.out.println("attack");
				KSpace = true;
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP :
			KUp = false;
			break;
		case KeyEvent.VK_DOWN :
			KDown = false;
			break;
		case KeyEvent.VK_LEFT :
			KLeft = false;
			break;
		case KeyEvent.VK_RIGHT :
			KRight = false;
			break;
		case KeyEvent.VK_SPACE:
			KSpace = false;
			break;
		}
	}
	
	public int imageWidth(String file) {
		int width = 0;
		try {
			File f = new File(file);
			BufferedImage buf = ImageIO.read(f);
			width=buf.getWidth();
		}catch(Exception e) {
			
		}
		return width;
		
	}
	public int imageHeight(String file) {
		int height = 0;
		try {
			File f = new File(file);
			BufferedImage buf = ImageIO.read(f);
			height=buf.getHeight();
		}catch(Exception e) {
			
		}
		return height;
		
	}
	
}
    
