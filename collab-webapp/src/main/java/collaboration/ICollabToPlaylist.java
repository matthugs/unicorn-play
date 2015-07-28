package collaboration;

import objects.PlaylistItem;

/**
 * An interface designed to sit between any collaboration model and the mechanisms of the playlist.
 * @author emily
 *
 */
public interface ICollabToPlaylist {

	/**
	 * @return the next track as suggested by the collaboration model
	 */
	public PlaylistItem getTrack();
}
