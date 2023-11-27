package com.tiagotaquelim.kindlerecaller.subscriber;

import com.tiagotaquelim.kindlerecaller.highlight.Highlight;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subscriber")
public class Subscriber {
    @Column(updatable = false)
    @Id
    @SequenceGenerator(
            name = "highlight_sequence",
            sequenceName = "highlight_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "highlight_sequence" )
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "subscriber", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private List<Highlight> highlights;

    public Subscriber(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Subscriber(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Subscriber() {}

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String setName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }
}
