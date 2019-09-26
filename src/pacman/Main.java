package pacman;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
		Tela t = new Tela();
		
		
		Timer m = new Timer(true);
		TimerTask task1 = new TimerTask() {
			@Override
			public void run() {
				if(Tela.vidas == 0) {
					Tela.music.stop();
					t.tocarGO();
					this.cancel();
				}
			}
		};
		m.scheduleAtFixedRate(task1, 5000, 1000);

	}

}
