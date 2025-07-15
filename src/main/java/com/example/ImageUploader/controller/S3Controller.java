package com.example.ImageUploader.controller;

import com.example.ImageUploader.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3Controller {
   @Autowired
   private S3Service s3Service;

   @PostMapping("/upload")
   public String uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
       return s3Service.uploadFile(file);
   }

   @GetMapping("/download/{fileName}")
    public String downloadFile(@PathVariable String fileName) {
       return s3Service.downloadFile(fileName).getKey();
   }

   @GetMapping("/list")
   public List<String> listFiles() {
       return s3Service.listFiles();
   }
}
