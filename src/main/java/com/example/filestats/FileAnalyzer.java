package com.example.filestats;

import com.example.filestats.model.FileStats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileAnalyzer {

    public FileStats analyze(Path path) throws IOException {
        long sizeBytes = Files.size(path);

        long linesTotal = 0;
        long nonEmpty = 0;
        long comments = 0;

        try (var reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                linesTotal++;

                String trimmed = line.trim();
                if (!trimmed.isEmpty())
                    nonEmpty++;

                // Java comment
                if (trimmed.startsWith("//"))
                    comments++;

                // Bash comment
                if (trimmed.startsWith("#"))
                    comments++;
            }
        }

        return new FileStats(sizeBytes, linesTotal, nonEmpty, comments);
    }
}
