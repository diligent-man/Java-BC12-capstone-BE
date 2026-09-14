package com.ndt.capstone.service;

import java.nio.file.*;

import java.util.Objects;
import java.util.stream.Stream;

import java.io.IOException;
import java.net.MalformedURLException;


import jakarta.annotation.PostConstruct;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;


import com.ndt.capstone.exception.GenericException;
import com.ndt.capstone.exception.file.FileException;
import com.ndt.capstone.service.contract.FileService;

import com.ndt.capstone.enums.exception.FileErrMsg;
import com.ndt.capstone.enums.exception.GenericErrMsg;


@Service
public class FileServiceImpl implements FileService {
    @Value("${file.root:./data/}")
    private final Path root = Paths.get("data");


    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new GenericException(GenericErrMsg.INTERNAL_SERVER_ERROR, "Could not initialize folder for upload!");
        }
    }


    @Override
    public void save(MultipartFile file, String relativePath) {
        Path dst = Paths.get(relativePath, Objects.requireNonNull(file.getOriginalFilename()));

        try {
            Files.copy(
                file.getInputStream(),
                root.resolve(dst),
                StandardCopyOption.REPLACE_EXISTING
            );
        } catch (FileAlreadyExistsException e) {
            throw new FileException(FileErrMsg.FILE_EXISTED);
        } catch (IOException e) {
            throw new FileException(
                FileErrMsg.FILE_UPLOAD_FAILED,
                String.format("Could not save %s file to %s", file.getOriginalFilename(), root)
            );
        }
    }


    @Override
    public Resource load(String filename, String... relativePaths) {
        try {
            Path dst = Paths.get(root.toString(), relativePaths);
            Path file = dst.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileException(
                    FileErrMsg.FILE_READ_FAILED,
                    String.format("Could not read the %s file from %s", filename, root)
                );
            }
        } catch (MalformedURLException e) {
            throw new GenericException(GenericErrMsg.INTERNAL_SERVER_ERROR, "FileService Error: " + e.getMessage());
        }
    }


    @Override
    public void deleteAll() {
        try (Stream<Path> children = Files.list(root)) {
            children.forEach(child -> FileSystemUtils.deleteRecursively(child.toFile()));
        } catch (IOException e) {
            throw new GenericException(GenericErrMsg.INTERNAL_SERVER_ERROR, "FileService Error: " + e.getMessage());
        }
    }


    @Override
    public Stream<Path> loadAll() {
        try (Stream<Path> walk = Files.walk(root, 1)) {
            return walk.filter(path -> !path.equals(this.root))
                .map(root::relativize)
                .toList()
                .stream();
        } catch (IOException e) {
            throw new FileException(
                FileErrMsg.FILE_READ_FAILED,
                String.format("Could not load the file(s) from %s", root)
            );
        }
    }
}
