package collaboration;

import objects.PlaylistItem;

public interface ICollabToPlaylist {
	// Returns the next track as suggested by the collaboration model
	public PlaylistItem getTrack();
}
