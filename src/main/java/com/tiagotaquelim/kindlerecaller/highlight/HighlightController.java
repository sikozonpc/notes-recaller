package com.tiagotaquelim.kindlerecaller.highlight;

import com.tiagotaquelim.kindlerecaller.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/v1/highlights" )
public class HighlightController {
    private final HighlightService highlightService;

    @Autowired
    public HighlightController(HighlightService highlightService) {
        this.highlightService = highlightService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/book/{isbn}")
    public List<Highlight> getAll(
            @PathVariable("isbn") String isbn
    ) {
        return highlightService.getBookHighlightsByBookIsbn(isbn);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/book/parse")
    public Optional<Book> parseBookAndCreateHighlights(@RequestParam("filename") String filename) throws IOException {
        return highlightService.parseBookHighlightsAndCreateEntities(filename);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/mail-subscribers")
    public void mailSubscribers() throws IOException {
        highlightService.sendRandomHighlightsEmailToSubscribers();
    }
}