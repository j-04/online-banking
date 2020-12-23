package com.online.banking.config;

import com.online.banking.entity.Permission;
import com.online.banking.entity.Role;
import com.online.banking.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/*")
                .hasAuthority(Permission.ALL.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .login("admin")
                        .password(passwordEncoder().encode("admin"))
                        .name("Sergey")
                        .secondName("Vladimirovich")
                        .lastName("Dragosh")
                        .address("Akademika")
                        .phone(" 8 923 841 26 70")
                        .email("testadminemail@yahoo.com")
                        .role(Role.ADMIN)
                        .build()
        );
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
