package examples.boot.webapi.api;

import examples.boot.webapi.dto.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// https://steemit.com/kr-dev/@igna84/spring-boot-responseentity
// 참고하세요.
@RestController
@RequestMapping("/api/books")
public class ApiController {
    // my.address 설정값을 주입하라.
    @Value("${my.address}")
    private String address;

//    @GetMapping(path ="/{id}")
//    public Book getBooks(@PathVariable(name = "id") Long id){
//        Book book = new Book(id, "hello");
//        return book;
//    }

    @GetMapping(path ="/{id}")
    public ResponseEntity<Book> getBooks(@PathVariable(name = "id") Long id){
        Book book = new Book(id, "hello");
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(){
        System.out.println("address: " + address);
        List<Book> list = new ArrayList<>();
        Book book1 = new Book(1L, "hello");
        Book book2 = new Book(2L, "hi");
        Book book3 = new Book(3L, "world");
        Book book4 = new Book(4L, "!!!");
        list.add(book1);
        list.add(book2);
        list.add(book3);
        list.add(book4);

        return new ResponseEntity<List<Book>>(list, HttpStatus.OK);
    }
}
