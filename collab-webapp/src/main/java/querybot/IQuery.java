package querybot;

import java.util.Map;
//import org.bff.javampd.objects.*;

import objects.PlaylistItem;

public interface IQuery {	
	/* These methods take in string search criteria and return a list of songs in
	 * Map<String, Object> format
	 */
	public Map<String, Object> searchAny(String criteria); //returns list of songs
	public Map<String, Object> searchArtist(String artist);//returns list of songs
	public Map<String, Object> searchGenre(String genre);  //returns list of songs
	public Map<String, Object> searchAlbum(String album);  //returns list of songs
	public Map<String, Object> searchSong(String song);    //returns list of songs
	
	//Deprecated: Use listAllAlbumsByArtist() below
	//public Map<String, Object> searchArtistAlbums(String artist);//returns list of albums
	
	/* This method takes in a string search criteria and returns a list of Artists */
	public Map<String, Object> searchforArtists(String artist);//returns list of artists

	/* This method takes in a string search criteria and returns a list of Albums 
	 * as opposed to the methods above which return Songs 
	 */
	public Map<String, Object> listAllAlbumsByArtist(String artist);


	public PlaylistItem getSong(String hash);
	// this one is temporary but should be informative
	public Map<String, Object> listAll();
};
