package com.darmokhval.Backend_part.controller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


//Not important, because for now I'm using index.html.
@Controller
public class FrontendController {

    @GetMapping("/front/{filename}")
    public ResponseEntity<String> getHtml(@PathVariable String filename) throws IOException {
        Resource resource = new ClassPathResource("static/" + filename + ".html");
        Path path = resource.getFile().toPath();
        String content = Files.readString(path);

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(content);
    }
}
