package examples.boot.aopexam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalMyBeanConfig {
    @Bean
    MyBean myBean(){
        MyBean bean = new MyBean();
        bean.setName("kim");
        return bean;
    }
}
