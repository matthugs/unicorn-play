package mpdconnectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.bff.javampd.*;
import org.bff.javampd.objects.*;

/**
 * This is the singleton class that provides a single global instance of the MPD object.
 * @author emily
 *
 */
public final class MPDWrapper {

	private static final Logger logger = LogManager.getLogger();

	private static final int portNumber = 6001;
	private static final String password = "password";


	private static MPD mpdglobal = null;

	/**
	 * Constructs the MPD object on the given port number and clears any existing playlists.
	 */
	private MPDWrapper(){
		try {
            logger.debug("Constructing global mpd object");
            mpdglobal = new MPD.Builder()
                        .port(portNumber)
                        .build();
            logger.debug("Clearing Playlist");
            mpdglobal.getPlaylist().clearPlaylist();
            
        }
        catch (Exception e) {
        	logger.error("MPD connection error. Is MPD running?");
        }
    }

    /**
     * @return The global instance of the MPD Object.
     */
    public static MPD getMPD()
    {
      	if (mpdglobal == null){
        	// it's ok, we can call this constructor
          	MPDWrapper wrapper = new MPDWrapper();
        }
        return mpdglobal;
    }
}
