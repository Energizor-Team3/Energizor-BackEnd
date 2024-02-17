package com.energizor.restapi.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
public class FileUploadUtils {

    public static String saveFile(String uploadDir, String originalFileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 확장자를 포함한 고유한 파일 이름 생성
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = UUID.randomUUID().toString(); // UUID를 이용해 고유한 문자열 생성
        String uniqueFileName = fileName + "." + extension; // 실제 저장될 고유 파일 이름

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ex){
            throw new IOException("Could not save file: " + originalFileName, ex);
        }

        return uniqueFileName;
    }

    public static boolean deleteFile(String uploadDir, String fileName) {

        boolean result = false;
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            result = true;
        }
        try {
            Path filePath = uploadPath.resolve(fileName);
            Files.delete(filePath);
            result = true;
        }catch (IOException ex){

            log.info("Could not delete file: " + fileName, ex);
        }

        return result;
    }
}
