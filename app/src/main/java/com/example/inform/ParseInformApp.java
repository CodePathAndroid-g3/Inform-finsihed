package com.example.inform;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseInformApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        ParseObject.registerSubclass(Post.class);
        //initialize parse
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("TFAdN8RQwugbk6phMih8NNvuEe9kUEs5gvBAsVhY")
                .clientKey("tQYZFm9vESujogEZaH5qs60HWwB7XKxkW4YQ0ZTp")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
