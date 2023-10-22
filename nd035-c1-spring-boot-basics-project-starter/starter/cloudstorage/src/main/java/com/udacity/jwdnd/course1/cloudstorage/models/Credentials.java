package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Data;

@Data
public class Credentials {
    private Integer credentialid;
    private Integer userid;
    private String url;
    private String username;
    private String password;

    private String key;


    public Credentials(Integer credentialid, String url, String username,String key, String password,Integer userid) {
        this.credentialid = credentialid;
        this.userid = userid;
        this.url = url;
        this.username = username;
        this.password = password;
        this.key = key;
    }
}
