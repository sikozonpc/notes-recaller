package com.tiagotaquelim.kindlerecaller.highlight;

import com.tiagotaquelim.kindlerecaller.book.Book;
import com.tiagotaquelim.kindlerecaller.subscriber.Subscriber;
import jakarta.persistence.*;

@Table
@Entity(name = "highlight")
public class Highlight {
    @Column(updatable = false)
    @Id()
    @SequenceGenerator(
            name = "highlight_sequence",
            sequenceName = "highlight_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "highlight_sequence")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private String location;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "bookIsbn", nullable = true)
    private Book book;

    @ManyToOne
    @JoinColumn(
            name = "subscriberId",
            nullable = true
    )
    private Subscriber subscriber;

    public Highlight() {}

    public Highlight(Long id, String text, String location, String note) {
        this.id = id;
        this.text = text;
        this.location = location;
        this.note = note;
    }

    public Highlight(String text, String location, String note, Book book) {
        this.text = text;
        this.location = location;
        this.note = note;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Highlight{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", location='" + location + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
