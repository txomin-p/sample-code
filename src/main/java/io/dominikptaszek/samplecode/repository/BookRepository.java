package io.dominikptaszek.samplecode.repository;

import io.dominikptaszek.samplecode.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {
}
