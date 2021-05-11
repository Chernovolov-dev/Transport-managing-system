package ua.nure.diploma.configs;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/request/**").hasAuthority("ROLE_REQUEST_MANAGER")
                .antMatchers("/routeSheet/**").hasAuthority("ROLE_LOGIST")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/login/**");
    }


}
