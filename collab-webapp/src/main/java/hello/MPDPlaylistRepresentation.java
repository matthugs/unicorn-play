package hello;

import java.util.Timer;
import java.util.TimerTask;

import org.bff.javampd.objects.MPDSong;

public class MPDPlaylistRepresentation{
	MPDSong current;
	MPDSong next;
	private Timer timer;
	public PlaylistStateManager manager;//so it can call methods to tell it when the song ends

	public MPDPlaylistRepresentation() {
		timer = new Timer();
		timer.schedule(new ChangePoller(), (current.getLength()*1000 - 4000), 500);
	}
	
	public MPDPlaylistRepresentation(PlaylistStateManager manager) {
		timer = new Timer();
		timer.schedule(new ChangePoller(), (current.getLength()*1000 - 4000), 500);
		this.manager = manager;
	}

	class ChangePoller extends TimerTask {
		long lastTime = 0;
		long thisTime = 0;

		public void run() {
			try{
				System.out.println("inside the try block");
				thisTime = player.getElapsedTime();

				if(thisTime != lastTime){
					lastTime = thisTime;
					System.out.println(thisTime+" / "+player.getTotalTime());
				}
				else {
					System.out.println("do I have a logical error?? Look:" + thisTime);
				}
			}
			catch (Exception e) {
				System.out.println("it didn't work");
			}
		}
	}
	
	private void timerUpdate(int milliseconds) {
		timer.cancel();
		//timer.purge();
		timer.schedule(new ChangePoller(), (current.getLength()*1000 - 4000), 500);
	}
}