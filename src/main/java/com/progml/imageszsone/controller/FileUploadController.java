package com.progml.imageszsone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Controller
public class FileUploadController {
    private final Random random = new Random();
    private final Path directory = Paths.get("src\\main\\resources\\static\\uploads");
    private final Path secondDirectory = Paths.get("target\\classes\\static\\uploads");

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        String originalFilename = multipartFile.getOriginalFilename().replace("[", "").replace("]", "");

        Path dest = Paths.get(directory + "/" + originalFilename);
        Path secondDest = Paths.get(secondDirectory + "/" + originalFilename);

        if (!Files.exists(directory))
            Files.createDirectory(directory);

        while (Files.exists(dest)) {
            originalFilename = random.nextInt(10) + originalFilename;
            dest = Paths.get(directory + "/" + originalFilename);
            secondDest = Paths.get(secondDirectory + "/" + originalFilename);
        }

        Files.createFile(dest);
        multipartFile.transferTo(dest);

        Files.createFile(secondDest);
        multipartFile.transferTo(secondDest);

        return originalFilename;
    }
}
