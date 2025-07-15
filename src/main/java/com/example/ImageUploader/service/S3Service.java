package com.example.ImageUploader.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3){
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(new PutObjectRequest(
                    bucketName,
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    metadata
            ));
            return "Uploaded Successfully";
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    public S3Object downloadFile(String fileName) {
        return amazonS3.getObject(bucketName, fileName);
    }

    public List<String> listFiles() {
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        List<String> fileNames = new ArrayList<>();
        for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
            fileNames.add(os.getKey());
        }
        return fileNames;
    }
}
