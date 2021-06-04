package ua.nure.diploma.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/deliveryRequest/**").hasAuthority("ROLE_REQUEST_MANAGER")
                .antMatchers("/routeSheet/**").hasAuthority("ROLE_LOGIST")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/login/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setUserDetailsService();

        return authenticationProvider;
    }
}
