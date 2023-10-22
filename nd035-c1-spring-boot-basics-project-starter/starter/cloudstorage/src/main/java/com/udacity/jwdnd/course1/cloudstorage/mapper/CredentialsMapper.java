package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM Credentials WHERE userid = #{userid}")
    ArrayList<Credentials> getCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS(url,username,key,password,userid)" +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true,keyProperty = "credentialid")
    Integer insertCredentials(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} ")
    void deleteCredential(Integer credentialid);

    @Update("UPDATE CREDENTIALS set url = #{url} , username = #{username}, password = #{password} , key=#{key} where credentialid = #{credentialid}")
    Integer editCredentials(Credentials credentials);
}
