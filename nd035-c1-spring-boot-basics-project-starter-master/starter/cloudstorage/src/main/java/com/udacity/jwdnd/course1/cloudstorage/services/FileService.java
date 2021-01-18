package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileUploadForm;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    FileMapper fileMapper;
    UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public List<File> getFiles(int userId){
        return fileMapper.getFiles(userId);
    }

    public void addFile(MultipartFile multipartFile, int userId) throws IOException {
        InputStream fis = multipartFile.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] fileData = buffer.toByteArray();

        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        File file = new File(0, fileName, contentType, fileSize, userId, fileData);
        fileMapper.insert(file);
    }

    public List<String> getAllStoredFileNames(int userId){
        List<File> allFiles = fileMapper.getFiles(userId);
        List<String> allFileNames = new ArrayList<String>();
        for(File file:allFiles){
            allFileNames.add(file.getFileName());
        }
        return allFileNames;
    }

    public File getFileByName(int userId,String fileName){
        return fileMapper.getFileByName(userId,fileName);
    }

    public void deleteFileByName(String fileName){
         fileMapper.deleteFileByName(fileName);
    }
}
