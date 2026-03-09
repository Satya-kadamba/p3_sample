package com.revplay.analytics.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                var request = attributes.getRequest();

                // Propagate user context headers
                String userId = request.getHeader("X-User-Id");
                String userRole = request.getHeader("X-User-Role");

                if (userId != null) {
                    requestTemplate.header("X-User-Id", userId);
                }
                if (userRole != null) {
                    requestTemplate.header("X-User-Role", userRole);
                }

                logger.debug("Propagating headers to Feign client: userId={}, role={}", userId, userRole);
            }
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    public static class CustomErrorDecoder implements ErrorDecoder {

        private final ErrorDecoder defaultDecoder = new Default();

        @Override
        public Exception decode(String methodKey, feign.Response response) {
            logger.error("Feign client error: method={}, status={}", methodKey, response.status());

            if (response.status() == 404) {
                return new feign.FeignException.NotFound(
                        "Resource not found",
                        response.request(),
                        response.body() != null ? response.body().toString().getBytes() : null,
                        response.headers()
                );
            }

            if (response.status() == 503) {
                return new feign.FeignException.ServiceUnavailable(
                        "Service unavailable",
                        response.request(),
                        response.body() != null ? response.body().toString().getBytes() : null,
                        response.headers()
                );
            }

            return defaultDecoder.decode(methodKey, response);
        }
    }
}
