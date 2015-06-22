package hello;

import org.bff.javampd.objects.*;
import java.util.List;
import java.util.ArrayList;

public class MPDQuery implements Query{
	public List<String> searchAny(String criteria){
		return songListToJSONList();
	}

	public List<String> searchArtist(String artist){
		return new ArrayList<String>();
	}

	public List<String> searchGenre(String genre){
		return new ArrayList<String>();
	}

	public List<String> searchAlbum(String album){
		return new ArrayList<String>();
	}

	public List<String> searchSong(String song){
		return new ArrayList<String>();
	}

	private String mpdSongToJSON(MPDSong mpdsong){
		//TODO: FIX ME!!!!!!
		return "";
	}

	private List<String> songListToJSONList(ArrayList<MPDSong> songList) {
		List<String> ret = new ArrayList<String>();
		for(MPDSong song : songList) {
			ret.add(mpdSongToJSON(song));
		}
		return ret;
	}

}