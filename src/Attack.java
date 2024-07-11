import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Attack {
	int x,y;
	Attack(int x, int y){
		this.x=x;
		this.y=y;
		
	}
	public void move() {
		x+=10;
	}
}
