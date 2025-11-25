package com.example.filestats;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GitIgnoreFilter {

    private final List<PathMatcher> matchers = new ArrayList<>();

    public GitIgnoreFilter(Path root) {
        Path gitignore = root.resolve(".gitignore");
        if (Files.exists(gitignore)) {
            try {
                List<String> lines = Files.readAllLines(gitignore);
                for (String line : lines) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;

                    String pattern = "glob:" + line;
                    matchers.add(FileSystems.getDefault().getPathMatcher(pattern));
                }
            } catch (IOException e) {
                System.err.println("Failed to read .gitignore: " + e.getMessage());
            }
        }
    }

    public boolean isIgnored(Path path) {
        if (path.getFileName().toString().equals(".gitignore")) return true;

        Path fileName = path.getFileName();
        if (fileName == null) return false;

        for (PathMatcher matcher : matchers) {
            if (matcher.matches(fileName)) {
                return true;
            }
        }
        return false;
    }
}
