package examples.boot.webclient.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import examples.boot.webclient.dto.Book;

import java.util.List;

@Component
public class BookAdapter {
    @Autowired
    RestTemplate restTemplate;

    public Book getBook(Long id){

        Book book = restTemplate.getForObject("http://localhost:8080/api/books/" + id,
                                                Book.class);
        return book;
    }

    public List<Book> getBooks(){
        List<Book> list = restTemplate.exchange("http://localhost:8080/api/books",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {}).getBody();
        return list;
    }
}
