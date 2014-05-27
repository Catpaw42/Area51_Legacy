import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import game.MainMenuController;

public class Main
{
	public static void main(String[] args)
	{
		MainMenuController menu = new MainMenuController();
//		playSound("Matadortheme.wav");
		menu.runProgram();
	}
	public static synchronized void playSound(final String url) {
		Runnable sound = new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							Main.class.getResourceAsStream(url));
					clip.open(inputStream);
					clip.start(); 
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		};
		sound.run();
	}
}
