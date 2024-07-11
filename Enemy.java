
public class Enemy {
	int x;
	int y;
	Enemy(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void move() {
		x-=3;
		
	}
	public void move2() {
		x-=8;
		
	}
}
