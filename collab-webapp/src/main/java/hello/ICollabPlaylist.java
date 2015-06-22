import org.bff.javampd.objects.*;
import java.util.List;
import java.util.ArrayList;

public interface ICollabPlaylist {
	public void addTrack(MPDSong song);
	public void removeTrack(MPDSong song);
	//If the song is not in the list, it just adds the song. Otherwise, it promotes it the specified number of positions.
	public void promoteTrack(MPDSong song, int positions);

}