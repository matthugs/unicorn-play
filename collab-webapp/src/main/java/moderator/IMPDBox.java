package moderator;

public interface IMPDBox {
	/*
	 * Takes in a hash value and plays the song in question.
	 * I'm not actually sure if the hash is a String, Emily may edit this to the appropriate type as she sees fit.
	 * What is important is that it searches for a song with the appropriate hash, puts it in the front of the playlist, and then plays it.
	 * It works like the currently existing addSong except that it can be more appropriately used by the frontend.
	 */
	public void playFromHash(String hash) throws Exception;
	
	/*
	 * These are things that the player should probably have and already does.
	 * I'll deal with querying features in the moderator's interface.
	 */
	
	public void play() throws Exception;
	
	public void pause() throws Exception;
	
	public void stop() throws Exception;
	
	public void next() throws Exception;
	
	public void previous() throws Exception;
}
