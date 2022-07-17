package com.example.learn.springboot.diagnostics;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class ProjectConstraintViolationFailureAnalyzer extends AbstractFailureAnalyzer {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, Throwable cause) {
        StringBuilder description = new StringBuilder("Project Constraint : ");
        description.append("1. classpath:/META-INF/my.properties");
        String action = "pls make sure resource [classpath:/META-INF/my.properties] exist ";
        FailureAnalysis failureAnalysis = new FailureAnalysis(description.toString(),action,cause);
        return failureAnalysis;
    }
}
