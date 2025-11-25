package com.example.filestats;

import com.example.filestats.model.Options;
import com.example.filestats.model.ExtStats;
import com.example.filestats.output.*;

import java.nio.file.Path;
import java.util.Map;

public class FileStatsApplication {

    public void run(String[] args) {
        try {
            Options opts = Options.parse(args);

            FileProcessor processor = new FileProcessor();
            Map<String, ExtStats> stats = processor.process(Path.of(opts.path()), opts);

            Formatter formatter;
            switch (opts.output().toLowerCase()) {
                case "json" -> formatter = new JsonFormatter();
                case "xml" -> formatter = new XmlFormatter();
                default -> formatter = new PlainFormatter();
            }

            System.out.println(formatter.format(stats));

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
