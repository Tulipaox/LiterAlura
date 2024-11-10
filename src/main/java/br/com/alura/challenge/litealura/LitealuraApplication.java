package br.com.alura.challenge.litealura;

import br.com.alura.challenge.litealura.model.Authors;
import br.com.alura.challenge.litealura.principal.principal;
import br.com.alura.challenge.litealura.repository.AuthorsRepository;
import br.com.alura.challenge.litealura.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class LitealuraApplication implements CommandLineRunner{
	@Autowired
	private BooksRepository repository;

	@Autowired
	private AuthorsRepository authorsRepository;

	public static void main(String[] args) {
		SpringApplication.run(LitealuraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal principal = new principal(repository, authorsRepository);
		principal.exibeMenu();
	}
}
