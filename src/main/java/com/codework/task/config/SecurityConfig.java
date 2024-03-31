package com.codework.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private UserDetailsService userDetailsService;
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // http://localhost:8080/login,signin
        // http://localhost:8080/user/addnotes,viewnotes

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/**").permitAll())
                        .formLogin(login -> login.loginPage("/signin")
                        .loginProcessingUrl("/userLogin")
                        .defaultSuccessUrl("/user/addNotes"))
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/signin?logout").permitAll())
				.exceptionHandling(handling -> handling.accessDeniedPage("/login?error=Access Denied"))


                // .formLogin(login -> login.loginPage("/signin")
                //         .loginProcessingUrl("/userLogin")
                //         .defaultSuccessUrl("/user/addNotes")
                //         .permitAll())
                //         .logout((logout) -> logout.logoutSuccessUrl("/logout"))
                .sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation().migrateSession()
                        .maximumSessions(1)
                        
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/signin?expired=true")); // Redirect to login page on session expiration
                        
    
        return http.build();

    }
    

}
