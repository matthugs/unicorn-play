package hello;

import java.util.List;

public interface Query {
	public List<String> searchAny(String criteria);
	public List<String> searchArtist(String artist);
	public List<String> searchGenre(String genre);
	public List<String> searchAlbum(String album);
	public List<String> searchSong(String song);
};
