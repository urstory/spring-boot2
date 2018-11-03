package examples.boot.webclient;

import examples.boot.webclient.client.BookAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import examples.boot.webclient.dto.Book;

import java.util.List;

// CommandLineRunner 인터페이스를 구현하면 CLI 애플리케이션을 만든다.
// run(String... args) 을 구현한다.
@SpringBootApplication
public class WebclientApplication implements CommandLineRunner {

    @Autowired
    BookAdapter bookAdapter;

    public static void main(String[] args) {
        SpringApplication.run(WebclientApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    // 실질적인 프로그램 시작점.
    // spring boot 애플리케이션에서는 main이라고 생각하면 편하다.
    // 주입된 객체를 이용할 수 있다.
    @Override
    public void run(String... args) throws Exception {
        Book book = bookAdapter.getBook(1L);
        printBook(book);

        System.out.println("-----------------------");
        List<Book> list = bookAdapter.getBooks();
        for(Book b : list){
            printBook(b);
        }
    }



    private void printBook(Book book) {
        System.out.println(book.getId());
        System.out.println(book.getTitle());
        System.out.println(book.getRegdate());
    }
}
