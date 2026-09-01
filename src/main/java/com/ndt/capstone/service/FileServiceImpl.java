package com.ndt.capstone.service;

import com.ndt.capstone.enums.exception.FileError;
import com.ndt.capstone.exception.file.FileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileServiceImpl implements FileService {
    @Value("${upload.image}")
    private String root;


    @Override
    public void save(MultipartFile file) {
        try {
            Path rootPath = Paths.get(root); // truy đến file trong server để tải hình xuống
            if (!Files.exists(rootPath)) {
                Files.createDirectories(rootPath);
            }

            Files.copy(file.getInputStream(), rootPath.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new FileException(FileError.SAVE_FILE_FAILED);
        }
    }


    @Override
    public Resource load(String filename) {
        try {
            Path pathFile = Paths.get(root).resolve(filename); // truy đến file trong server để đọc và tải về client, su dung browser
            Resource resource = new UrlResource(pathFile.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileException(FileError.FILE_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
