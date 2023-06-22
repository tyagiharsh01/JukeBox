package com.harshtyagi.bean;

public class Song {
        private int songId;
        private String songTitle;
        private String artistName;
        private String genre;
        private String duration;
        private String filePath;

        public Song(int songId, String songTitle, String artistName, String genre, String duration, String filePath) {
            this.songId = songId;
            this.songTitle = songTitle;
            this.artistName = artistName;
            this.genre = genre;
            this.duration = duration;
            this.filePath = filePath;
        }

        public Song(java.lang.String songTitle, String artistName, String genre, String duration, String filePath) {
            this.songTitle = songTitle;
            this.artistName = artistName;
            this.genre = genre;
            this.duration = duration;
            this.filePath = filePath;
        }

        public Song() {
        }

        public int getSongId() {
            return songId;
        }

        public String getSongTitle() {
            return songTitle;
        }

        public void setSongTitle(String songTitle) {
            this.songTitle = songTitle;
        }

        public String getArtistName() {
            return artistName;
        }

        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public String toString() {
            return "Song{" +
                    "songId=" + songId +
                    ", songTitle='" + songTitle + '\'' +
                    ", artistName='" + artistName + '\'' +
                    ", genre='" + genre + '\'' +
                    ", duration='" + duration + '\'' +
                    ", filePath='" + filePath + '\'' +
                    '}';
        }
}

