package examples.boot.simpleboard.config;

import examples.boot.simpleboard.security.oauth2.AlreadyLoginCheckFilter;
import examples.boot.simpleboard.security.oauth2.OAuth2SuccessHandler;
import examples.boot.simpleboard.security.oauth2.UserTokenServices;
import examples.boot.simpleboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.*;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig
{
    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    UserService userService;

    @Bean
    @ConfigurationProperties("facebook.client")
    AuthorizationCodeResourceDetails facebook()
    {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    ResourceServerProperties facebookResource()
    {
        return new ResourceServerProperties();
    }

//    @Bean
//    @ConfigurationProperties("naver.client")
//    AuthorizationCodeResourceDetails naver()
//    {
//        return new AuthorizationCodeResourceDetails();
//    }
//
//    @Bean
//    @ConfigurationProperties("naver.resource")
//    ResourceServerProperties naverResource()
//    {
//        return new ResourceServerProperties();
//    }

    @Bean
    FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter)
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        // 스프링 사이트에 의하면 다른 필터보다 우선순위를 올리기위해 -100을 주었다고 나옵니다.
        registration.setOrder(-100);
        return registration;
    }

    @Bean("sso.filter")
    Filter ssoFilter(OAuth2SuccessHandler oAuth2SuccessHandler)
    {
        List<Filter> filters = new ArrayList<>();

        // 페이스북
        OAuth2ClientAuthenticationProcessingFilter facebook
                = new OAuth2ClientAuthenticationProcessingFilter("/sign-in/facebook");
        facebook.setRestTemplate(new OAuth2RestTemplate(facebook(), oauth2ClientContext));
        facebook.setTokenServices(new UserTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
        facebook.setAuthenticationSuccessHandler(oAuth2SuccessHandler);
        filters.add(facebook);

//        // 네이버
//        OAuth2ClientAuthenticationProcessingFilter naver
//                = new OAuth2ClientAuthenticationProcessingFilter("/sign-in/naver");
//        naver.setRestTemplate(new OAuth2RestTemplate(naver(), oauth2ClientContext));
//
//
//        System.out.println("-----------------------------------");
//        System.out.println(naverResource().getUserInfoUri());
//        System.out.println("-----------------------------------");
//        NaverUserTokenServices userTokenServices = new NaverUserTokenServices(naverResource().getUserInfoUri(), naver().getClientId());
//        naver.setTokenServices(userTokenServices);
//        naver.setAuthenticationSuccessHandler(new OAuth2SuccessHandler("naver", userService));
//        filters.add(naver);

        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(filters);
        return filter;
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler(){
        OAuth2SuccessHandler oAuth2SuccessHandler = new OAuth2SuccessHandler("facebook", userService);
        return oAuth2SuccessHandler;
    }
}