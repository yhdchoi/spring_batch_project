package com.yhdc.batch_scheduler.batch;

import com.yhdc.batch_scheduler.entity.Book;
import com.yhdc.batch_scheduler.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookWriter implements ItemWriter<Book> {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void write(Chunk<? extends Book> chunk) throws Exception {
        log.info("Writing: {}", chunk.toString());
        bookRepository.saveAll(chunk.getItems());
    }

}
