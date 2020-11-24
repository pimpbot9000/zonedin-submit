package projekti.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("dev")
@Configuration
@EnableWebSecurity
public class DevelopmentSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // mahdollistetaan h2-konsolin käyttö
        System.out.println("Enable development security configuration");
        
        //http.headers().frameOptions().sameOrigin();
        http.csrf().ignoringAntMatchers("/login", "/login/**");
        http.authorizeRequests()
                .antMatchers("/dev", "/dev/**").permitAll()
                .antMatchers("/h2-console", "/h2-console/**").permitAll()
                .antMatchers("/static", "/static/**").permitAll()
                .antMatchers("/signin", "/signin/**").permitAll()
                .antMatchers("/login", "/login/**").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll() 
                .failureUrl("/login?error=true")               
                .and()
                .logout();              
        
        
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
