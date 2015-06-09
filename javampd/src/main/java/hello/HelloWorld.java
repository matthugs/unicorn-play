package hello;
import org.bff.javampd.*;
import java.util.List;

public class HelloWorld {  
    
    boolean debug = true;
    public static void main(String[] args) {
        System.out.println("\n\nStarting this mess");
        MPD mpd = null;
        try{
            mpd = new MPD.Builder()
                        .port(6001)
                        .password("password")
                        .build();

            for(String stuff : mpd.getServerStatus().getStatus()) {
                System.out.println(stuff);
            }

            System.out.println("uptime: "+mpd.getServerStatistics().getUptime());
            System.out.println("songs : "+ mpd.getServerStatistics().getSongs());

            Admin admin = mpd.getAdmin();
            admin.updateDatabase();

            System.out.println("uptime: "+mpd.getServerStatistics().getUptime());
            System.out.println("songs : "+ mpd.getServerStatistics().getSongs());

            Database database = mpd.getDatabase();
            System.out.println("Now I should be listing songs:");
            for(String song : database.listAllFiles()) {
                System.out.println(song);
            }

            System.out.println("Now I'm going to add all these songs to a playlist");

            Playlist playlist = mpd.getPlaylist();

            //playlist.addSongs(List<MPDSong> songList)
            //Collection<MPDSong>   database.listAllSongs()
            playlist.addSongs((List)database.listAllSongs());

            Player player = mpd.getPlayer();

            player.play();

            if(mpd.isConnected()) {
                System.out.println("HELL YEAH I'M CONNECTED");
            }
            else {
                System.out.println("WAT WHY AM I NOT CONNECTED");
            }

            //genreDatabase = mpd.getDatabaseManager().getGenreDatabase();
            System.out.println("Finishing this mess\n\n");
        }
        catch (Exception e){
        	System.out.println("OH NOES");
            System.out.println(e.getMessage());
        }
        // ServerStatus ss = mpd.getServerStatus();
        // genreDatabase = mpd.getDatabaseManager().getGenreDatabase();
        // //MPDDatabaseManager databaseManager = mpd.getDatabaseManager();

        // Greeter greeter = new Greeter();
        // System.out.println(greeter.sayHello());
    }

    public void debugOut(String str){
        if (debug) {
            System.out.println(str);
        }
    }
}