package br.com.alura.challenge.litealura.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "authors")
public class Authors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer anoNascimento;
    private Integer anoMorte;
    private String title;

    @OneToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Book> book;

    public Authors() {}

    public Authors(String nome, int anoNascimento, int anoMorte, String title) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoMorte = anoMorte;
        this.title = title;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        book.forEach(book1 -> book1.setAuthors(this));
        this.book = book;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoMorte() {
        return anoMorte;
    }

    public void setAnoMorte(Integer anoMorte) {
        this.anoMorte = anoMorte;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return """ 
                **********************
                Author: %s
                Ano De Nascimento: %d
                Ano Da Morte: %d
                Livro: [%s]
                ***********************
                """.formatted(nome, anoNascimento, anoMorte, title);
    }
}
