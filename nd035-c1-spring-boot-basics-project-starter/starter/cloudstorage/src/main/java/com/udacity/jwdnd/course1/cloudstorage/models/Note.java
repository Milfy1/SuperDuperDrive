package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Data;

@Data
public class Note {

    public Note(Integer noteid, String noteTitle, String noteDescription, Integer userid) {
        this.noteid = noteid;
        this.userid = userid;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    private Integer noteid;
    private Integer userid;
    private String noteTitle;
    private String noteDescription;


}
