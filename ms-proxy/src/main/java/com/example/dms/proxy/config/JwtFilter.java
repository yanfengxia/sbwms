package com.example.dms.proxy.config;

import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    final
    JwtConfig jwtConfig;

    public JwtFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(jwtConfig.getHeader());

        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtConfig.getPrefix(), "");

        try {

            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getKey())
                    .build()
                    .parseClaimsJws(token);
//            String moduleString = claims.getBody().get("modules").toString();
//            List<SimpleGrantedAuthority> modules = Stream.of(moduleString.split(",")).map(element -> new SimpleGrantedAuthority(element)).collect(Collectors.toList());
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(claims.getBody().get("sub"),null, modules);
//            SecurityContextHolder.getContext().setAuthentication(authToken);
            RequestContext.getCurrentContext().addZuulRequestHeader("user-context", claims.getBody().get("sub").toString());
            RequestContext.getCurrentContext().addZuulRequestHeader("roles", claims.getBody().get("roles").toString());

            log.info(claims.toString());

        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }

        filterChain.doFilter(request, response);

    }
}
