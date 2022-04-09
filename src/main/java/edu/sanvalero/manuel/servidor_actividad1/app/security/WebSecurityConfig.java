package edu.sanvalero.manuel.servidor_actividad1.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuración general de seguridad para Spring Security
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ServidorActividad1UserDetailsService userDetailsService;

    /**
     * Configuración de la autenticación de usuario
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Configuración de seguridad
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/movies/popular").permitAll()
                .antMatchers("/movies/popular/*").permitAll()
                .antMatchers("/reviews/list/*").permitAll()
                .antMatchers("/reviews/show/*").permitAll()
                .antMatchers("/admin/**").hasAuthority(Constants.ADMIN_ROLE).anyRequest()
                .authenticated().and().csrf().disable()
                .formLogin()
                .loginPage(Constants.LOGIN_URL)
                .defaultSuccessUrl(Constants.LOGIN_SUCCESS_URL)
                .failureUrl(Constants.LOGIN_FAILURE_URL)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher(Constants.LOGOUT_URL))
                .logoutSuccessUrl(Constants.LOGOUT_SUCCESS_URL);
    }

    /**
     * Permite el acceso al contenido estático
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/resources/**",
                "/static/**",
                "/templates/**",
                "/css/**",
                "/js/**",
                "/images/**",
                "/fonts/**",
                "/webjars/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

