package io.dominikptaszek.samplecode.model;


import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

import lombok.*;

@Builder
@Entity
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_id_sequence",
            sequenceName = "book_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_id_sequence"
    )
    private Integer id;
    private Integer isbn;
    private String title;
    private String author;
    private String genre;
    private String coverImageUrl;
    private Timestamp obtainDate;

    public Book() { }

    public Book(Integer id,
                Integer isbn,
                String title,
                String author,
                String genre,
                String coverImageUrl) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.coverImageUrl = coverImageUrl;
    }

    public Book(Integer id,
                Integer isbn,
                String title,
                String author,
                String genre,
                String coverImageUrl,
                Timestamp timestamp) {
        this(id,isbn,title,author,genre,coverImageUrl);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Timestamp getObtainDate() {
        return obtainDate;
    }

    public void setObtainDate(Timestamp obtainDate) {
        this.obtainDate = obtainDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre) && Objects.equals(coverImageUrl, book.coverImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, author, genre, coverImageUrl);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn=" + isbn +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", coverImageUrl='" + coverImageUrl + '\'' +
                '}';
    }
}
