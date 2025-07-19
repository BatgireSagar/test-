package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DocumentParserController {
    private final DocumentParser documentParser;

    @PostMapping(value = "/parse", consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, Map<String, String>>> parse(@RequestParam("files") List<MultipartFile> files) {
        Map<String, Map<String, String>> results = new HashMap<>();
        for (MultipartFile file : files) {
            results.put(file.getOriginalFilename(), documentParser.parse(file));
        }
        return ResponseEntity.ok(results);
    }
}