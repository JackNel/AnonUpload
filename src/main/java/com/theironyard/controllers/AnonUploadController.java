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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void upload(HttpServletResponse response, MultipartFile file, boolean isPerm, String comment) throws IOException {
        File f = File.createTempFile("file", file.getOriginalFilename(), new File("public"));
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());

        AnonFile anonFile = new AnonFile();
        anonFile.originalName = file.getOriginalFilename();
        anonFile.name = f.getName();
        anonFile.uploadTime = LocalDateTime.now();
        anonFile.isPerm = isPerm;
        anonFile.comment = comment;
        files.save(anonFile);




         //My Way to do it:
        if (files.findByIsPermFalse().size() > 3) {
            List<AnonFile> tempList = files.findByIsPermFalse();
            AnonFile tempFile = tempList.get(0);
            files.delete(tempFile);

            File diskFile = new File("public", tempFile.name);
            diskFile.delete();

        }
/*
        //Another way to do it:
        List<AnonFile> filesList = (List<AnonFile>) files.findAll();
        List<AnonFile> filteredList = new ArrayList();
        for (AnonFile af : filteredList) {
            if (!af.isPerm) {
                filteredList.add(af);
            }
        }
        if (filteredList.size() > 3) {
            AnonFile af = filesList.get(0);
            files.delete(af);

            File diskFile = new File("public", af.name);
            diskFile.delete();
        }


        // Another example of using a Repo query
        ArrayList<AnonFile> filteredList = (ArrayList<AnonFile>) files.findByIsPermOrderByIdAsc(false);
        if (filteredList.size() > 3) {
            AnonFile af = filteredList.get(0);
            files.delete(af);

            File diskFile = new File("public", af.name);
            diskFile.delete();
        }


        // Example using streams
        List<AnonFile> stuff = (List<AnonFile>) files.findAll();
        List<AnonFile> nonPermFiles = stuff.stream()
                .filter(old -> !old.isPerm)
                .collect(Collectors.toList());

        if (nonPermFiles.size() > 3) {
            files.delete(nonPermFiles.get(0));
        }
*/
        response.sendRedirect("/");
    }
}
