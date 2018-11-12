package examples.boot.aopexam;

import examples.boot.aopexam.config.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopexamApplication implements CommandLineRunner {
    @Autowired
    MyBean myBean;

    public static void main(String[] args) {
        SpringApplication.run(AopexamApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(myBean.getName());
    }
}
