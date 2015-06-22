package hello;

import org.bff.javampd.*;
import org.bff.javampd.objects.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class MPDQuery implements Query{

	private MPDDatabase database;
	private HashTable<String, MPDSong> hashTable;

	public MPDQuery(MPDDatabase database) {
		this.database = database;
		hashTable = new HashTable<String, MPDSong>();
		ArrayList<MPDSong> allSongList = database.listAllSongs();
		for(MPDSong song : allSongList) {
			hashTable.put(hash(song), song);
		}
	}

	public List<String> searchAny(String criteria){
		return songListToJSONList(database.findAny(criteria));
	}

	public List<String> searchArtist(String artist){
		return songListToJSONList(database.findArtist(artist));
	}

	public List<String> searchGenre(String genre){
		return songListToJSONList(database.findGenre(genre));
	}

	public List<String> searchAlbum(String album){
		return songListToJSONList(database.findAlbum(album));
	}

	public List<String> searchSong(String song){
		return songListToJSONList(database.findTitle(song));
	}

	public List<String> searchYear(String year) {
		return songListToJSONList(database.findYear(year));
	}

	private String mpdSongToJSON(MPDSong mpdsong){
		//TODO: FIX ME!!!!!!
		//Should include the hash for this song: method is below.
		return "";
	}

	private List<String> songListToJSONList(Collection<MPDSong> songList) {
		List<String> ret = new ArrayList<String>();
		for(MPDSong song : songList) {
			ret.add(mpdSongToJSON(song));
		}
		return ret;
	}

	protected String hash(MPDSong song){
		return (song.getArtistName() + song.getTitle());
	}
	
	public MPDSong getSong(String hash){
		return hashTable.get(hash);
	}

}