package com.example.filestats;

import com.example.filestats.model.ExtStats;
import com.example.filestats.model.FileStats;
import com.example.filestats.model.Options;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class FileProcessor {

    private final FileAnalyzer analyzer = new FileAnalyzer();

    public Map<String, ExtStats> process(Path root, Options opts) throws IOException {
        Map<String, ExtStats> result = new ConcurrentHashMap<>();

        ExecutorService exec = Executors.newFixedThreadPool(
                opts.threads(),
                Thread.ofVirtual().factory()
        );

        int depth = opts.recursive() ? opts.maxDepth() : 1;

        GitIgnoreFilter gitFilter = opts.gitIgnore() ? new GitIgnoreFilter(root) : null;

        try (Stream<Path> stream = Files.walk(root, depth)) {
            stream
                    .filter(Files::isRegularFile)
                    .filter(path -> filterExt(path, opts))
                    .filter(path -> gitFilter == null || !gitFilter.isIgnored(path))
                    .forEach(path ->
                            exec.submit(() -> {
                                try {
                                    String ext = getExt(path);
                                    FileStats fs = analyzer.analyze(path);

                                    result
                                            .computeIfAbsent(ext, k -> new ExtStats())
                                            .add(fs);

                                } catch (Exception e) {
                                    System.err.println("Failed to analyze " + path + ": " + e);
                                }
                            })
                    );
        }

        exec.shutdown();
        try {
            exec.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException ignored) {}

        return result;
    }

    private boolean filterExt(Path path, Options opts) {
        String ext = getExt(path);

        if (!opts.includeExt().isEmpty() && !opts.includeExt().contains(ext))
            return false;

        if (!opts.excludeExt().isEmpty() && opts.excludeExt().contains(ext))
            return false;

        return true;
    }

    private String getExt(Path path) {
        String name = path.getFileName().toString();
        int i = name.lastIndexOf('.');
        return i == -1 ? "" : name.substring(i).toLowerCase();
    }
}
