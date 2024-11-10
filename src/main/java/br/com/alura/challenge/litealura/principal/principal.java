package br.com.alura.challenge.litealura.principal;

import br.com.alura.challenge.litealura.model.*;
import br.com.alura.challenge.litealura.repository.AuthorsRepository;
import br.com.alura.challenge.litealura.repository.BooksRepository;
import br.com.alura.challenge.litealura.service.ConsumoApi;
import br.com.alura.challenge.litealura.service.ConverteDados;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import java.util.*;

public class principal {
    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private List<Book> books = new ArrayList<>();
    private List<Authors> author;
    private BooksRepository repository;
    private AuthorsRepository authorsRepository;

    public principal(BooksRepository repository, AuthorsRepository authorsRepository) {
        this.repository = repository;
        this.authorsRepository = authorsRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    Escolha o número de sua opção:
                    1 - buscar livro pelo titulo
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma
                    6 - sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listregisteredbooks();
                    break;
                case 3:
                    listregisteredAuthor();
                    break;
                case 4:
                    listofactorsalive();
                    break;
                case 5:
                    listbookinlanguage();
                    break;
                case 6:
                    System.out.println("Encerrando.......");
                    break;
            }
        }
    }

    private void listbookinlanguage() {
        System.out.println("Encontar livro em um determinado idioma!");
        var idioma = leitura.nextLine();
        List<Book> books = repository.findAll();
        List<Book> livrosEncontrados = books.stream()
                .filter(book -> Objects.equals(book.getLanguages(), idioma))
                .toList();
        if (livrosEncontrados.isEmpty()){
            System.out.println("Nenhum livro encontrado no idioma especificado.");
        } else { livrosEncontrados.forEach(System.out::println); }
    }

    private void listofactorsalive() {
        System.out.println("Encontar autor vivo em determinado ano!");
        var ano = leitura.nextInt();
        author = authorsRepository.findAll();
        author.stream()
                .filter(d-> d.getAnoNascimento() <= ano && d.getAnoMorte() >= ano)
                .forEach(System.out::println);
    }


    private void listregisteredAuthor() {
        author = authorsRepository.findAll();
        author.forEach(System.out::println);
    }

    private void listregisteredbooks() {
        books = repository.findAll();
        books.stream()
                .sorted(Comparator.comparing(Book::getTitulo))
                .sorted(Comparator.comparing(Book::getName)).forEach(System.out::println);
    }

    private void buscarLivro() {

        var dadosbook = getDadosBooks().result().stream()
                .flatMap(book -> book.autor().stream()
                        .map(autho ->
                                new Book(book.titulo(), Collections.singletonList((String) book.languages().getFirst()),book.downloads(),
                                 new Authors(autho.nome(),
                                 Optional.ofNullable( autho.anoNacismento()).orElse(0),
                                 Optional.ofNullable(autho.anoMorte()).orElse(0),
                                 book.titulo()))))
                .toList();
        
        Book bookEncontrado = dadosbook.getFirst();
        Book book = new Book(bookEncontrado);

        Authors authors = new Authors(dadosbook.getFirst().getName(),
                dadosbook.getFirst().getAuthors().getAnoNascimento(),
                dadosbook.getFirst().getAuthors().getAnoMorte(),
                dadosbook.getFirst().getTitulo()
        );

        try {
            book.setAuthors(authors);
            authors.setBook(books);
            repository.save(book);
            System.out.println(book);
        } catch (InvalidDataAccessApiUsageException e){
            System.out.println("erro ao criar" + e);
        }

    }
    private DadosResult getDadosBooks() {
        System.out.println("Digite o nome do livro para busca");
        var nomeBook = leitura.nextLine().replace(" ", "+");;
        var json = consumo.obterDados(ENDERECO + nomeBook);
        return conversor.obterDados(json, DadosResult.class);
    }
}
