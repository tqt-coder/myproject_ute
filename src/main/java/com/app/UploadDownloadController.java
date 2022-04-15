package com.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Sarvesh Kumar
 * Provide update and download server in the application.
 */

@RestController
public class UploadDownloadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadDownloadController.class);
    static StringBuilder data = new StringBuilder();

    public UploadDownloadController() {
    }

    @GetMapping({"/download/{fileName:.+}"})
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
        String content="";
        try {
            InputStream resource1 = new ClassPathResource("data/"+fileName).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource1));
            content = reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return ((BodyBuilder)ResponseEntity.ok().header("Content-Disposition", new String[]{"attachment; filename=\"" + fileName + "\""})).body(content);
    }

    @PostMapping({"/upload"})
    public void upload(@RequestParam MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer lines = new StringBuffer("");
            lines.append(bufferedReader.readLine());
            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                lines.append("\n"+line);
            }
            bufferedReader.close();

            logger.info("lines: {}", lines.toString());
            data.append(lines.toString());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }

    @GetMapping({"/data"})
    public String getData() {
        String result = data.toString();
        data = new StringBuilder();
        return result;
    }

}
