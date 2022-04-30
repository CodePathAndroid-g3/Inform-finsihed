package com.example.inform;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject{

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT ="createdAt";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_LOCATION = "location";
    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION,description);
    }
    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE,parseFile);
    }
    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }
    public String getStatus(){
        return getString(KEY_STATUS);
    }
    public void setStatus(String status){
        put(KEY_STATUS,status);
    }
    public String getContact(){
        return getString(KEY_CONTACT);
    }
    public void setContact(String Contact){
        put(KEY_CONTACT,Contact);
    }
    public String getLocation(){
        return getString(KEY_LOCATION);
    }
    public void setLocation(String location){
        put(KEY_LOCATION,location);
    }
}
