package org.nicktorwald.todo.config;

import java.util.Locale;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.validation.ValidationConfig;

@Provider
public class ValidationConfigResolver implements ContextResolver<ValidationConfig> {

    private final ValidationConfig defaultValidationConfig;

    public ValidationConfigResolver(@Context HttpHeaders requestHeaders) {
        defaultValidationConfig = new ValidationConfig().messageInterpolator(new LocaleMessageInterpolator(
                Validation.byDefaultProvider().configure().getDefaultMessageInterpolator(),
                requestHeaders));
    }
    
    @Override
    public ValidationConfig getContext(Class<?> type) {
        return defaultValidationConfig;
    }
    
    public static class LocaleMessageInterpolator implements MessageInterpolator {

        private final MessageInterpolator defaultInterpolator;
        private final HttpHeaders requestHeaders;

        public LocaleMessageInterpolator(MessageInterpolator defaultInterpolator,
                                         HttpHeaders requestHeaders) {
            this.defaultInterpolator = defaultInterpolator;
            this.requestHeaders = requestHeaders;
        }
        
        @Override
        public String interpolate(String messageTemplate, Context context) {
            return defaultInterpolator.interpolate(messageTemplate,
                    context, 
                    requestHeaders.getAcceptableLanguages().get(0));
        }

        @Override
        public String interpolate(String messageTemplate, Context context, Locale locale) {
            return defaultInterpolator.interpolate(messageTemplate, context, locale);
        }
        
    }
    
}
