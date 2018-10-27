package examples.boot.myshop.config;

import examples.boot.myshop.security.LoginUserArgumentResolver;
import examples.boot.myshop.utils.MyArgumentResolver;
import examples.boot.myshop.utils.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// WebMvcConfigurer 를 구현하는 클래스는 Spring MVC 를 확장할 수 있는
// 기능을 가진다.
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MyArgumentResolver());
        resolvers.add(new LoginUserArgumentResolver());
    }
}


/*

JDK 8 부터는 default 메소드라는 기능이 추가됨.
인터페이스도 메소드를 구현할 수 있다.

interface가 있고, 해당 interface를 구현해야 한다.
이 인터페이스의 모든 기능은 구현할 필요가 없고, 필요한 기능만 구현하고 싶다면 어떻게 할까?

예를 들면 사용자가 MyInterface 라는 interface를 만들었다. 해당 인터페이스가 메소드를
5개 가지고 있다.

이런 불편함을 해결하려고 MyInterface를 구현한 MyAdapter라는 클래스를 제공한다.
구현하고 있지만 내용은 없다.

class MyAdapter implements MyInterface{
 // 5개의 메소드를 구현...
}

class MyBean extends MyAdapter{
    // 필요한것만 오버라이딩한다.
}
 */