package com.harshtyagi.dao;
import com.harshtyagi.exception.*;
import com.harshtyagi.util.SimpleAudioPlayer;
import com.harshtyagi.bean.PlayList;
import com.harshtyagi.bean.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.harshtyagi.dao.SongDao.connection;

public class PlaylistDetails {
    Scanner sc = new Scanner(System.in);
    PlayListDao playListDao = new PlayListDao();
    SongDao songDao = new SongDao();
    Song song = new Song();

    PlayList playList = new PlayList();
    public void operations () throws SQLException, ArtistNotFound, GenreNotFound, IdNotFound, SongNotFound, PlayListNotFound {
        List<Song> songList = new ArrayList<>();
        songList = songDao.getAllSongs();
        songDao.showSong(songList);
        boolean flag = true;
        while (flag) {
            System.out.println("********************" +
                    "\n1. Create PlayList " +
                    "\n2. Add Song in PlayList " +
                    "\n3. Delete PlayList " +
                    "\n4. Display After Sorting" +
                    "\n5. Search Song in  main catalog " +
                    "\n6. play a Song from catalog"+
                    "\n7. View Playlist" +
                    "\n8. Play Playlist " +
                    "\n9. Shuffle the song in playlist" +
                    "\n10. Display Song in playlist"+
                    "\n0. Exit " +
                    "\n********************");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    playListDao.createPlayList();
                    break;
                case 2:
                    playListDao.addASong();
                    break;
                case 3:
                    playListDao.deletePlayList();
                    break;
                case 4:
                    songDao.sortSongs();
                    break;
                case 5:
                    songDao.displayAfterSearching();
                    break;
                case 6:
                    songDao.playASong();
                    break;
                case 7:
                    playListDao.show();
                    break;
                case 8 :
                    playListDao.show();
                    System.out.println("Enter the playlistId");
                    int id= sc.nextInt();
                   playListDao.playPlayList(id);
                   break;
                case 9 :
                    playListDao.show();
                    System.out.println("Enter the playlistId");
                    int playListId1 = sc.nextInt();
                   List<Song> songs = playListDao.shuffle(playListId1);
                    songDao.showSong(songs);
                    break;
                case 10:
                    playListDao.show();
                    System.out.println("Enter the playlistId");
                    int playListId = sc.nextInt();
                    List<Song> songs1 =playListDao.getSongFromPlayList(playListId);
                    songDao.showSong(songs1);
                case 0 :
                    flag = false;
                    break;
                default:
                    System.out.println("Please provide a input between 1 to 10.");
                    break;
            }
        }

    }

}
