package com.tiagotaquelim.kindlerecaller.highlight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HighlightRepository extends JpaRepository<Highlight, Long> {
    @Query(value="SELECT * FROM highlight WHERE highlight.book_isbn = ?1", nativeQuery = true)
    List<Highlight> findAllByBookIsbn(String isbn);

    @Query(value="SELECT * FROM highlight WHERE highlight.text = ?1", nativeQuery = true)
    Optional<Highlight> findByText(String text);

    @Query(value="SELECT * FROM highlight ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    List<Highlight> findRandomHighlights(Number n);
}
