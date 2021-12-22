package music;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Java ������Ƶ
 * @ClassName: MusicPlayer   
 * @Description: TODO   
 * @author: hyacinth
 * @date: 2020��3��5�� ����12:10:53     
 * @Copyright: hyacinth
 */
public class MusicPlayer {
	
	
	static Player player = null;

	public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
		File file = new File("D:\\Desktop\\Battle\\src\\music\\suiyueshentou.mp3");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream stream = new BufferedInputStream(fis);
		Player player = new Player(stream);
		player.play();
	}

	/**
	 * ���� 20 �벢��������
	 */
	public void play() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
				} catch (Exception e) {

					// TODO: handle exception
				}
			}
		}).start(); 
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		player.close();
	}

	
}

