package fit.biktjv.freelancerManager;

import fit.biktjv.freelancerManager.security.ApiAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String API_KEY_PRIVATE = "TOKEN";
    private static final String API_KEY_PUBLIC = "TOKEN-PUBLIC";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        RequestMatcher restApiMatcher = new AntPathRequestMatcher("/rest/**");

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(restApiMatcher))
                .authorizeRequests(authz -> authz
                        .requestMatchers("/web/**").permitAll()
                        .requestMatchers("/rest/**").authenticated()
                        )
                .addFilterBefore(new ApiAuth(API_KEY_PRIVATE, API_KEY_PUBLIC), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
