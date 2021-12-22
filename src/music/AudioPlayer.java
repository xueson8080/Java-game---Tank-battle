package music;

import java.io.FileInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javazoom.jl.player.Player;

class AudioPlayer implements Runnable{
	private String Path ="music/chunjiaoyuzhiming.mp3";
	public static void main(String[] args) {
		ExecutorService executorService=Executors.newCachedThreadPool();
		AudioPlayer bgm=new AudioPlayer();
		executorService.execute(bgm);
		// executorService.isShutdown(bgm);
	}
	@Override
	public void run() {
		try {
			new Player(new FileInputStream(AudioPlayer.class.getClassLoader().getResource(this.Path).getPath().substring(1))).play();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
