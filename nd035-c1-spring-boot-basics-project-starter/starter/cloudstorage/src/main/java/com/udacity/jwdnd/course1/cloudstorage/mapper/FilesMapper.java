package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    ArrayList<File> getFiles(Integer userid);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES(filename,contenttype,filesize,userid, filedata)" +
            "VALUES( #{fileName},#{contentType},#{fileSize}, #{userid},#{fileData})")
    @Options(useGeneratedKeys = true,keyProperty = "fileId")
    Integer insertFile(File file);


    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);


}
