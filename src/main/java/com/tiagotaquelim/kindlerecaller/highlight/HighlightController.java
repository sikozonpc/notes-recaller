package com.tiagotaquelim.kindlerecaller.highlight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/highlights")
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
        return highlightService.getHighlightsByBook(isbn);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createHighlight(@RequestBody Highlight highlight) {
        highlightService.createHighlight(highlight);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteHighlight(@RequestBody Long id) {
        highlightService.deleteHighlight(id);
    }
}