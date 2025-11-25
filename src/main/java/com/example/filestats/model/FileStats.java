package com.example.filestats.model;

public record FileStats(
        long sizeBytes,
        long linesTotal,
        long linesNonEmpty,
        long commentLines
) {}
