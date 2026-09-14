package com.ndt.capstone.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;


import jakarta.validation.Valid;


import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.ndt.capstone.model.FileInfo;

import com.ndt.capstone.service.contract.FileService;

import com.ndt.capstone.enums.file.UploadImageType;
import com.ndt.capstone.enums.exception.FileErrMsg;

import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.payload.request.file.ProductImageRequest;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    @Value("${file.upload.image.path:./data/upload/images}")
    private final String uploadImagePath = Paths.get("data", "upload", "images").toString();

    private final FileService fileService;


    private String resolveContentType(Resource resource) {
        try {
            String type = Files.probeContentType(resource.getFile().toPath());
            return type != null ? type : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }


    private ResponseEntity<Resource> getFile(String filename) {
        Resource file = fileService.load(filename);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(resolveContentType(file)))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file);
    }


    @GetMapping({
        "/product/{filename}", // path for default image
        "/product/{brand}/{productName}/{filename}"
    })
    public ResponseEntity<Resource> getProductImage(
        @Valid @ModelAttribute ProductImageRequest req
    ) {
        return getFile(Paths.get(
                uploadImagePath,
                UploadImageType.PRODUCT.getFolder(),
                Objects.requireNonNullElse(req.getBrand(),""),
                Objects.requireNonNullElse(req.getProductName(), ""),
                req.getFilename()
            ).toString()
        );
    }


    @GetMapping("/{filename}/info")
    public ResponseEntity<ApiResponse> getFileInfo(@PathVariable String filename) {
        String url = MvcUriComponentsBuilder.fromMethodName(
                FileController.class,
                "getFile",
                filename
            )
            .build()
            .toString();
        return ResponseEntity.ok(
            ApiResponse.builder()
                .message(FileErrMsg.FILE_READ.getErrorMsg())
                .data(new FileInfo(filename, url))
                .build()
        );
    }


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        fileService.save(file);
        return ResponseEntity.ok(
            ApiResponse.builder()
                .message(FileErrMsg.FILE_UPLOADED.getErrorMsg())
                .build()
        );
    }


    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteAllFiles() {
        fileService.deleteAll();
        return ResponseEntity.ok(
            ApiResponse.builder()
                .message(FileErrMsg.FILE_DELETED_ALL.getErrorMsg())
                .build()
        );
    }
}
