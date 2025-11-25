package com.example.filestats.output;

import com.example.filestats.model.ExtStats;

import java.util.Map;

public class XmlFormatter implements Formatter {

    @Override
    public String format(Map<String, ExtStats> stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("<statistics>\n");

        for (Map.Entry<String, ExtStats> entry : stats.entrySet()) {
            String ext = entry.getKey();
            ExtStats st = entry.getValue();

            sb.append("  <extension name=\"").append(ext.isEmpty() ? "no_extension" : ext).append("\">\n");
            sb.append("    <files>").append(st.files).append("</files>\n");
            sb.append("    <sizeBytes>").append(st.sizeBytes).append("</sizeBytes>\n");
            sb.append("    <linesTotal>").append(st.linesTotal).append("</linesTotal>\n");
            sb.append("    <linesNonEmpty>").append(st.linesNonEmpty).append("</linesNonEmpty>\n");
            sb.append("    <commentLines>").append(st.commentLines).append("</commentLines>\n");
            sb.append("  </extension>\n");
        }

        sb.append("</statistics>\n");
        return sb.toString();
    }
}
