package com.example.filestats.model;

import java.util.*;
import java.util.stream.Collectors;

public record Options(
        String path,
        boolean recursive,
        int maxDepth,
        int threads,
        Set<String> includeExt,
        Set<String> excludeExt,
        boolean gitIgnore,
        String output
) {

    public static Options parse(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Path argument required");

        String path = args[0];
        boolean recursive = false;
        int maxDepth = Integer.MAX_VALUE;
        int threads = 4;
        Set<String> include = new HashSet<>();
        Set<String> exclude = new HashSet<>();
        boolean gitIgnore = false;
        String output = "plain";

        for (int i = 1; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals("--recursive")) {
                recursive = true;

            } else if (arg.startsWith("--max-depth=")) {
                maxDepth = Integer.parseInt(arg.substring("--max-depth=".length()));

            } else if (arg.startsWith("--thread=")) {
                threads = Integer.parseInt(arg.substring("--thread=".length()));

            } else if (arg.startsWith("--include-ext=")) {
                include = splitExt(arg.substring("--include-ext=".length()));

            } else if (arg.startsWith("--exclude-ext=")) {
                exclude = splitExt(arg.substring("--exclude-ext=".length()));

            } else if (arg.equals("--git-ignore")) {
                gitIgnore = true;

            } else if (arg.startsWith("--output=")) {
                output = arg.substring("--output=".length());
            }
        }

        return new Options(path, recursive, maxDepth, threads, include, exclude, gitIgnore, output);
    }

    private static Set<String> splitExt(String s) {
        return Arrays.stream(s.split(","))
                .map(e -> e.startsWith(".") ? e.toLowerCase() : ("." + e.toLowerCase()))
                .collect(Collectors.toSet());
    }
}
