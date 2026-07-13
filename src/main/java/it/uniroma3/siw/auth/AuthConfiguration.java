package it.uniroma3.siw.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import it.uniroma3.siw.model.Credentials;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?")
                // ECCO LA RIGA MAGICA DEL TORNEO CHE MANCAVA!
                .passwordEncoder(new BCryptPasswordEncoder()); 
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. URL riservati all'Amministratore (al posto del Torneo, qui abbiamo /admin/**)
                        .requestMatchers("/admin/**").hasAuthority(Credentials.ADMIN_ROLE)
                        
                        // 2. URL pubblici (Visualizzazione)
                        .requestMatchers(HttpMethod.GET, "/", "/index", "/piatti", "/tappe", "/recensioni", "/api/recensioni", "/css/**", "/images/**", "/error").permitAll()
                        
                        // 3. URL per Login e Registrazione
                        .requestMatchers(HttpMethod.GET, "/register", "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                        
                        // 4. Tutto il resto richiede l'accesso (come per le future recensioni)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                );
        return httpSecurity.build();
    }
}