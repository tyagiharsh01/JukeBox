package com.harshtyagi.bean;

public class PlayList {

        private int id;
        private String playListName;

        public PlayList() {
        }

        public PlayList(String playListName) {
            this.playListName = playListName;
        }

        public PlayList(int id, String playListName) {
            this.id = id;
            this.playListName = playListName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlayListName() {
            return playListName;
        }

        public void setPlayListName(String playListName) {
            this.playListName = playListName;
        }

    @Override
    public String toString() {
        return "PlayList{" +
                "id=" + id +
                ", playListName='" + playListName + '\'' +
                '}';
    }
}
