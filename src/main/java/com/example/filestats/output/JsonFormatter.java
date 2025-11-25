package com.example.filestats.output;

import com.example.filestats.model.ExtStats;

import java.util.Map;

public class JsonFormatter implements Formatter {

    @Override
    public String format(Map<String, ExtStats> stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        int count = 0;
        int size = stats.size();

        for (Map.Entry<String, ExtStats> entry : stats.entrySet()) {
            String ext = entry.getKey();
            ExtStats st = entry.getValue();

            sb.append("  \"").append(ext.isEmpty() ? "no_extension" : ext).append("\": {\n");
            sb.append("    \"files\": ").append(st.files).append(",\n");
            sb.append("    \"sizeBytes\": ").append(st.sizeBytes).append(",\n");
            sb.append("    \"linesTotal\": ").append(st.linesTotal).append(",\n");
            sb.append("    \"linesNonEmpty\": ").append(st.linesNonEmpty).append(",\n");
            sb.append("    \"commentLines\": ").append(st.commentLines).append("\n");
            sb.append("  }");
            if (++count < size) sb.append(",");
            sb.append("\n");
        }

        sb.append("}\n");
        return sb.toString();
    }
}
