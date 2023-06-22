package com.harshtyagi.exception;

import com.harshtyagi.dao.PlayListDao;

public class PlayListNotFound extends Exception {
    public PlayListNotFound(String massage){
        super(massage);
    }

}
