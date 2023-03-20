package com.example.nurecareercenterua.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_PNG;

@Service
@RequiredArgsConstructor
public class FileStoreService {

    private static final String EXCEPTION_MESSAGE = "Failed to store file to amazon s3";
    private final AmazonS3 amazonS3;

    private void uploadImageToBucket(String path,
                       String fileName,
                       Optional<Map<String, String>> optionalMetadata,
                       InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        try {
            amazonS3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException(EXCEPTION_MESSAGE, e);
        }
    }

    public void uploadImage(MultipartFile image, String path) {
        validateImage(image);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", image.getContentType());
        metadata.put("Content-Length", String.valueOf(image.getSize()));

        String filename = image.getOriginalFilename();

        try {
            uploadImageToBucket(path, filename, Optional.of(metadata), image.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file!");
        } else if (!Arrays.asList(IMAGE_JPEG.toString(), IMAGE_PNG.toString()).contains(image.getContentType())) {
            throw new IllegalStateException("The file type ["+ image.getContentType() + "] does not support!");
        }
    }
}
