package com.example.demo;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentParserImpl implements DocumentParser {
    @Override
    public Map<String, String> parse(File file) {
        // TODO: Use Apache Tika or Textract for real parsing
        Map<String, String> result = new HashMap<>();
        result.put("dummyKey", "dummyValue");
        return result;
    }
}