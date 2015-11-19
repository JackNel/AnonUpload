package com.theironyard.controllers;

import com.theironyard.entities.AnonFile;
import com.theironyard.services.AnonFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Jack on 11/18/15.
 */

@RestController
public class AnonUploadController {
    @Autowired
    AnonFileRepository files;

    @RequestMapping("/files")
    public List<AnonFile> getFiles() {

        return (List<AnonFile>) files.findAll();
    }


    @RequestMapping("/upload")
    public void upload(HttpServletResponse response, MultipartFile file, boolean isPerm) throws IOException {
        File f = File.createTempFile("file", file.getOriginalFilename(), new File("public"));
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());

        AnonFile anonFile = new AnonFile();
        anonFile.originalName = file.getOriginalFilename();
        anonFile.name = f.getName();
        anonFile.uploadTime = LocalDateTime.now();
        anonFile.isPerm = isPerm;
        files.save(anonFile);

        if (files.findByIsPermFalse().size() > 3) {
            List<AnonFile> tempList = files.findByIsPermFalse();
            AnonFile tempFile = tempList.get(0);
            files.delete(tempFile);

            File diskFile = new File("public", tempFile.name);
            diskFile.delete();

        }
        response.sendRedirect("/");
    }
}
