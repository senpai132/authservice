package com.devops2022.DislinktAuthService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;

import com.devops2022.DislinktAuthService.security.TokenUtils;
import com.devops2022.DislinktAuthService.security.auth.RestAuthenticationEntryPoint;
import com.devops2022.DislinktAuthService.security.auth.TokenAuthenticationFilter;
import com.devops2022.DislinktAuthService.services.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsServiceImpl jwtUserDetailsService;

    /*@Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;*/

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    // Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
        .antMatchers("/auth/**").permitAll()
        .antMatchers("/followshipadapter/**").permitAll()
        .antMatchers("/userprofileadapter/**").permitAll()
        .antMatchers("/notificationadapter/**").permitAll()
        .antMatchers("/jobopeningadapter/**").permitAll()
        .antMatchers("/messageadapter/**").permitAll()
        .anyRequest().authenticated().and()
        // za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
        .cors().and()

        // umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
        .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
                BasicAuthenticationFilter.class);
                
        http.csrf().disable();

        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers(HttpMethod.GET, "/test/comm");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }
}
