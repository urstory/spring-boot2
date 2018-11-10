package examples.boot.simpleboard.security.oauth2;

import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.domain.UserConnection;
import examples.boot.simpleboard.domain.UserRole;
import examples.boot.simpleboard.security.LoginUserInfo;
import examples.boot.simpleboard.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler
{
    final String type;
    final UserService userService;

    public OAuth2SuccessHandler(String type, UserService userService)
    {
        this.type = type;
        this.userService = userService;
    }

    // type : naver
    /*
    org.springframework.security.oauth2.provider.OAuth2Authentication@e39f77d3: Principal: unknown; Credentials: [PROTECTED]; Authenticated: true; Details: remoteAddress=0:0:0:0:0:0:0:1, sessionId=<SESSION>, tokenType=bearertokenValue=<TOKEN>; Granted Authorities: OAUTH
     */
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
            throws IOException, ServletException
    {
        String referer = req.getHeader("referer");
        // 연결되어 있는 계정이 있는지 확인합니다.
        // 이전 강의 AccountService.getAccountByOAuthId 참고!!
        String authName = auth.getName(); // 숫자로 된 social id 이다.
        User user = userService.getSocialUser(type, authName);

        // 연결되어 있는 계정이 있는경우.
        if (user != null)
        {
            // 기존 인증을 바꿉니다.
            // 이전 강의의 일반 로그인과 동일한 방식으로 로그인한 것으로 간주하여 처리합니다.
            // 기존 인증이 날아가기 때문에 OAUTH ROLE은 증발하며, USER ROLE 이 적용됩니다.
            List<String> invalidUrls = Arrays.asList("/users/joinform");
            final String finalReferer = referer;
            boolean find =
                invalidUrls.stream().anyMatch(url -> {
                    if(finalReferer.contains(url)){
                        return true;
                    }
                    return false;
                });
            if(find)
                referer = req.getContextPath() + "/users/already-social-join-and-login";


            List<GrantedAuthority> list = new ArrayList<>();
            user.getRoles().forEach(role -> list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));

            LoginUserInfo userDetails = new LoginUserInfo(user.getEmail(), user.getPassword(), list);
            userDetails.setId(user.getId());
            userDetails.setName(user.getName());

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            userDetails, null, list)
            );
            res.sendRedirect(referer);
            return;
        }
        // 연결된 계정이 없는경우
        else
        {
            User joinUser = new User();

            joinUser.setPassword("");

            SocialLoginInfo socialLoginInfo = getUserConnection(auth);

            User userByEmail = userService.getUserByEmail(socialLoginInfo.getEmail());

            String alreadyLoginId = (String)req.getAttribute("alreadyLoginId");
            if(alreadyLoginId != null){ // 이미 login된 아이디가 있다. 그런데 social로그인을 했다. 이미 있는 회원인데 소셜 로그인을 했다는 것은 소셜 로그인 연결을 했다는 의미다.
                joinUser = userService.getUserByEmail(socialLoginInfo.getEmail());
                joinUser.addUserConnection(socialLoginInfo.getUserConnection());
                userService.addUser(joinUser);

                // 이미 가입되어 있는데 소셜로그인을 하는 경우는 사용자 정보보기 밖에 없다.
                referer = req.getContextPath() + "/users/info";

                List<GrantedAuthority> list = new ArrayList<>();
                joinUser.getRoles().forEach(role -> list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));

                LoginUserInfo userDetails = new LoginUserInfo(joinUser.getEmail(), joinUser.getPassword(), list);
                userDetails.setId(joinUser.getId());
                userDetails.setName(joinUser.getName());
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken
                        (
                                userDetails, null, list)
                );
                res.sendRedirect(referer);
                return;
            }else if(userByEmail != null && user == null){
                // 회원 정보에 email은 있지만 social정보로 찾은 user정보는 없을 경우
                // facebook으로 로그인 처리는 되었지만, 회원 가입 처리는 할 수 없는 상황이다.
                // 로그인 정보를 제거한다.
                SecurityContextHolder.getContext().setAuthentication(null);
                res.sendRedirect(req.getContextPath() + "/users/already-join");
                return;
            }else {
                // 처음 소셜 로그인을 했다. facebook이나 다른 소셜 계정으로 회원가입이 된다.
                joinUser.setEmail(socialLoginInfo.getEmail());
                joinUser.setName(socialLoginInfo.getUserName());

                UserRole userRole = new UserRole();
                userRole.setRoleName("USER");
                joinUser.addUserRole(userRole);

                UserConnection userConnection = socialLoginInfo.getUserConnection();
                userConnection.setUser(joinUser);

                userService.addUser(joinUser);


                List<GrantedAuthority> list = new ArrayList<>();
                joinUser.getRoles().forEach(role -> list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));

                LoginUserInfo userDetails = new LoginUserInfo(joinUser.getEmail(), joinUser.getPassword(), list);
                userDetails.setId(joinUser.getId());
                userDetails.setName(joinUser.getName());

                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken
                        (
                                userDetails, null, list)
                );
                res.sendRedirect(req.getContextPath() + "/users/welcome");
                return;
            } // if end
        }
    }

    private SocialLoginInfo getUserConnection(Authentication auth){
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)auth;
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails)oAuth2Authentication.getDetails();

        String userName = null;
        String providerUserId = null;
        String nickName = null;
        String birthday = null;
        String gender = null;
        String age = null;
        String profileImage = null;
        String email = null;

        if("facebook".equals(type)) {
            Map<String, String> map = (Map) oAuth2Authentication.getUserAuthentication().getDetails();
            providerUserId = map.get("id");
            userName = map.get("name");
            email = map.get("email");
        }else if("naver".equals(type)){
            Map<String, Object> map = (Map) oAuth2Authentication.getUserAuthentication().getDetails();
            Map<String, String> responseMap = (Map)map.get("response");
            providerUserId = responseMap.get("id");
            nickName = responseMap.get("nickname");
            birthday = responseMap.get("birthday");
            gender = responseMap.get("gender");
            age = responseMap.get("age");
            profileImage = responseMap.get("profile_image");
            userName = responseMap.get("name");
            email = responseMap.get("email");
        }

        UserConnection userConnection = new UserConnection();
        userConnection.setAccessToken(oAuth2AuthenticationDetails.getTokenValue());
        userConnection.setProviderId(type);
        userConnection.setProviderUserId(providerUserId);
        userConnection.setProfileUrl(profileImage);
        userConnection.setDisplayName(nickName);

        SocialLoginInfo socialLoginInfo = new SocialLoginInfo();
        socialLoginInfo.setUserConnection(userConnection);
        socialLoginInfo.setEmail(email);
        socialLoginInfo.setUserName(userName);

        return socialLoginInfo;
    }

}

@Getter
@Setter
class SocialLoginInfo{
    private UserConnection userConnection;
    private String email;
    private String userName;
}
