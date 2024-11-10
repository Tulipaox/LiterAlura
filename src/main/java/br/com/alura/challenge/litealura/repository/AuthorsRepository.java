package br.com.alura.challenge.litealura.repository;

import br.com.alura.challenge.litealura.model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsRepository extends JpaRepository<Authors, Long> {
}
