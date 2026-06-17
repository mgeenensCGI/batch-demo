package com.example.batch_demo.customers.utils;

import java.nio.file.Path;

public class ExportUtils {

    private ExportUtils() {}

    public static String buildExportFileName(String baseName, String extension) {
        return String.join("_", baseName, String.valueOf(System.currentTimeMillis())) + extension;
    }

    public static Path buildExportPath(String baseDirectory, String subDirectory, String fileName) {
        return Path.of(baseDirectory, subDirectory, fileName).normalize();
    }
}
