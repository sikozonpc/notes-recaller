package com.tiagotaquelim.kindlerecaller.pojos;

import java.util.List;

public class BookPojo {
    private String asin;
    private String title;
    private String authors;
    private List<HighlightPojo> highlights;

    public BookPojo(String asin, String title, String authors, List<HighlightPojo> highlights) {
        this.asin = asin;
        this.title = title;
        this.authors = authors;
        this.highlights = highlights;
    }

    public BookPojo() {
    }

    public String getAsin() {
        return asin;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public List<HighlightPojo> getHighlights() {
        return highlights;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setHighlights(List<HighlightPojo> highlights) {
        this.highlights = highlights;
    }
}

