package com.distributed.prject.distributedweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @Autowired
    private Environment environment;
    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/public/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()

                .antMatchers("/jsondoc**","/files/**","/api/v1/employee/assign-password**","/api/v1/employee/forget-password","/api/v1/employee/login-username-password","/api/v1/vacation/uploadFile**","/sick-report**", "/sick-success**" , "/password-reset**" , "/new-password/**","/new-password**", "/webjars/**","/**","/sick-report/**", "/docs/**","/api/v1/employee/login", "/api/v1/employee/login-gmail","/login" ,"/api/v1/employee/botLogin","/api/v1/toffy/**","/botLogin","/reports**","/login_email","/api/v1/tenant","/toffy","/api/v1/tenant/signup","/api/v1/tenant/**/logo","/api/v1/employee/getEmailFromSlack*","/api/v1/toffy/contact","/hello").permitAll()
                //.antMatchers(HttpMethod.POST, "/api/v1/employee").hasAnyAuthority("ADMIN", "HR_ADMIN","HR_OFFICER")
                //.antMatchers(HttpMethod.PUT, "/api/v1/employee").hasAnyAuthority("ADMIN", "HR_ADMIN")
                //.antMatchers("/api/v1/w-hours").hasAnyAuthority("ADMIN", "HR_ADMIN")
                //.antMatchers(HttpMethod.POST, "/api/v1/vacation/impose").hasAnyAuthority("ADMIN")
                //.antMatchers(HttpMethod.PUT, "**/managerApproval/**").hasAnyAuthority("ADMIN", "HR_ADMIN","APPROVER")
                //.antMatchers(HttpMethod.POST,"/api/v1/holidays").hasAnyAuthority("HR_ADMIN","ADMIN")
                //.antMatchers(HttpMethod.PUT,"/api/v1/holidays").hasAnyAuthority("HR_ADMIN","ADMIN")
                //.antMatchers(HttpMethod.POST,"/api/v1/compensation").hasAnyAuthority("ADMIN")
                //.antMatchers(HttpMethod.PUT,"/api/v1/compensation").hasAnyAuthority("ADMIN")
                //.antMatchers(HttpMethod.GET,"/api/v1/compensation").hasAnyAuthority("ADMIN","HR_ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .logoutUrl("/api/v1/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                            throws IOException, ServletException {
                        if(authentication == null)
                        {
                            response.sendError(HttpStatus.UNAUTHORIZED.value());
                        }
                        else {
                            response.sendRedirect("/");
                            response.setStatus(HttpStatus.OK.value());
                        }

                    }
                })
                .deleteCookies("JSESSIONID", environment.getProperty("rememberme.cookie.name"))
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .rememberMe().tokenRepository(tokenRepository())//rememberMeServices(rememberMeServices()).tok
                .tokenValiditySeconds(1209600);

    }


    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getLocalizedMessage());
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                response.setStatus(HttpStatus.OK.value());
            }
        };
    }
    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // you USUALLY want this
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public RememberMeServices rememberMeServices(){
        PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(environment.getProperty("rememberme.key"), userAuthorizationService,
                tokenRepository());
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setCookieName(environment.getProperty("rememberme.cookie.name"));
        rememberMeServices.setTokenValiditySeconds(1209600);
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        super.authenticationManagerBean();
        List<AuthenticationProvider> providers =  new ArrayList<>();
        providers.add(rememberMeAuthenticationProvider());

        return new ProviderManager(providers);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthorizationService);
        auth.authenticationProvider(rememberMeAuthenticationProvider());
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public RememberMeAuthenticationFilter rememberMeFilter() throws Exception {
        RememberMeAuthenticationFilter rememberMeFilter = new RememberMeAuthenticationFilter(authenticationManagerBean(), rememberMeServices());

        return rememberMeFilter;
    }

    @Bean
    public AuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(environment.getProperty("rememberme.key"));
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("user");
    }


}