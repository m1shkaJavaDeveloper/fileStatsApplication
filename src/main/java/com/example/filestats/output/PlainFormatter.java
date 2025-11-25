package com.example.filestats.output;

import com.example.filestats.model.ExtStats;

import java.util.Map;

public class PlainFormatter implements Formatter {

    @Override
    public String format(Map<String, ExtStats> stats) {
        StringBuilder sb = new StringBuilder();

        stats.forEach((ext, st) -> {
            sb.append("Extension: ").append(ext.isEmpty() ? "<no extension>" : ext).append("\n");
            sb.append("  Files: ").append(st.files).append("\n");
            sb.append("  Size (bytes): ").append(st.sizeBytes).append("\n");
            sb.append("  Lines total: ").append(st.linesTotal).append("\n");
            sb.append("  Lines non-empty: ").append(st.linesNonEmpty).append("\n");
            sb.append("  Comment lines: ").append(st.commentLines).append("\n");
            sb.append("\n");
        });

        return sb.toString();
    }
}
