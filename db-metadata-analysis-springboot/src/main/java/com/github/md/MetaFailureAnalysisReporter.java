package com.github.md;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalysisReporter;
import org.springframework.util.StringUtils;

/**
 * PLAY FOR FUN
 *
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-analysis-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MetaFailureAnalysisReporter implements FailureAnalysisReporter {

    @Override
    public void report(FailureAnalysis failureAnalysis) {
        if (log.isDebugEnabled()) {
            log.debug("Application failed to start due to an exception", failureAnalysis.getCause());
        }
        if (log.isErrorEnabled()) {
            log.error(buildMessage(failureAnalysis));
        }
    }

    private String buildMessage(FailureAnalysis failureAnalysis) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%n%n"));
        builder.append(String.format("***************************%n"));
        builder.append(String.format("Custom error output%n"));
        builder.append(String.format("***************************%n"));
        builder.append(String.format("APPLICATION FAILED TO START%n"));
        builder.append(String.format("***************************%n%n"));
        builder.append(String.format("Description:%n%n"));
        builder.append(String.format("%s%n", failureAnalysis.getDescription()));
        if (StringUtils.hasText(failureAnalysis.getAction())) {
            builder.append(String.format("%nAction:%n%n"));
            builder.append(String.format("%s%n", failureAnalysis.getAction()));
        }
        return builder.toString();
    }
}
