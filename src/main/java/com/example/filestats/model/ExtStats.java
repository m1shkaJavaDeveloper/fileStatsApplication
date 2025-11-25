package com.example.filestats.model;

public class ExtStats {
    public long files = 0;
    public long sizeBytes = 0;
    public long linesTotal = 0;
    public long linesNonEmpty = 0;
    public long commentLines = 0;

    public synchronized void add(FileStats fs) {
        files++;
        sizeBytes += fs.sizeBytes();
        linesTotal += fs.linesTotal();
        linesNonEmpty += fs.linesNonEmpty();
        commentLines += fs.commentLines();
    }
}
