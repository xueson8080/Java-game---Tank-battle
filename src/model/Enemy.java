package model;
import game.Direction;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class Enemy {
    
    public Direction dir;
    private  int x,y, dirtime_cnt=0;
    public int width,height;
    private int speed=7;

    private int dir_flag;
    
    private Image img ;

    private static final Random r = new Random();

    public Enemy( String img_path ) {
        this.img = GameUtil.getImage(img_path);
        this.width = img.getWidth(null);
        this.height = img.getWidth(null);
		Init();
        getfangxiang();
        getimgpath();
	}
    public void Init(){
        this.x = 10 + (int)(Math.random() * ((540 - 10) + 1));
        this.y = 10 + (int)(Math.random() * ((540 - 10) + 1));
	}
    public void getfangxiang(){
        this.dir_flag=(int)(r.nextInt(5))+1;
        if(this.dir_flag==1){
            this.dir = Direction.U;
        }else
        if(this.dir_flag==2){
            this.dir = Direction.D;
        }else
        if(this.dir_flag==3){
            this.dir = Direction.L;
        }else{
            this.dir = Direction.R;
        }
    }
    public void getimgpath(){
        switch(this.dir) {
            case L:
                this.img= GameUtil.getImage("image/enemyL.png");
                break;
            case U:
                this.img= GameUtil.getImage("image/enemyU.png");
                break;
            case R:
                this.img= GameUtil.getImage("image/enemyR.png");
                break;
            case D:	
                this.img= GameUtil.getImage("image/enemyD.png");
                break;
            }
    }

    public void move(){
        switch(this.dir) {
            case L:
                this.x =  this.x-(int)(speed*Math.random()) < 10 ? 10 : this.x-(int)(speed*Math.random()) ;
                break;
            case R:
                this.x =  this.x+(int)(speed*Math.random()) > 540 ? 540 : this.x+(int)(speed*Math.random()) ;
                break;
            case U:
                this.y =  this.y-(int)(speed*Math.random()) < 33 ? 33 : this.y-(int)(speed*Math.random()) ;
                break;
            case D:	
                this.y =  this.y+(int)(speed*Math.random()) > 540 ? 540 : this.y+(int)(speed*Math.random()) ;
                break;
        }
    }

    public void draw(Graphics g){
        if (this.dirtime_cnt>40) {
            getfangxiang();
            getimgpath();
            this.dirtime_cnt=0;
        }
        else {
            this.dirtime_cnt++;
        }
        move();
        g.drawImage(img, x, y, null);
        
    }

    public int Getx(){
        return this.x;
    }
    public int Gety(){
    	return this.y;
    }
    public Direction Getdir(){
        return this.dir;
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

}

