package com.malyshev2202.store.backend.config;



import com.malyshev2202.store.backend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {
   @Autowired
    private DataSource dataSource;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF is handled by Vaadin: https://vaadin.com/framework/security
                .exceptionHandling().accessDeniedPage("/accessDenied")
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and().logout().logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                // allow Vaadin URLs and the login URL without authentication
                .regexMatchers("/frontend/.*", "/VAADIN/.*", "/login", "/accessDenied","/","/reg","/forget").permitAll()
                .regexMatchers(HttpMethod.POST, "/\\?v-r=.*").permitAll()
                // deny any other URL until authenticated
                .antMatchers("/**").fullyAuthenticated()
            /*
             Note that anonymous authentication is enabled by default, therefore;
             SecurityContextHolder.getContext().getAuthentication().isAuthenticated() always will return true.
             Look at LoginView.beforeEnter method.
             more info: https://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/anonymous.html
             */
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());}


    /**
     * Expose the AuthenticationManager (to be used in LoginView)
     *
     * @return
     * @throws Exception
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

