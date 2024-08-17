package com.yhdc.batch_scheduler.batch;

import com.yhdc.batch_scheduler.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class RestBookReader implements ItemReader<Book> {

    private final String url;
    private final RestTemplate restTemplate;
    private int nextBook;
    private List<Book> bookList;

    @Override
    public Book read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (bookList == null) {
            bookList = fetchBooks();
        }

        Book book = null;

        if (nextBook < bookList.size()) {
            book = bookList.get(nextBook);
            nextBook++;
        } else {
            nextBook = 0;
            bookList = null;
        }
        return book;
    }


    private List<Book> fetchBooks() {
        ResponseEntity<Book[]> response = restTemplate.getForEntity(url, Book[].class);
        Book[] books = response.getBody();
        if (books != null) {
            return Arrays.asList(books);
        } else {
            return null;
        }
    }

}
