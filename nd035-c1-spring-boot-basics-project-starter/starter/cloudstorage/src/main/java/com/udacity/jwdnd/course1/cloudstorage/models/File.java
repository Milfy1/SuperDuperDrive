package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Blob;
@Data
public class File{

    public File(Integer fileId, String fileName, String contentType, Long fileSize,Integer userid, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.contentType = contentType;
        this.userid = userid;
    }

    private Integer fileId;
    private String fileName;
    private Long fileSize;
    private byte[] fileData;
    private String contentType;
    private Integer userid;







}
