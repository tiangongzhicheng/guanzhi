package com.moyu.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DownFileMapper {

    @Insert("INSERT INTO down_file (folder_no) VALUES (#{param1})")
    Integer insertData(String folderNo);
}
