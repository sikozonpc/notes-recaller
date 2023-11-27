package com.tiagotaquelim.kindlerecaller.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagotaquelim.kindlerecaller.gcp.GcpFileStorage;
import com.tiagotaquelim.kindlerecaller.highlight.Highlight;
import com.tiagotaquelim.kindlerecaller.highlight.HighlightRepository;
import com.tiagotaquelim.kindlerecaller.pojos.BookPojo;
import com.tiagotaquelim.kindlerecaller.pojos.HighlightPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class BookService {
    private final HighlightRepository highlightRepository;
    private final BookRepository bookRepository;
    private final GcpFileStorage fs;

    @Autowired
    public BookService(HighlightRepository highlightRepository, BookRepository bookRepository, GcpFileStorage fs) {
        this.highlightRepository = highlightRepository;
        this.bookRepository = bookRepository;
        this.fs = fs;
    }

    public Optional<Book> parseBookHighlightsAndCreateEntities(String filename) throws IOException {
        String bookFile = fs.readGcsFile(filename);

        ObjectMapper objectMapper = new ObjectMapper();
        BookPojo book = objectMapper.readValue(bookFile, BookPojo.class);

        Optional<Book> existingBook = bookRepository.findById(book.getAsin());
        if (existingBook.isEmpty()) {
            Book bookEntity = new Book(
                    book.getAsin(),
                    book.getTitle(),
                    book.getAuthors()
            );

            bookRepository.save(bookEntity);
        }

        for (HighlightPojo highlight : book.getHighlights()) {
            Optional<Highlight> existingHighlight = highlightRepository.findByText(highlight.getText());

            if (existingHighlight.isEmpty() && existingBook.isPresent()) {
                Highlight highlightEntity = new Highlight(
                        highlight.getText(),
                        highlight.getLocation().getUrl(),
                        highlight.getNote(),
                        existingBook.get()
                );

                highlightRepository.save(highlightEntity);
            }
        }

        return existingBook;
    }
}
