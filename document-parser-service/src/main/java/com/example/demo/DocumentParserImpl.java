package com.example.demo;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DocumentParserImpl implements DocumentParser {
    private static final Tika tika = new Tika();

    @Override
    public Map<String, String> parse(MultipartFile file) {
        Map<String, String> result = new HashMap<>();
        try {
            String text = tika.parseToString(file.getInputStream(), new Metadata());
            // Simple regex-based extraction for demo purposes
            result.put("documentType", extractField(text, "Document Type[:\n\s]*([A-Za-z0-9 ]+)", "Unknown"));
            result.put("clientName", extractField(text, "Name[:\n\s]*([A-Za-z ]+)", "Unknown"));
            result.put("dateOfBirth", extractField(text, "Date of Birth[:\n\s]*([0-9\-/]+)", "Unknown"));
            result.put("documentNumber", extractField(text, "Document Number[:\n\s]*([A-Za-z0-9\- ]+)", "Unknown"));
            result.put("nationality", extractField(text, "Nationality[:\n\s]*([A-Za-z ]+)", "Unknown"));
        } catch (IOException e) {
            result.put("error", "Failed to parse document: " + e.getMessage());
        }
        return result;
    }

    private String extractField(String text, String regex, String defaultValue) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return defaultValue;
    }
}