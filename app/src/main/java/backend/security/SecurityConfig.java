package backend.security;

import backend.service.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/index").permitAll()
                .antMatchers("/api/hotel/").permitAll()
                .antMatchers("/api/hotel/{id}").permitAll()
                .antMatchers("/api/hotel/rooms").permitAll()
                .antMatchers("/api/hotel/rooms/{id}").permitAll()
                .antMatchers("/api/users/register").permitAll()
                .antMatchers("/api/users/login").permitAll()
                .antMatchers("/api/reservations/check-availability").permitAll()
                .antMatchers("/api/reservations").hasRole("USER")
                .antMatchers("/api/reservations/{id}").hasRole("USER")
                .antMatchers("/api/users/reset-password").hasRole("USER")
                .antMatchers("/api/admin-page").hasRole("ADMIN")
                .antMatchers("/api/admin/login").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
