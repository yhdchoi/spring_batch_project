package com.yhdc.batch_scheduler.controller;

import com.yhdc.batch_scheduler.entity.Book;
import com.yhdc.batch_scheduler.repository.BookRepository;
import com.yhdc.batch_scheduler.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/book")
@RestController
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        log.info("Getting all books");
        return bookRepository.findAll();
    }


}
