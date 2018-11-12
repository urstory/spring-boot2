package examples.boot.aopexam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("real")
public class RealMyBeanConfig {
    @Bean
    MyBean myBean(){
        MyBean bean = new MyBean();
        bean.setName("lee");
        return bean;
    }
}
