package examples.boot.myshop.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebApplicationSecurity
        extends WebSecurityConfigurerAdapter {
    /*
    인증이 아예 필요없는(무시하는) 경로에 대한 설정
    ex> image, css, html 파일 (정적)
    "/css/**",
    "/js/**",
    "/images/**"
    /webjars/**"
    "/** /favicon.ico"
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources()
                        .atCommonLocations())
                .requestMatchers(
                        new AntPathRequestMatcher("/**.html"));
    }

    /*
    로그인 템플릿에 대한 설정.
    로그인 처리 URL에 대한 설정
    PATH별 접근 설정
    로그아웃PAth에 대한 설정.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout() // logout설정을 한다.
                    .logoutRequestMatcher(
                        new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/boards").and()
                .authorizeRequests() // path별 접근설정, 위에서 부터 체크 순서가 중요.
                    .antMatchers("/boards").permitAll() // 권한이 있든 없든, 로그인을 안했든..
                    .antMatchers("/members/joinform").permitAll()
                    .antMatchers(HttpMethod.POST,
                            "/members/join").permitAll()
                    .antMatchers("/members/welcome").permitAll()
                    .antMatchers("/members/login").permitAll()
                    .antMatchers("/members/**").hasRole("USER")
                    .antMatchers(HttpMethod.GET,"/boards").permitAll()
                    .antMatchers(HttpMethod.POST,"/boards").hasRole("USER")
                    .antMatchers("/boards/**").hasRole("USER")
                    .antMatchers("/api/**").hasRole("USER")
                    .antMatchers("/h2-console/**").permitAll()
                    .anyRequest().fullyAuthenticated().and()
                .headers()
                    .frameOptions().disable().and() // h2-console을 사용하려면 설정
                .csrf() // 보안설정. 실제 배포할때는 csrf()설정을 해야한다.
                    .ignoringAntMatchers("/**")// h2-console을 사용하려면 post방식으로 값을 전달할 때 csrf를 무시
                .and()
                    .formLogin() // 로그인 설정
                        .loginPage("/members/login") // Controller 에서 해당 path를 처리한다.
                            .usernameParameter("id") // <input name="id"
                            .passwordParameter("password") // <input name="password"
                    .loginProcessingUrl("/members/login"); // id, password를 PoST방식으로 전달받은 경로, 필터가 받는다.

    }
}
