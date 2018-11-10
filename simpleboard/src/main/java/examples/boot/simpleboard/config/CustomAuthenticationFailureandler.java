package examples.boot.simpleboard.config;

import examples.boot.simpleboard.security.oauth2.NotSocialLoginContext;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String loginId = NotSocialLoginContext.getEmail();
        if(loginId != null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/users/already-social-join");
        }else{
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/users/login");
        }
    }
}
