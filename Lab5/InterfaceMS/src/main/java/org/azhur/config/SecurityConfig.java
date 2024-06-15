package org.azhur.config;


import org.azhur.kafka.producers.KafkaMyUserDtoProducer;
import org.azhur.kafka.producers.KafkaOwnerDtoProducer;
import org.azhur.services.MyUserDetailService;
import org.azhur.services.OwnerRequestService;
import org.azhur.services.UserRequestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(KafkaMyUserDtoProducer kafkaMyUserDtoProducer,
                                                 UserRequestService userRequestService) {
        return new MyUserDetailService(kafkaMyUserDtoProducer, userRequestService, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("interface-api/v1/users/**").permitAll()
                        .requestMatchers("interface-api/v1/owner/**","interface-api/v1/cat/**").authenticated())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(KafkaMyUserDtoProducer kafkaMyUserDtoProducer,
                                                         UserRequestService userRequestService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(kafkaMyUserDtoProducer, userRequestService));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}