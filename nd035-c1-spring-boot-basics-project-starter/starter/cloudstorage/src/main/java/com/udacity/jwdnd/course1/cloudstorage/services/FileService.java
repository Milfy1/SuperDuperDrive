package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
@Service
public class FileService {
    private final FilesMapper filesMapper;

    public FileService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public ArrayList<File> getFiles(Integer userId){
       return filesMapper.getFiles(userId);
    }

    public String uploadFile(Integer userId, MultipartFile file) throws IOException, SQLException {

        ArrayList<File> allfiles = filesMapper.getFiles(userId);

        if(file.isEmpty()){
            return "Please Choose a File";
        }
for (File item : allfiles){
    if(item.getFileName().contentEquals(file.getOriginalFilename()))
        return "There is a file with the same name";
}

        File newFile = new File(null,file.getOriginalFilename(),file.getContentType(),file.getSize(),userId,file.getBytes());
      filesMapper.insertFile(newFile);
        return "success";
    }

    public void deleteFile(Integer fileId){
        filesMapper.deleteFile(fileId);
    }

    public File viewFile(Integer fileId){
        File file = filesMapper.getFile(fileId);
        return file;
    }
}
