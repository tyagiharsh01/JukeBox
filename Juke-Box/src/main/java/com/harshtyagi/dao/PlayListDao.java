package com.harshtyagi.dao;
import com.harshtyagi.exception.SongNotFound;
import com.harshtyagi.util.SimpleAudioPlayer;
import com.harshtyagi.bean.PlayList;
import com.harshtyagi.bean.Song;
import com.harshtyagi.exception.PlayListNotFound;
import com.harshtyagi.util.DbUtil;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PlayListDao {
    SongDao songDao = new SongDao();
    Scanner sc = new Scanner(System.in);
    PlayList playList = new PlayList();

    List<PlayList> playLists;
    private static Connection connection = DbUtil.getConnection();


    public void createPlayList() throws SQLException, SongNotFound {
        PlayList playList = new PlayList();
        System.out.println("*******************************");
        System.out.println("    Create New PlayList     ");
        System.out.println("******************************");
        System.out.println("Enter PlayList Name");
        String name = sc.nextLine();
       if(createdPlayList(name)){
           String query = "INSERT INTO Playlist(playlistName) VALUES (?)";
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setString(1,name);
           int row = preparedStatement.executeUpdate();
           if(row>0){
               System.out.println("playList is created.");
              boolean flag = true;
               do{
                   System.out.println("Do you want to add in the playlist"+
                           "\n1. add song in playlist."+
                           "\n0. to exit");
                   int choice = sc.nextInt();
                   switch (choice){
                       case 1 :
                           addASong();
                           break;
                       case 0 :
                           flag = false;

                   }

               }while(flag);

           }

       }
    }

    public void deletePlayList() throws SQLException {
        show();
        System.out.println("Enter Your PlaylistId");
        playList.setId(sc.nextInt());
        String query ="Delete from songs_in_playlist where playlistId = "+playList.getId();
        String Queery = "DELETE FROM playlist WHERE playlistId = " + playList.getId();
        Statement statement = connection.createStatement();

       int  rows = statement.executeUpdate(query);
        int rows1 = statement.executeUpdate(Queery);
        if (rows != 0||rows1!=0) {
            System.out.println("Deleted successFully.");
        } else {
            System.out.println("Given id is not present in table.");
        }
    }

    public List<PlayList> getAllPlayList() throws SQLException {
        playLists = new ArrayList<>();
        Statement statement = connection.createStatement();
        String Query = "SELECT * FROM playlist;";
        ResultSet resultSet = statement.executeQuery(Query);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            playLists.add(new PlayList(id, name));
        }
        return playLists;
    }

    public void show() throws SQLException {
        getAllPlayList();
        System.out.println("+------------------------+");
        System.out.format("|%-4s|\t%-17s|\n", "Id", "PlayListName");
        System.out.println("+----+-------------------+");

        for (PlayList list : playLists) {
            System.out.format("|%-4d|\t%-17s|\n", list.getId(), list.getPlayListName());
        }
        System.out.println("+----+-------------------+");
    }

    public void addASong() throws SQLException,SongNotFound {
        List<Song> songs = songDao.getAllSongs();
        songDao.showSong(songs);
        System.out.println("Enter songId.");
        int songId = sc.nextInt();
        show();
        System.out.println("Enter playlistId.");
        int playListId = sc.nextInt();
        String Query = "INSERT INTO songs_in_playlist values(?,?)";
        PreparedStatement pstmt = connection.prepareStatement(Query);
        pstmt.setInt(1, playListId);
        pstmt.setInt(2, songId);
        int rows = pstmt.executeUpdate();
        if(rows!=0){
            System.out.println("Song is added in the playlist.");
        }
     else{
           throw new SongNotFound("Song is not in the catalog");
        }

    }

    public List<Song> getSongFromPlayList(int playListId) throws SQLException {
//        System.out.println("please enter  the playlistId");
//        int playListId = sc.nextInt();
        List<Song> songList = new ArrayList<>();
        String Query = "Select s.* from Songs s  inner join songs_in_playlist  sp on  sp.songsId = s.songId " +
                "inner join playlist p on sp.playlistId = p.playlistId where sp.playlistId =" + playListId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Query);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String songTitle = resultSet.getString(2);
            String artistName = resultSet.getString(3);
            String genre = resultSet.getString(4);
            String duration = resultSet.getString(5);
            String filePath = resultSet.getString(6);
            songList.add(new Song(id, songTitle, artistName, genre, duration, filePath));
        }
        return songList;
    }

        public  boolean createdPlayList(String name) throws SQLException {
            String sql = "select * from Playlist where playlistName  ='"+name+"';";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            if(resultSet.next()){
                System.out.println("playList already exist.");
                return false;
            }
            else{
               return  true;
            }

        }
        public List<Song> shuffle(int playListId) throws SQLException {
          List<Song> songs =  getSongFromPlayList(playListId);
            Collections.shuffle(songs);
            return  songs;
        }
        public void searchPlayList()throws SQLException, PlayListNotFound {
        int id = sc.nextInt();
                List<PlayList> playLists1 = getAllPlayList();
                List<PlayList> list = new ArrayList<>();
            for (PlayList playList:playLists1) {
                if(playList.getId()==id){
                    list.add(playList);
                }
            }
            if(list.size()==0){
                throw new PlayListNotFound("Id is not found.");

            }
        }
        public void playPlayList(int id ) throws SQLException {
        List<Song> songs;
        songs = getSongFromPlayList(id);
        for(Song song : songs){
            String filePath = song.getFilePath();
            SimpleAudioPlayer.getPlaySong(filePath);
        }
        }
}
