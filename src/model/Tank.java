package model;

import game.Direction;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Tank {
	
	public static Direction dir = Direction.U;
	public  int x,y,width,height;
	private int speed=10;
	public Image img ;

	public void draw(Graphics g){
			g.drawImage(img, x, y, null);
	}
	public Tank(String img_path,int x, int y){
		this.img = GameUtil.getImage(img_path);
		this.x = x;
		this.y = y;
		this.width = img.getWidth(null);
		this.height = img.getWidth(null);
	}
	public void Replace(){
		this.x=300;
		this.y=300;
    }
	public int Getx(){
        return this.x;
    }
    public int Gety(){
    	return this.y;
    }
	public int Getgun_x(){
		if(this.dir==Direction.U){
			return this.x+20;
		}else 
		if(this.dir==Direction.D){
			return this.x+20;
		}else 
		if(this.dir==Direction.L){
			return this.x-8;
		}else 
		if(this.dir==Direction.R){
			return this.x+50;
		}else return 0;

	}
	public int Getgun_y(){
		if(this.dir==Direction.U){
			return this.y-10;
		}else
		if(this.dir==Direction.D){
			return this.y+50;
		}else
		if(this.dir==Direction.L){
			return this.y+20;
		}else
		if(this.dir==Direction.R){
			return this.y+19;
		}else return 0;
	}
	public Direction Getdir(){
		return this.dir;
	}


	public void KeyRelasedControlDirection(KeyEvent e){
		int key_code = e.getKeyCode();
		switch(key_code){
			case KeyEvent.VK_LEFT :
				this.img= GameUtil.getImage("image/tankL.png");
				this.dir=Direction.L;
				this.x= this.x-speed<10? 10:this.x-speed;
				break;
			case KeyEvent.VK_UP :
				this.img= GameUtil.getImage("image/tankU.png");
				this.dir=Direction.U;
				this.y= this.y-speed<33? 33:this.y-speed;
				break;
			case KeyEvent.VK_RIGHT :
				this.img= GameUtil.getImage("image/tankR.png");
				this.dir=Direction.R;
				this.x= this.x+speed>540? 540:this.x+speed;
				break;
			case KeyEvent.VK_DOWN :
				this.img= GameUtil.getImage("image/tankD.png");
				this.dir=Direction.D;
				this.y= this.y+speed>540? 540:this.y+speed;
				break;
		}
	}

}
