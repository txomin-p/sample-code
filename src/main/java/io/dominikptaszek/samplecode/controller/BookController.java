package io.dominikptaszek.samplecode.controller;

import io.dominikptaszek.samplecode.model.Book;
import io.dominikptaszek.samplecode.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    record newBookRequest(Integer isbn,
                          String title,
                          String author,
                          String genre
                          //,String coverImageUrl
                          ){
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("{bookID}")
    public Book getBookById(@PathVariable("bookID") Integer bookId){
        return bookRepository.findById(bookId).get();
    }

    @PostMapping
    public void addBook(@RequestBody newBookRequest request){
        Book book = new Book();
        book.setIsbn(request.isbn);
        book.setTitle(request.title);
        book.setAuthor(request.author);
        book.setGenre(request.genre);
        //book.setCoverImageUrl(request.coverImageUrl);
        book.setObtainDate(new Timestamp(System.currentTimeMillis()));
        book.setCoverImageUrl(String.format("https://covers.openlibrary.org/b/isbn/%s-L.jpg",request.isbn));
        /*
        /// https://covers.openlibrary.org/b/isbn/$value-L.jpg
        /// format supported by openlibrary Covers Api
         */
        bookRepository.save(book);
    }

    @DeleteMapping("{bookID}")
    public void deleteBook(@PathVariable("bookID") Integer bookId){
        bookRepository.deleteById(bookId);
    }

    @PutMapping("{bookID}")
    public void updateBook(@RequestBody newBookRequest request,@PathVariable("bookID") Integer bookId){
        Book book = bookRepository.findById(bookId).get();
        book.setIsbn(request.isbn);
        book.setTitle(request.title);
        book.setAuthor(request.author);
        book.setGenre(request.genre);
        book.setObtainDate(new Timestamp(System.currentTimeMillis()));
        book.setCoverImageUrl(String.format("https://covers.openlibrary.org/b/isbn/%s-L.jpg",request.isbn));
        bookRepository.save(book);

    }

    @PatchMapping("{bookID}/{coverImageUrl}")
    public void editBook(@PathVariable("bookID")Integer bookId ,@PathVariable("coverImageUrl") String newUrl){
        Book book = bookRepository.findById(bookId).get();
        book.setCoverImageUrl(newUrl);
        book.setObtainDate(new Timestamp(System.currentTimeMillis()));
        bookRepository.save(book);
    }


}
