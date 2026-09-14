package com.ndt.capstone.service.contract;

import java.nio.file.Path;
import java.util.stream.Stream;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    void init();


    void save(MultipartFile file, String relativePath);


    Resource load(String filename, String... relativePath);


    void deleteAll();


    Stream<Path> loadAll();


    default void save(MultipartFile file) {
        save(file, "");
    }


    default Resource load(String filename) {
        return load(filename, "");
    }
}
