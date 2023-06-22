package com.harshtyagi.main;

import com.harshtyagi.dao.PlaylistDetails;
import com.harshtyagi.exception.*;

import java.sql.SQLException;
import java.util.Scanner;

public class JukeboxImpl {
    public static void main(String[] args){
        PlaylistDetails  playlistDetails = new PlaylistDetails();
        System.out.println("*************************************  Welcome to  Harsh's Jukebox   *************************************" );
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
       while(flag){
           System.out.println("1.for continue with jukebox \n0. for exit ");
           int choice = sc.nextInt();
           switch (choice){

               case 1 :
                   try {
                       playlistDetails.operations();
                   } catch (SQLException e){
                   e.printStackTrace();
                   }catch (ArtistNotFound |GenreNotFound |IdNotFound |PlayListNotFound | SongNotFound e){
                       System.out.println(e);
                   }
                   break;
               case 0 : flag = false;
                   System.out.println("Thanks for visiting.");
               break;
               default:
                   System.out.println("please provide a  valid input");

           }
       }
    }
}