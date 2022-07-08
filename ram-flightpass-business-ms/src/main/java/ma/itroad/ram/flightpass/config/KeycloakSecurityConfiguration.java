package ma.itroad.ram.flightpass.config;

import org.keycloak.adapters.springboot.KeycloakBaseSpringBootConfiguration;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import java.util.List;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@EnableAutoConfiguration(exclude = {UserDetailsServiceAutoConfiguration.class})
//@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class KeycloakSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    @Value("${keycloak-add-on.unprotectedpaths}")
    private List<String> unprotectedPaths;
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Configuration
    static class CustomKeycloakBaseSpringBootConfiguration extends KeycloakBaseSpringBootConfiguration {
    }

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        // required for bearer-only applications.
        return new NullAuthenticatedSessionStrategy();
    }

    /**
     * Prevent double bean declaration
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(KeycloakAuthenticationProcessingFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();
        // simple Authority Mapper to avoid ROLE_

        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    /**
     * Spring security specific configuration
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        // add cors options
        http
                .csrf().disable()
                .sessionManagement()
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                .and()
                .addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter.class)
                .addFilterBefore(keycloakAuthenticationProcessingFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(keycloakAuthenticatedActionsRequestFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(keycloakSecurityContextRequestFilter(), SecurityContextHolderAwareRequestFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authorizeRequests().anyRequest().permitAll();
        http.cors().disable();
        http.headers()
                .contentSecurityPolicy("default-src 'self';connect-src 'self' ;frame-src 'self' data:; script-src 'self' ; style-src 'self' ; img-src 'self' data:; font-src 'self' data:")
                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
                .and()
                .frameOptions()
                .deny();


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        if (!unprotectedPaths.isEmpty()) {
            web.ignoring().antMatchers(unprotectedPaths.toArray(new String[unprotectedPaths.size()]));
        }
        // No security restriction for static resources : swagger ui & spring fox
        configureStaticResourcesAccessControlRules(web);
    }

    private void configureStaticResourcesAccessControlRules(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs/**");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/webjars/**/*");
        web.ignoring().antMatchers("/swagger-resources/webjars/**");
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new RealmRoleConverter());
        return jwtConverter;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }

}
