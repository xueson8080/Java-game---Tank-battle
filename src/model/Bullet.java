package model;
import game.Direction;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Bullet {
	private int speed=12;

	//所在的位置
	private int row;
	private int col;
	private Direction dir;
	//大小
	public   int width =10;
	public   int height = 10;
	
	private Color color = Color.RED;
	
	public Bullet(int col, int row, Direction dir) {
		this.row = row;
		this.col = col;
		this.dir=dir;
	}
	public int Getx(){
        return this.col;
    }
    public int Gety(){
    	return this.row;
    }
    public Direction Getdir(){
        return this.dir;
    }
	public void draw(Graphics g){
		Color c= g.getColor();
		g.setColor(color);
		g.fillOval(col, row, width, height);
		g.setColor(c);
		move();
		//改变下一次的颜色
		if(color==Color.RED){
			color = Color.BLUE;
		}
		else{
			color = Color.RED;
		}
	}

	public void move() {
		if(this.dir==Direction.U){
			this.row-=speed;
		}
		if(this.dir==Direction.D){
			this.row+=speed;
		}
		if(this.dir==Direction.L){
			this.col-=speed;
		}
		if(this.dir==Direction.R){
			this.col+=speed;
		}
	}
	public void BulletControl(KeyEvent e){
		int key_code = e.getKeyCode();
		switch(key_code){
			case KeyEvent.VK_LEFT :
				break;
			case KeyEvent.VK_UP :
				break;
			case KeyEvent.VK_RIGHT :
				break;
			case KeyEvent.VK_DOWN :
				break;
			case KeyEvent.VK_SPACE :
				//shoting();
				break;
		}
	}
}





