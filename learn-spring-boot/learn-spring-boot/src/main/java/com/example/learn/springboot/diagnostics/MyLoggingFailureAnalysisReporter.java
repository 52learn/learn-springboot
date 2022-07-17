package com.example.learn.springboot.diagnostics;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalysisReporter;

import java.util.HashMap;
import java.util.Map;

public class MyLoggingFailureAnalysisReporter implements FailureAnalysisReporter {
    private static final Log logger = LogFactory.getLog(MyLoggingFailureAnalysisReporter.class);
    @Override
    public void report(FailureAnalysis analysis) {
        if (logger.isDebugEnabled()) {
            logger.debug("Application failed to start due to an exception", analysis.getCause());
        }
        if (logger.isErrorEnabled()) {
            logger.error(buildMessage(analysis));
        }
    }
    private Map<Object,Object>  buildMessage(FailureAnalysis failureAnalysis) {
        Map<Object,Object> message = new HashMap<>();
        message.put("description",failureAnalysis.getDescription());
        message.put("action",failureAnalysis.getAction());

        return message;
    }
}
