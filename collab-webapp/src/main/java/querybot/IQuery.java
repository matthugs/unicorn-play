package querybot;

import java.util.Map;
//import org.bff.javampd.objects.*;

import objects.PlaylistItem;

public interface IQuery {
	// all of these need to return in the form of a map containing JSON objects
	// The objects in question will have to be composed from MPD info... perhaps we could have a different constructor of PlaylistItem to make sure we get everything?
	// This is the only format we can actually return in with the bot.
	public Map<String, Object> searchAny(String criteria);
	public Map<String, Object> searchArtist(String artist);
	public Map<String, Object> searchGenre(String genre);
	public Map<String, Object> searchAlbum(String album);
	public Map<String, Object> searchSong(String song);
	public PlaylistItem getSong(String hash);
	// this one is temporary but should be informative
	public Map<String, Object> listAll();
};
