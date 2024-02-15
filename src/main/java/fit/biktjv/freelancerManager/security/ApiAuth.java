package fit.biktjv.freelancerManager.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;


public class ApiAuth extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private String apiKeyPrivate;
    private String apiKeyPublic;

    public ApiAuth(String apiKeyPrivate, String apiKeyPublic) {
        this.apiKeyPrivate = apiKeyPrivate;
        this.apiKeyPublic = apiKeyPublic;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(AUTHORIZATION_HEADER);
        String requestURI = request.getRequestURI();

        if (header != null && header.startsWith(BEARER_PREFIX)) {
            String apiKey = header.substring(BEARER_PREFIX.length());

            // use public key for modifyTask
            if ("/rest/task/assignTask".equals(requestURI) && this.apiKeyPublic.equals(apiKey)) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(apiKey, null, new ArrayList<>())
                );
            }
            if (!"/rest/task/assignTask".equals(requestURI) && this.apiKeyPrivate.equals(apiKey)) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(apiKey, null, new ArrayList<>())
                );
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().startsWith("/web/");
    }
}