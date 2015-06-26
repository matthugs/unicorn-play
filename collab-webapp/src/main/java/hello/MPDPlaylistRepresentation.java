public class MPDPlaylistRepresentation{
	MPDSong current;
	MPDSong next;

	public MPDPlaylistRepresentation() {
		timer = new Timer();
		timer.schedule(new ChangePoller(), (current.getLength()*1000 - 4000), 500);
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