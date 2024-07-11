import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class StartFrame extends JFrame{
	
	private StartPanel startPanel = new StartPanel();
	private ImageIcon startIcon = new ImageIcon("image/start.png");
	private ImageIcon startClickIcon = new ImageIcon("image/startclick.png");
	private ImageIcon startBackIcon = new ImageIcon("image/startback.png");
	private ImageIcon startBoogieIcon = new ImageIcon("image/startboogie.png");
	private Image startBackImg = startBackIcon.getImage();
	private Image startBoogieImg = startBoogieIcon.getImage();
	private JButton startBtn = new JButton(startIcon);
	public StartFrame() {
		setTitle("Ω¥∆√ ∞‘¿”");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900,700);
		setLocation(200,50);
		setContentPane(startPanel);
		
		startBtn.setSize(400,145);
		startBtn.setLocation(230,500);
		startBtn.setFont(new Font("Gothic",Font.ITALIC,50));
		startBtn.setBorderPainted(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setRolloverIcon(startClickIcon);
		//startBtn.setText("START");
		add(startBtn);
		

		
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				GameFrame sf = new GameFrame();
			}
		});
		
		
		
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g) {
		g.drawImage(startBackImg, 0, 0, null);
		g.drawImage(startBoogieImg,40,100,null);
		g.setFont(new Font("Arail",Font.BOLD,90));
		g.setColor(Color.WHITE);
		g.drawString("SpaceBoogie",310,370);
	}
}

class StartPanel extends JPanel{
	public StartPanel() {
		setLayout(null);
	}
}