package examples.boot.simpleboard.config;

import examples.boot.simpleboard.security.SimpleBoardTokenRepositoryImpl;
import examples.boot.simpleboard.security.oauth2.AlreadyLoginCheckFilter;
import examples.boot.simpleboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

@Configuration
public class WebApplicationSecurity  extends WebSecurityConfigurerAdapter {
    @Autowired
    SimpleBoardTokenRepositoryImpl simpleBoardTokenRepositoryImpl;

    @Autowired
    ApplicationContext context;

    @Autowired
    UserService userService;

    /*
    permitAll()의 경우는 인증 검사가 수행되긴 한다. static resource의 경우는 아예 인증을 무시하게 하는 것이 불필요한 sql이 실행안되게 할 수 있다.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(new AntPathRequestMatcher("/**.html"))
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/boards").and()
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/chatrooms/**").hasRole("USER")
                .antMatchers("/boards/writeform").hasRole("USER")
                .antMatchers("/boards/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/users").permitAll()
                .antMatchers("/api/boards").hasRole("USER")
                .antMatchers("/api/boards/**").permitAll()
                .antMatchers("/image-files/**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .csrf().ignoringAntMatchers("/**")
//                .ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().disable()
                .and()
                    .formLogin()
                        .loginProcessingUrl("/users/login")
                        .loginPage("/users/login").usernameParameter("id").passwordParameter("password")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureHandler(customAuthenticationFailureandler())
                .and().rememberMe().tokenRepository(simpleBoardTokenRepositoryImpl).rememberMeParameter("remember-me").tokenValiditySeconds(1209600)
                .and().logout().permitAll()
                .and()
                    .addFilterBefore(new AlreadyLoginCheckFilter(), BasicAuthenticationFilter.class)
                    .addFilterBefore((Filter)context.getBean("sso.filter"), BasicAuthenticationFilter.class);
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler = new CustomAuthenticationSuccessHandler();
        customAuthenticationSuccessHandler.setDefaultUrl("/boards");
        customAuthenticationSuccessHandler.setTargetUrlParameter("loginRedirect");
        customAuthenticationSuccessHandler.setUseReferer(true);
        return customAuthenticationSuccessHandler;
    }

    @Bean
    public CustomAuthenticationFailureandler customAuthenticationFailureandler(){
        CustomAuthenticationFailureandler customAuthenticationFailureandler = new CustomAuthenticationFailureandler();
        return customAuthenticationFailureandler;
    }

}
