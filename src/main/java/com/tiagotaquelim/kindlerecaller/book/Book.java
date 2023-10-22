package com.tiagotaquelim.kindlerecaller.book;

import com.tiagotaquelim.kindlerecaller.highlight.Highlight;
import jakarta.persistence.*;

import java.util.List;

@Table
@Entity(name="book")
public class Book {
    @Id
    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @OneToMany(mappedBy = "book")
    private List<Highlight> highlights;

    public Book() {}

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<Highlight> highlights) {
        this.highlights = highlights;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
