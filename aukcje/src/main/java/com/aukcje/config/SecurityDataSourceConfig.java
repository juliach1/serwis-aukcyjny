package com.aukcje.config;

import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityDataSourceConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/rest/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/rejestracja").permitAll()
                .antMatchers("/rejestracja/**").permitAll()
                .antMatchers("/logowanie").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/logowanie")
                .loginProcessingUrl("/logowanie/autoryzacja")
                    .defaultSuccessUrl("/admin/uzytkownik")
                    .permitAll()
                .and()
                    .logout().permitAll().logoutSuccessUrl("/logowanie")
                .and().csrf().disable();

    }

    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/files/**");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider ap = new DaoAuthenticationProvider();
        ap.setUserDetailsService(userService);
        ap.setPasswordEncoder(passwordEncoder());
        return ap;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}