package t1707m.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean(name = "bCryptPasswordEncoder")
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    }

    @Bean
    protected AuthenticationFailureHandler authenticationFailureHandler(){
        return new MyAuthenticationFailureHandle();
    }

    @Bean
    protected AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new MyAuthenticationSuccessHandle();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/").hasAnyAuthority("ADMIN,STUDENT")
                .antMatchers("/employee").hasAnyAuthority("EMPLOYEE")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureHandler(authenticationFailureHandler())
                .successHandler(authenticationSuccessHandler())
                .and()
                .logout()
                .permitAll();
    }

}
