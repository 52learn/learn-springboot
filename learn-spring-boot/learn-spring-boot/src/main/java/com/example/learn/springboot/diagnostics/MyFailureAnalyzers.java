package com.example.learn.springboot.diagnostics;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringBootExceptionReporter;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalysisReporter;
import org.springframework.boot.diagnostics.FailureAnalyzer;
import org.springframework.boot.util.Instantiator;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.log.LogMessage;

import java.util.List;
@Slf4j
public class MyFailureAnalyzers implements SpringBootExceptionReporter {
    private static final Log logger = LogFactory.getLog(MyFailureAnalyzers.class);
    private final ClassLoader classLoader;

    private final List<FailureAnalyzer> analyzers;

    MyFailureAnalyzers(ConfigurableApplicationContext context) {
        this(context, SpringFactoriesLoader.loadFactoryNames(FailureAnalyzer.class, getClassLoader(context)));
    }
    MyFailureAnalyzers(ConfigurableApplicationContext context, List<String> classNames) {
        this.classLoader = getClassLoader(context);
        this.analyzers = loadFailureAnalyzers(classNames, context);
    }

    @Override
    public boolean reportException(Throwable failure) {
        logger.info("-----------------com.example.learn.springboot.diagnostics.MyFailureAnalyzers.reportException-----------------");
        FailureAnalysis analysis = analyze(failure, this.analyzers);
        Throwable throwable = analysis.getCause();
        logger.fatal("Application startUp throwable：",throwable);

        //report(analysis, this.classLoader);
        return false;
    }


    private FailureAnalysis analyze(Throwable failure, List<FailureAnalyzer> analyzers) {
        for (FailureAnalyzer analyzer : analyzers) {
            try {
                FailureAnalysis analysis = analyzer.analyze(failure);
                if (analysis != null) {
                    return analysis;
                }
            }
            catch (Throwable ex) {
                logger.trace(LogMessage.format("FailureAnalyzer %s failed", analyzer), ex);
            }
        }
        return null;
    }
    private boolean report(FailureAnalysis analysis, ClassLoader classLoader) {
        List<FailureAnalysisReporter> reporters = SpringFactoriesLoader.loadFactories(FailureAnalysisReporter.class,
                classLoader);
        if (analysis == null || reporters.isEmpty()) {
            return false;
        }
        for (FailureAnalysisReporter reporter : reporters) {
            reporter.report(analysis);
        }
        return true;
    }

    private List<FailureAnalyzer> loadFailureAnalyzers(List<String> classNames,
                                                       ConfigurableApplicationContext context) {
        Instantiator<FailureAnalyzer> instantiator = new Instantiator<>(FailureAnalyzer.class,
                (availableParameters) -> {
                    if (context != null) {
                        availableParameters.add(BeanFactory.class, context.getBeanFactory());
                        availableParameters.add(Environment.class, context.getEnvironment());
                    }
                }, new MyFailureAnalyzers.LoggingInstantiationFailureHandler());
        List<FailureAnalyzer> analyzers = instantiator.instantiate(this.classLoader, classNames);
        // return handleAwareAnalyzers(analyzers, context);
        return analyzers;
    }
    private static ClassLoader getClassLoader(ConfigurableApplicationContext context) {
        return (context != null) ? context.getClassLoader() : null;
    }
    static class LoggingInstantiationFailureHandler implements Instantiator.FailureHandler {

        @Override
        public void handleFailure(Class<?> type, String implementationName, Throwable failure) {
            logger.trace(LogMessage.format("Skipping %s: %s", implementationName, failure.getMessage()));
        }

    }
}
