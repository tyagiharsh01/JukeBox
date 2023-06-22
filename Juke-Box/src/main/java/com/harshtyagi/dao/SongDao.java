package com.harshtyagi.dao;

import com.harshtyagi.util.SimpleAudioPlayer;
import com.harshtyagi.bean.Song;
import com.harshtyagi.exception.*;
import com.harshtyagi.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SongDao {
    private List<Song> songs;
    Scanner sc = new Scanner(System.in);

    public static Connection connection = DbUtil.getConnection();
    public List<Song> getAllSongs() throws SQLException {
        songs = new ArrayList<>();
        String Query = "SELECT * FROM Songs;";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(Query);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String songTitle = resultSet.getString(2);
            String artistName = resultSet.getString(3);
            String genre = resultSet.getString(4);
            String duration = resultSet.getString(5);
            String filePath = resultSet.getString(6);
            songs.add(new Song(id, songTitle, artistName, genre, duration, filePath));
        }

        return songs;
    }


    public void showSong(List<Song> songList) throws SQLException {

        System.out.println("+-------------------------------------------------------------------------------------+");

        System.out.format("|%-6s|\t%-21s|\t%-17s|\t%-15s|\t%-10s|\n","Id","songTitle","artistName","genre","duration","filePath");
        System.out.println("+-------------------------------------------------------------------------------------+");

        for (Song song : songList) {
            System.out.format("|%-6d|\t%-21s|\t%-17s|\t%-15s|\t%-10s|\n", song.getSongId(), song.getSongTitle(),
                    song.getArtistName(), song.getGenre(), song.getDuration());

        }
        System.out.println("+-------------------------------------------------------------------------------------+");
    }


    public void insertSong(Song song) throws SQLException {

        String  query = "insert into Song values(?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(2, song.getSongTitle());
        pstmt.setString(3, song.getArtistName());
        pstmt.setString(4, song.getGenre());
        pstmt.setString(5, song.getDuration());
        pstmt.setString(6, song.getFilePath());
        int rows = pstmt.executeUpdate();
        if (rows != 0) {
            System.out.println("Song is not added in catalog");
        } else {
            System.out.println("Song is successfully added in catalog");
        }
    }


    public void sortbySongTitle(List<Song> songList) throws SQLException {
        Comparator<Song> bySongTitle = (Song s1, Song s2) -> s1.getSongTitle().compareTo(s2.getSongTitle());
        songList.sort(bySongTitle);

    }

    public void sortByArtistName() throws SQLException {
        Comparator<Song> byArtist = (Song s1, Song s2) -> s1.getArtistName().compareTo(s2.getArtistName());
        songs.sort(byArtist);
        showSong(songs);
    }

    public void sortByGenre() throws SQLException {
        Comparator<Song> byGenre = (Song s1, Song s2) -> s1.getGenre().compareTo(s2.getGenre());
        songs.sort(byGenre);
        showSong(songs);
    }

    public void sortBySongId() throws SQLException {
        Comparator<Song> bySongId = (Song s1, Song s2) -> {
            return Integer.compare(s1.getSongId(), s2.getSongId());
        };
        songs.sort(bySongId);
        showSong(songs);
    }

//    public void searchSong(String category) throws SQLException {
//        if(category.trim().equalsIgnoreCase("songId")){
//            sortBySongId();
//        } else if (category.trim().equalsIgnoreCase("songTitle")) {
//            sortbySongTitle();
//        } else if (category.equals("genre")) {
//            sortByGenre();
//        }
//    }
public void searchById(int id) throws SQLException, IdNotFound {
    List<Song> songList = getAllSongs();
    List<Song> list = new ArrayList<>();
    for (Song song : songList) {
        if (song.getSongId() == id) {
            list.add(song);
        }
    }
    if(list.size()==0){
        throw new IdNotFound("Id is not found.");

    }
    sortbySongTitle(list);

    showSong(list);
}
    public void searchByName(String name) throws SQLException, ArtistNotFound {
        List<Song> songList = getAllSongs();
        List<Song> list = new ArrayList<>();
        for (Song song : songList) {
            if(song.getArtistName().equalsIgnoreCase(name)){
                list.add(song);
            }

        }
        if(list.size()==0){
            throw new ArtistNotFound("Artist is not Found.");
        }
        sortbySongTitle(list);

        showSong(list);
    }
    public void searchByTitle(String name) throws SQLException, SongNotFound {
        List<Song> songList = getAllSongs();
        List<Song> list = new ArrayList<>();
        for (Song song : songList) {
            if(song.getSongTitle().equalsIgnoreCase(name)){
                list.add(song);
            }

        }
        if(list.size()==0){
            throw  new SongNotFound("Song is not found.");

        }
        sortbySongTitle(list);
        showSong(list);
    }
    public void searchByGenre(String name) throws SQLException, GenreNotFound {
        List<Song> songList = getAllSongs();
        List<Song> list = new ArrayList<>();
        for (Song song : songList) {
            if(song.getGenre().equalsIgnoreCase(name)){
                list.add(song);
            }

        }
        if(list.size()==0){
            throw new GenreNotFound("Genre is not found.");
        }
        sortbySongTitle(list);
        showSong(list);
    }
    public void displayAfterSearching() throws SQLException, ArtistNotFound, GenreNotFound, SongNotFound, IdNotFound {
        Scanner sc = new Scanner(System.in);
        System.out.println("search on the basis of \n1.Artist \n2.Genre \n3.SongID ");
        int k = sc.nextInt();
        if ( k == 1) {
            System.out.println("Enter Artist Name");
            sc.nextLine();
            String artist = sc.nextLine();
            searchByName(artist);
        }
        if (k == 2) {
            System.out.println("Enter genre category");
            sc.nextLine();
            String cate = sc.nextLine();
            searchByGenre(cate);
        }
        if (k == 3) {
                System.out.println("Enter SongId ");
                int sid = sc.nextInt();
                sc.nextLine();
                searchById(sid);
            }
        }
    public void sortSongs() throws SQLException {
        System.out.println("Enter Category");
        String sortCategory = sc.nextLine();
        List<Song> list = getAllSongs();
        List<Song> songs = new ArrayList<>();
        switch (sortCategory.toLowerCase()) {
            case "songtitle":
                sortbySongTitle(list);
                showSong(list);
                break;

            case "genre":
                sortByGenre();
                break;

            case "artist":
                sortByArtistName();
                break;
        }
    }
    public void playASong() throws SQLException, IdNotFound {
        List<Song >songList = getAllSongs();
        showSong(songList);
        System.out.println("Please enter a song id");
        int songId = sc.nextInt();
        songList.forEach(song -> {
            if(song.getSongId()==songId){
               String filepath = song.getFilePath();
               SimpleAudioPlayer.getPlaySong(filepath);
            }
        });
        //        String filePath = null;
//        String query  = "select filePath from Songs where songId = "+songId;
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(query);
//        while(resultSet.next()){
//            filePath = resultSet.getString(1);
//        }

//        else
//            throw new IdNotFound("songId is not match.");




    }
}
