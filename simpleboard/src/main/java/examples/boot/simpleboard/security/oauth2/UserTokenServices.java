package examples.boot.simpleboard.security.oauth2;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;

public class UserTokenServices extends UserInfoTokenServices
{
    public UserTokenServices(String userInfoEndpointUrl, String clientId)
    {
        super(userInfoEndpointUrl, clientId);
    }

}