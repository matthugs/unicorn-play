package querybot;

import java.util.Map;
//import org.bff.javampd.objects.*;

import objects.PlaylistItem;

/**
 * Specifies the interface between the bots and MPD Database related methods
 * @author emily
 *
 */
public interface IQuery {	
	/* These methods take in string search criteria and return a list of songs in
	 * Map<String, Object> format
	 */
	/**
	 * @param criteria
	 * @return a list of songs found searching by the criteria
	 */
	public Map<String, Object> searchAny(String criteria); //returns list of songs
	
	
	/**
	 * @param artist
	 * @return a list of songs found searching by the artist
	 */
	public Map<String, Object> searchArtist(String artist);//returns list of songs
	
	
	/**
	 * @param genre
	 * @return a list of songs found searching by the genre
	 */
	public Map<String, Object> searchGenre(String genre);  //returns list of songs
	
	
	/**
	 * @param album
	 * @return a list of songs found searching by the album
	 */
	public Map<String, Object> searchAlbum(String album);  //returns list of songs
	
	
	/**
	 * @param song
	 * @return a list of songs found searching by the song title
	 */
	public Map<String, Object> searchSong(String song);    //returns list of songs

	

	/**
	 * This method takes in a string search criteria and returns a list of Artists
	 * @param artist
	 * @return a list of ArtistItems
	 */
	public Map<String, Object> searchforArtists(String artist);//returns list of artists

	/* This method takes in a string search criteria and returns a list of Albums 
	 * as opposed to the methods above which return Songs 
	 */
	
	/**
	 * This method takes in a string search criteria and returns a list of AlbumItems
	 * as opposed to the methods above which return Songs 
	 * @param artist
	 * @return a lit of AlbumItems
	 */
	public Map<String, Object> listAllAlbumsByArtist(String artist);


	/**
	 * Returns a song associated with the hash.
	 * @param hash
	 * @return
	 */
	public PlaylistItem getSong(String hash);
	
	
	/**
	 * @return a list of all possible PlaylistItems in the library
	 */
	public Map<String, Object> listAll();
};
