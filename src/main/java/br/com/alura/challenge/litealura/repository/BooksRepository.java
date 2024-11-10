package br.com.alura.challenge.litealura.repository;

import br.com.alura.challenge.litealura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {

}
