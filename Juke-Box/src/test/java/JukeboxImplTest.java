import com.harshtyagi.bean.PlayList;
import com.harshtyagi.bean.Song;
import com.harshtyagi.dao.PlayListDao;
import com.harshtyagi.dao.PlaylistDetails;
import com.harshtyagi.dao.SongDao;
import com.harshtyagi.main.JukeboxImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

public class JukeboxImplTest {
   JukeboxImpl jukebox;
   SongDao songDao;
   Song song;
   PlayList playList;
   PlayListDao playListDao;
   PlaylistDetails playlistDetails;
   @Before
   public void setUp(){
     jukebox = new JukeboxImpl();
     song = new Song();
     songDao = new SongDao();
     playList = new PlayList();
     playListDao = new PlayListDao();
     playlistDetails = new PlaylistDetails();

   }


   @Test
    public void getallSongsTest() throws SQLException {
       List<Song> songs = songDao.getAllSongs();
       int size = songs.size();
        assertEquals(12,size);

   }
   @Test
   public void createdPlaylistTest() throws SQLException {
     boolean flag =   playListDao.createdPlayList("Insane");
     assertEquals(true,flag);
      flag = playListDao.createdPlayList("Insane");
     assertNotEquals(false,flag);

   }
   @Test
   public void getSongFromPlayListTest() throws SQLException {
       List<Song> songs = playListDao.getSongFromPlayList(1);
       String name = "Insane";
       assertEquals(name,songs.get(0).getSongTitle());
   }
   @Test
   public void getAllPlayListTest() throws SQLException {
       List<PlayList> playLists = playListDao.getAllPlayList();
       int size = playLists.size();
       assertEquals(7,size);

   }
    @After
    public void tearDown(){
        jukebox = null;
        song = null;
        songDao=null;
        playList = null;
        playListDao = null;
        playlistDetails = null;
    }

}
