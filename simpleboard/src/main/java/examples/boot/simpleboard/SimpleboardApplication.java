package examples.boot.simpleboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import examples.boot.simpleboard.argumentresolver.LoginUserInfoArgumentResolver;
import examples.boot.simpleboard.interceptor.RefererSaveInterceptor;
import examples.boot.simpleboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SimpleboardApplication  implements WebMvcConfigurer {


	public static void main(String[] args) {
		SpringApplication.run(SimpleboardApplication.class, args);
	}

	// https://stackoverflow.com/questions/28862483/spring-and-jackson-how-to-disable-fail-on-empty-beans-through-responsebody
	// https://github.com/FasterXML/jackson-datatype-hibernate/issues/87
	// https://stackoverflow.com/questions/33727017/configure-jackson-to-omit-lazy-loading-attributes-in-spring-boot
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).modules(new JavaTimeModule()).build();
		Hibernate5Module hibernateModule = new Hibernate5Module();
		// 레이지 로딩을 강제화 한다.
//		hibernateModule.enable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
		mapper.registerModule(hibernateModule);
		List<MediaType> supportedMediaTypes=new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(mapper);
		converter.setPrettyPrint(true);
		converter.setSupportedMediaTypes(supportedMediaTypes);
		converters.add(converter);
	}

	@Bean
	public RefererSaveInterceptor loginCheckInterceptor(){
		RefererSaveInterceptor refererSaveInterceptor = new RefererSaveInterceptor();
		return refererSaveInterceptor;
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		if(!registry.hasMappingForPattern("/webjars/**")){
//			registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:META-INF/resources/webjars/");
////			registry.addResourceHandler("/**").addResourceLocations("classpath:META-INF/");
//		}
//		if(!registry.hasMappingForPattern("/static/**")){
//			registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//		}
//	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/boards");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginUserInfoArgumentResolver());
	}
}
