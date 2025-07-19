package com.example.demo;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface DocumentParser {
    Map<String, String> parse(MultipartFile file);
}