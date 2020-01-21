package de.othr.se.grj46992.bikerator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] ALLOW_ACCESS_WITHOUT_AUTHENTICATION = {
            "/images/**", "/", "/login", "/signup", "/register", "/configuration", "/createConfiguration/start", "/createConfiguration/next", "/createConfiguration/back", "/createConfiguration/complete", "/overview", "/restapi/customers/emails"};
    @Autowired
    @Qualifier("labresources")
    private UserDetailsService userSecurityService;
    @Autowired
    private SecurityUtilities securityUtilities;

    private BCryptPasswordEncoder passwordEncoder() {
        return securityUtilities.passwordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(ALLOW_ACCESS_WITHOUT_AUTHENTICATION)
                .permitAll().anyRequest().authenticated()
                .and().csrf().disable();
        http
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/user/account")
                .failureUrl("/login?error")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout")
                .deleteCookies("remeber-me")
                .permitAll()
                .and()
                .rememberMe();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }
}