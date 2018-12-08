package com.neo.tools;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author 34924
 */
@Component
public class Upload {
    private static String UPLOADED_FOLDER = "D:\\test\\";

    public static String uploadFile( MultipartFile file) {
        if (file.isEmpty()) {
            return "error,the file is empty!";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            return "You successfully uploaded '" + file.getOriginalFilename() + "'";

        } catch (IOException e) {
            return "upload error!";
        }
    }

    public static String uploadFiles( List<MultipartFile> files) {
        String message = "success uploaded ";
        for (MultipartFile file : files) {
            try {
                uploadFile(file);
            } catch (Exception e) {
                return "upload error!";
            }
            message = message + file.getOriginalFilename() + " ";
        }
        return message;

    }

    public static String uploadFiless(MultipartFile []files) {
        String message = "success uploaded ";
        for (MultipartFile file : files) {
            try {
                uploadFile(file);
            } catch (Exception e) {
                return "upload error!";
            }
            message = message + file.getOriginalFilename() + " ";
        }
        return message;

    }
}
