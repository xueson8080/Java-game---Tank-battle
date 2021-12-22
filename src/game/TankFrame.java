package game;

import model.GameUtil;
import model.Tank;
import model.Bullet;
import model.Enemy;
// System.out.println("随机数为："+ this.x);
import java.awt.Color;
import java.awt.Font;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
// package music;

import java.io.FileInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javazoom.jl.player.Player;

/*
 * 基本界面完备
 * */

public class TankFrame extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int width=600,height=600;

	public double may=0;

	Image bg = GameUtil.getImage("image/bg.png");

	Image life = GameUtil.getImage("image/life.png");

	Tank tank = new Tank("image/tankU.png",300,300);
	
	LinkedList<Bullet> bulletList = new LinkedList<>();

	LinkedList<Enemy> enemyList = new LinkedList<>();

	LinkedList<Bullet> enemybulletList = new LinkedList<>();

	private int score=0;
	private int unmatchedtime=0;
	private boolean BGM_flag=false;
	public  int lifecnt=0;
	public boolean death_flag=false;
	public boolean running=true;
	public int enemytotal = 50 ;
	public boolean voice=true;
	public int enemycnt;

	private final MyPaintThread paintThread = new MyPaintThread();

	Music bgm=new Music("music/chunjiaoyuzhiming.mp3");
	Music shoot=new Music("music/shoot.mp3");
	Music move=new Music("music/move.mp3");
	Music crash =new Music("music/crash_enemy.mp3");
	Music startgame =new Music("music/startgame.mp3");
	
	ExecutorService executorService=Executors.newCachedThreadPool();

	private Image offScreenImage = null;

	private static TankFrame TF = null;
	
	public static void main(final String[] args) {
		TF = new TankFrame();
		TF.launch();
	}
	public void launch() {
		this.setTitle("TankFrame");
		this.setSize(600, 600);
		this.setLocation(750, 50);
		this.setBackground(Color.BLACK);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setVisible(true);
		for(int i=0;i<enemytotal;i++){
			final Enemy enemy = new Enemy("image/enemyU.png");
			enemyList.add(enemy);
		 }
		 if (voice) {
		 	executorService.execute(startgame);
		 }
			 this.addKeyListener(new KeyMonitor());
		new Thread(paintThread).start();
	}
	@Override
	public void update(final Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(600, 600);
		}
		final Graphics offg = offScreenImage.getGraphics();
		paint(offg);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	@Override
	public void paint(final Graphics g) {
		g.drawImage(bg, 0, 0, null);
		if (lifecnt==-1) {
			lifecnt=0;
			gameOver(g);
			return;
		}
		for(int i=0;i<bulletList.size();i++){
			final Bullet bullet=bulletList.get(i);
			for(int j=0;j<(enemyList.size() < 5 ? enemyList.size() : 5);j++){
				final Enemy enemy=enemyList.get(j);
				final Rectangle bulletRectangle = new Rectangle(bullet.Getx(), bullet.Gety(),bullet.width,bullet.height);
				final Rectangle enemyRectangle = new Rectangle(enemy.Getx(), enemy.Gety(), enemy.width, enemy.height);
				final boolean bullet_enemy_crash= bulletRectangle.intersects(enemyRectangle);
				if(bullet_enemy_crash){
					bulletList.remove(i);
					enemyList.remove(j);
					score++;
					if (voice) {
						executorService.execute(crash);
					}
					continue;
				}
			}
			for(int j=0;j< enemybulletList.size();j++){
				final Bullet enemybullet=enemybulletList.get(j);
				final Rectangle bulletRectangle = new Rectangle(bullet.Getx(), bullet.Gety(),bullet.width,bullet.height);
				final Rectangle enemybulletRectangle = new Rectangle(enemybullet.Getx(), enemybullet.Gety(), enemybullet.width, enemybullet.height);
				final boolean bullet_enemybullet_crash_flag= bulletRectangle.intersects(enemybulletRectangle);
				if(bullet_enemybullet_crash_flag){
					bulletList.remove(i);
					enemybulletList.remove(j);
				}
			}
		}

		for(int j=0;j< enemybulletList.size();j++){
			final Bullet enemybullet=enemybulletList.get(j);
			final Rectangle enemybulletRectangle = new Rectangle(enemybullet.Getx(), enemybullet.Gety(), enemybullet.width, enemybullet.height);
			final Rectangle tankRectangle = new Rectangle(tank.Getx(), tank.Gety(), tank.width, tank.height);
			final boolean death_crash_flag= enemybulletRectangle.intersects(tankRectangle);
			if(death_crash_flag){
				enemybulletList.remove(j);
				if(unmatchedtime>60) {
					tank.Replace();
					lifecnt--;
					unmatchedtime=0;
			   }
				
			}
		}
		g.drawImage(life, 10, 58, null);
		tank.draw(g);
		for(int i=0;i<bulletList.size();i++){
			final Bullet bullet=bulletList.get(i);
			bullet.draw(g);
		}
		for(int i=0;i < (enemyList.size() < 5 ? enemyList.size() : 5) ;i++){
			final Enemy enemy=enemyList.get(i);
			enemy.draw(g);
			if (Math.random() < 0.05) {
				final Bullet enemybullet = new Bullet(enemy.Getgun_x(),enemy.Getgun_y(),enemy.Getdir());
				enemybulletList.add(enemybullet);
			}
		}
		for(int i=0;i<enemybulletList.size();i++){
			final Bullet enemybullet=enemybulletList.get(i);
			enemybullet.draw(g);
		}
		final Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.setColor(c);
		printInfo(g,"分数："+ score ,20,13,50);
		printInfo(g,"x " + lifecnt ,20,50,80);
		printInfo(g,"开炮：空格      暂停：C      开启或关闭音乐：V" ,13,150,45);
		printInfo(g,"播放背景音乐：P",13,486,45);
		if (unmatchedtime<60) {
			printInfo(g,"无敌时间："+ (3000-unmatchedtime*50)/1000,15,500,580);
		}
	}

	public void printInfo(final Graphics g,final String message,final int size,final int x,final int y){
		g.setColor(Color.orange);
		final Font f = new Font("宋体",Font.BOLD,size);
		g.setFont(f);
		g.drawString(message, x,y);
	}
	private void gameOver(Graphics g) {
		printInfo(g,"GAME OVER",80,120,300);
		printInfo(g,"分数："+ score, 40, 240, 400);
		printInfo(g,"按 C 键重新开始",17,250,550);
		switch(score/10){
			case 4:
				printInfo(g,"独孤求败",50,220,500);
				break;
			case 3:
				printInfo(g,"登堂入室",50,220,500);
				break;
			case 2:
				printInfo(g,"小有成就",50,220,500);
				break;
			default:
				printInfo(g,"初入江湖",50,220,500);
				break;
		}
		paintThread.dead();
	}
	/*
	 * 
	 */
	private class MyPaintThread implements Runnable {
		private boolean pause = false;
		@Override
		public void run() {
			while (true) {
				if (pause) {
					continue;
				}
				repaint();  
				try {
					Thread.sleep(50);
					if(unmatchedtime<=60) {
					 	unmatchedtime++;
					}
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		/*
		 * 函数功能：暂停
		 * */
		public void pause(){
			pause = true;
		}
		/*
		 * 从暂停中恢复
		 * */
		public void keepon(){
			pause = false;
		}
		public void dead(){
			death_flag=true;
			pause = true;
			lifecnt=2;
			score=0;
			enemycnt=enemyList.size();
			enemycnt=enemycnt<enemytotal? enemytotal-enemycnt : enemytotal;
			for(int i=0;i<enemycnt;i++){
				final Enemy enemy = new Enemy("image/enemyU.png");
				enemyList.add(enemy);
			 }
			for(int i=0;i < enemyList.size() ;i++){
				final Enemy enemy=enemyList.get(i);
				enemy.Init();
			}
		}
	}
	class Music implements Runnable{
		private String Path = null;
		public Music(String music_path){
			this.Path=music_path;
		}
		@Override
		public void run() {
			try {
				new Player(new FileInputStream(TankFrame.class.getClassLoader().getResource(this.Path).getPath().substring(1))).play();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	

	private class KeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(final KeyEvent e) {
			tank.KeyRelasedControlDirection(e);
			final int key_code = e.getKeyCode();
			switch(key_code){
				
				case KeyEvent.VK_C :
					if (!paintThread.pause){
						paintThread.pause();
						voice = false;
					}
					else {
						paintThread.keepon();
						new Thread(paintThread).start();
						if (death_flag)
							if (voice) {
								executorService.execute(startgame);
							}
							death_flag=false;
					}
					break;
				case KeyEvent.VK_V :
					voice = !voice;	
					break;
				case KeyEvent.VK_P :
					if (!BGM_flag) {
						executorService.execute(bgm);
						BGM_flag=true;
					}
					break;
				case KeyEvent.VK_LEFT :
				if (voice) {
					executorService.execute(move);
				}
				break;
				case KeyEvent.VK_RIGHT :
					if (voice) {
						executorService.execute(move);
					}
					break;
				case KeyEvent.VK_DOWN :
					if (voice) {
						executorService.execute(move);
					}
					break;
				case KeyEvent.VK_UP :
					if (voice) {
						executorService.execute(move);
					}
					break;
				case KeyEvent.VK_SPACE :
					final Bullet bullet = new Bullet(tank.Getgun_x(),tank.Getgun_y(),tank.Getdir());
					bulletList.add(bullet);
					if (voice) {
						executorService.execute(shoot);
					}
					break;
				case KeyEvent.VK_ESCAPE :
					System.exit(0);
					break;
			}
		}
	}
}
