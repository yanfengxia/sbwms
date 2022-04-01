package com.example.dms.proxy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    final
    JwtFilter jwtFilter;

    public SecurityConfiguration(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests((authorizeRequests) ->
//                authorizeRequests.antMatchers(HttpMethod.POST, "/oauth/**").permitAll()
//                        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
//                        .anyRequest().authenticated()
//        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//        http.csrf().disable()
//
//                .authorizeRequests()
//
//                .antMatchers("/oauth/**").permitAll()
//                .antMatchers("/actuator/**").permitAll()
//                .antMatchers("/**").authenticated();

        http.csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().anonymous()
                .and().exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/ms-auth/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/**").authenticated();

    }
}