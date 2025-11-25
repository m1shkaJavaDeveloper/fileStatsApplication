package com.example.filestats.output;

import com.example.filestats.model.ExtStats;

import java.util.Map;

public interface Formatter {
    String format(Map<String, ExtStats> stats);
}
