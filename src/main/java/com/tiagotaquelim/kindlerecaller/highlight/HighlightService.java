package com.tiagotaquelim.kindlerecaller.highlight;

import com.tiagotaquelim.kindlerecaller.book.BookRepository;
import com.tiagotaquelim.kindlerecaller.gcp.GcpFileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HighlightService {
    private final HighlightRepository highlightRepository;
    private final BookRepository bookRepository;

    @Autowired
    public HighlightService(HighlightRepository highlightRepository, BookRepository bookRepository) {
        this.highlightRepository = highlightRepository;
        this.bookRepository = bookRepository;
    }

    public void createHighlight(Highlight highlight) {
        highlightRepository.save(highlight);
    }

    public void deleteHighlight(Long id) {
        highlightRepository.deleteById(id);
    }

    public List<Highlight> getHighlightsByBook(String isbn) {
        bookRepository
                .findById(isbn)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        return highlightRepository.findAllByBookIsbn(isbn);
    }
}