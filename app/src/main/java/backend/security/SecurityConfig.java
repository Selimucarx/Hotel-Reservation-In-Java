package backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF korumasını devre dışı bırakın (geçici olarak)
                .authorizeRequests()
                .antMatchers("/api/users/{id}").permitAll() // Giriş API'sine herkese izin verin
                .antMatchers("/api/users/reset-password").permitAll()
                .antMatchers("/api/users/login").permitAll() // Giriş API'sine herkese izin verin
                .antMatchers("/api/users/register").permitAll() // Giriş API'sine herkese izin verin
                .antMatchers("/api/admin/login").hasRole("admin") // Sadece adminin girebilcegi api
                .antMatchers("/api/**").authenticated() // Diğer API'lar için kimlik doğrulama gerekiyor
                .anyRequest().permitAll() // Diğer istekler için yetkilendirme yok
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}1234").roles("admin")
                .and()
                .withUser("selim").password("{noop}1234").roles("user");
    }




}
