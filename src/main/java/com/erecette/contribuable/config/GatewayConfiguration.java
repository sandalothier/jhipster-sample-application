package com.erecette.contribuable.config;

import io.github.jhipster.config.JHipsterProperties;

import com.erecette.contribuable.gateway.accesscontrol.AccessControlFilter;
import com.erecette.contribuable.gateway.responserewriting.SwaggerBasePathRewritingFilter;
import com.erecette.contribuable.gateway.ratelimiting.RateLimitingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Configuration
    public static class SwaggerBasePathRewritingConfiguration {

        @Bean
        public SwaggerBasePathRewritingFilter swaggerBasePathRewritingFilter(){
            return new SwaggerBasePathRewritingFilter();
        }
    }

    @Configuration
    public static class AccessControlFilterConfiguration {

        @Bean
        public AccessControlFilter accessControlFilter(RouteLocator routeLocator, JHipsterProperties jHipsterProperties){
            return new AccessControlFilter(routeLocator, jHipsterProperties);
        }
    }

    /**
     * Configures the Zuul filter that limits the number of API calls per user.
     * <p>
     * This uses Bucket4J to limit the API calls, see {@link com.erecette.contribuable.gateway.ratelimiting.RateLimitingFilter}.
     */
    @Configuration
    @ConditionalOnProperty("jhipster.gateway.rate-limiting.enabled")
    public static class RateLimitingConfiguration {

        private final JHipsterProperties jHipsterProperties;

        public RateLimitingConfiguration(JHipsterProperties jHipsterProperties) {
            this.jHipsterProperties = jHipsterProperties;
        }
        @Bean
        public RateLimitingFilter rateLimitingFilter() {
            return new RateLimitingFilter(jHipsterProperties);
        }
    }
}
