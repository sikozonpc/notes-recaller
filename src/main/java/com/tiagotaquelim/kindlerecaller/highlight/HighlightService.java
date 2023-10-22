package com.tiagotaquelim.kindlerecaller.highlight;

import com.tiagotaquelim.kindlerecaller.book.Book;
import com.tiagotaquelim.kindlerecaller.book.BookRepository;
import com.tiagotaquelim.kindlerecaller.mail.SendGridService;
import com.tiagotaquelim.kindlerecaller.pojos.BookPojo;
import com.tiagotaquelim.kindlerecaller.gcp.GcpFileStorage;
import com.tiagotaquelim.kindlerecaller.pojos.EmailHighlightField;
import com.tiagotaquelim.kindlerecaller.pojos.HighlightPojo;
import com.tiagotaquelim.kindlerecaller.subscriber.Subscriber;
import com.tiagotaquelim.kindlerecaller.subscriber.SubscriberService;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

@Service
public class HighlightService {
    private final HighlightRepository highlightRepository;
    private final BookRepository bookRepository;
    private final GcpFileStorage fs;
    private final SendGridService sendGridService;
    private final SubscriberService subscriberService;

    public HighlightService(HighlightRepository highlightRepository, BookRepository bookRepository, GcpFileStorage fs, SendGridService sendGridService, SubscriberService subscriberService) {
        this.highlightRepository = highlightRepository;
        this.bookRepository = bookRepository;
        this.fs = fs;
        this.sendGridService = sendGridService;
        this.subscriberService = subscriberService;
    }

    public List<Highlight> getBookHighlightsByBookIsbn(String isbn) {
        bookRepository
                .findById(isbn)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        return highlightRepository.findAllByBookIsbn(isbn);
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

    public void sendRandomHighlightsEmailToSubscribers() throws IOException {
        List<Highlight> highlights = highlightRepository.findRandomHighlights(3);

        List<EmailHighlightField> emailFields = highlights.stream()
                .map(highlight -> new EmailHighlightField(
                        highlight.getText(),
                        highlight.getBook().getAuthor(),
                        highlight.getNote()
                ))
                .toList();

        List<Subscriber> subscribers = subscriberService.getAllSubscribers();

        sendGridService.sendTextEmail(subscribers, emailFields);
    }
}