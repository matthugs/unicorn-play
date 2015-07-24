package moderator;

import objects.PlaylistItem;

/**
 * This interface is intended to sit between the moderator package and the mpdconnector package.
 * This is NOT intended for use by the PlaylistModerator to make edits.
 * @author emily
 *
 */
public interface IPlaylistUpdate {
	// really this first pop method should be all that's generally necessary. The other two are included for insurance.
	
	/**
	 * Removes the currently song that was currently playing from the list. 
	 */
	public void popCurrentlyPlaying(); // should implicitly case the playlistStateManager to mark the next as currently playing, if present

	// Included as insurance against errors.
	/**
	 * @param whatMPDIsCurrentlyPlaying
	 * @return true if MPD is playing the same thing that is at the top of the playlist, false else.
	 */
	public boolean verifyCurrentlyPlaying(PlaylistItem whatMPDIsCurrentlyPlaying); //should return true if MPD and the playlistStateManager agree, false else
	
	
	/**
	 * Force the song that MPD is currently playing to be the top of the playlist.
	 * @param whatMPDIsCurrentlyPlaying
	 */
	public void forceCurrentlyPlaying(PlaylistItem whatMPDIsCurrentlyPlaying); // forces the top item on the playlist to be this track that MPD is currently playing
	
	
	/**
	 * @return the next song to be played. 
	 */
	public PlaylistItem getNext();
}