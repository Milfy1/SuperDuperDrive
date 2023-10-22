package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    ArrayList<Note> getNotes(Integer userid);

    @Insert("INSERT INTO NOTES(notetitle,notedescription,userid)" +
            "VALUES( #{noteTitle} ,#{noteDescription} , #{userid} )")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insertNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void deleteNote(Integer noteId);

    @Update("UPDATE NOTES set notetitle = #{noteTitle} , notedescription = #{noteDescription} where noteid = #{noteid}")
    Integer editNote(Note note);
}
