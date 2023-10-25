package com.tiagotaquelim.kindlerecaller.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/parse-kindle-file")
    public Optional<Book> parseBookAndCreateHighlights(@RequestParam("filename") String filename) throws IOException {
        return bookService.parseBookHighlightsAndCreateEntities(filename);
    }
}
