package moderator;

import objects.PlaylistItem;

/*
 * This interface is intended to sit between the moderator package and the mpdconnector package.
 * This is NOT intended for use by the PlaylistModerator to make edits.
 */
public interface IPlaylistUpdate {
	// really this first pop method should be all that's generally necessary. The other two are included for insurance.
	
	public void popCurrentlyPlaying(); // should implicitly case the playlistStateManager to mark the next as currently playing, if present

	// Included as insurance against errors.
	public boolean verifyCurrentlyPlaying(PlaylistItem whatMPDIsCurrentlyPlaying); //should return true if MPD and the playlistStateManager agree, false else
	public void forceCurrentlyPlaying(PlaylistItem whatMPDIsCurrentlyPlaying); // forces the top item on the playlist to be this track that MPD is currently playing
	public PlaylistItem getNext();
}