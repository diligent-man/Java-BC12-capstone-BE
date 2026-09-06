package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import com.ndt.capstone.model.FileInfo;
import com.ndt.capstone.enums.exception.FileErrMsg;
import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.service.contract.FileService;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        fileService.save(file);
        return ResponseEntity.ok(
            ApiResponse.builder()
                .message(FileErrMsg.FILE_UPLOADED.getErrorMsg())
                .build()
        );
    }


    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.load(filename);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(file);
    }


    @GetMapping("/{filename}/info")
    public ResponseEntity<ApiResponse> getFjmetileInfo(@PathVariable String filename) {
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
