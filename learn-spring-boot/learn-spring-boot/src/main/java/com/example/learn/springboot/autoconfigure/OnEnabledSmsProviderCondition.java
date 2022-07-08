package com.example.learn.springboot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.Annotation;

class OnEnabledSmsProviderCondition extends SpringBootCondition {
    private final String prefix;

    private final Class<? extends Annotation> annotationType;

    OnEnabledSmsProviderCondition(){
        this("sms.",ConditionalOnEnabledSmsProvider.class);
    }
    protected OnEnabledSmsProviderCondition(String prefix, Class<? extends Annotation> annotationType) {
        this.prefix = prefix;
        this.annotationType = annotationType;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes
            .fromMap(metadata.getAnnotationAttributes(this.annotationType.getName()));
        String endpointName = annotationAttributes.getString("value");
        ConditionOutcome outcome = getConditionOutcome(context, endpointName);
        if (outcome != null) {
            return outcome;
        }
        return getDefaultOutcome(context, annotationAttributes);
    }

    protected ConditionOutcome getConditionOutcome(ConditionContext context, String endpointName) {
        Environment environment = context.getEnvironment();
        String enabledProperty = this.prefix + endpointName + ".enabled";
        if (environment.containsProperty(enabledProperty)) {
            boolean match = environment.getProperty(enabledProperty, Boolean.class, true);
            return new ConditionOutcome(match, ConditionMessage.forCondition(this.annotationType)
                .because(this.prefix + endpointName + ".enabled is " + match));
        }
        return null;
    }
    protected ConditionOutcome getDefaultOutcome(ConditionContext context, AnnotationAttributes annotationAttributes) {
        boolean match = Boolean
            .parseBoolean(context.getEnvironment().getProperty(this.prefix + "defaults.enabled", "true"));
        return new ConditionOutcome(match, ConditionMessage.forCondition(this.annotationType)
            .because(this.prefix + "defaults.enabled is considered " + match));
    }

}
