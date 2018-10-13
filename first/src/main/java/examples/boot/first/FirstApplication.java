package examples.boot.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FirstApplication {
	// 컴포넌트 스캔으로 찾을 수 없는 객체를 생성하는 코드를 작성한다.
	// 컨테이너에 위해 해당 메소드는 자동실행된다.
	// Bean이란? 스프링 컨테이너가 관리하는 객체를 말한다.
	// Bean은 모두 유일한 이름의 id를 가진다.
	// 메소드이름이 id가 된다.
	@Bean
	public TodayBean todayBean(){
		// 자기 자신의 클래스 이름을 출력한다.
		System.out.println(this.getClass().getName());
		return new TodayBean();
	}

//	@Bean
//	public TodayService todayService1(){
//		return new TodayService(todayBean());
//	}
//
//	@Bean
//	public TodayService todayService2(){
//		return new TodayService(todayBean());
//	}

	// 프로그램 시작점.
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}
}
