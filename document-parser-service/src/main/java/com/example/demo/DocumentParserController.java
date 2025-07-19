package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DocumentParserController {
    private final DocumentParser documentParser;

    @PostMapping("/parse")
    public ResponseEntity<Map<String, String>> parse(@RequestParam("file") MultipartFile file) throws IOException {
        File temp = File.createTempFile("kyc-upload", file.getOriginalFilename());
        file.transferTo(temp);
        Map<String, String> result = documentParser.parse(temp);
        temp.delete();
        return ResponseEntity.ok(result);
    }
}