package guru.sfg.brewery.config;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
                        .csrf().disable();
        httpSecurity
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers(
                                    "/",
                                    "/webjars/**",
                                    "/login",
                                    "/resources/**",
                                    "/beers/find",
                                    "/beers*"
                            ).permitAll()
                            .antMatchers(HttpMethod.GET,
                                    "/api/v1/beer/**"
                            ).permitAll()
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                })
                .authorizeRequests()
                .anyRequest()
                .authenticated().and()
                .formLogin().and()
                .httpBasic();
    }
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring")
                .password("{bcrypt}$2a$10$My87Kc9M0018R1oyqOFGlOPcihW6FFzb5buRzfQ9bUm4rZm.0Ksjy")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{sha256}71b9068383ef35c92454f7b39cd9b08009f292bfc4d4317f5d73d236f549b5535b7fffeb88224bee")
                .roles("USER")
                .and()
                .withUser("scott")
                .password("{bcrypt15}$2a$15$X2aYSjfhzlyEDwH4uXtGjeWi60X9ILrsbvAjXqsL6J35BmbBUcx4e")
                .roles("CUSTOMER");
    }
}
