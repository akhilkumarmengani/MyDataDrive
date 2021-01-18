package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Mapper

public interface FileMapper {

    @Select("SELECT * FROM files WHERE userId = #{userId}")
    List<File> getFiles(int userId);

    @Insert("INSERT INTO files(filename,contenttype,filesize,userid,filedata) VALUES (#{fileName},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * from files WHERE userId = #{userId} and filename = #{fileName}")
    File getFileByName(int userId, String fileName);

    @Delete("DELETE FROM FILES WHERE filename = #{fileName}")
    void deleteFileByName(String fileName);
}
