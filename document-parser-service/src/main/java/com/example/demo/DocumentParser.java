package com.example.demo;

import java.io.File;
import java.util.Map;

public interface DocumentParser {
    Map<String, String> parse(File file);
}