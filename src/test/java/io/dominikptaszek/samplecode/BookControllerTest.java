package io.dominikptaszek.samplecode;

import io.dominikptaszek.samplecode.model.Book;
import io.dominikptaszek.samplecode.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@WebMvcTest
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookRepository bookRepository;

    private final int BOOK_ID = 1;

    private Book book = 
        Book.builder()
            .id(BOOK_ID)
            .isbn(111)
            .title("Parasite")
            .author("Marek Krajewski")
            .genre("crime novel")
            .coverImageUrl("https://book-covers.org/marek.krajewski/111")
            .build();

    private Book book2 =
            Book.builder()
                    .id(BOOK_ID)
                    .isbn(222)
                    .title("Parasite")
                    .author("Marek Krajewski")
                    .genre("crime novel")
                    .coverImageUrl("https://book-covers.org/marek.krajewski/222")
                    .build();

    @BeforeEach
    public void setupTest() {
        bookRepository.deleteAll();
    }
    @Test
    public void givenNoBookExists_whenPostBook_thenSucceeds() throws Exception {
        // Given
        given(bookRepository.existsById(BOOK_ID)).willReturn(false);

        // When
        ResultActions response = mockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(book)));

        MvcResult result = response.andDo(print()).andReturn();
        
        // Then
        then(result.getResponse().getStatus())
            .as("Check HTTP response status code")
            .isEqualTo(200);

        then(bookRepository.findById(BOOK_ID))
            .as("Check if book ID is in the repository")
            .isNotNull();  
    }

   @Test
    public void givenBookExists_whenGetBook_thenSucceeds() throws Exception {
        // Given
        given(bookRepository.findById(BOOK_ID)).willReturn(Optional.of(book));

        // When
        ResultActions response = mockMvc.perform(get("/api/books/{bookID}",BOOK_ID)
            .accept(MediaType.APPLICATION_JSON));

        MvcResult result = response.andDo(print()).andReturn();
    
        // Then
        then(result.getResponse().getStatus())
            .as("Check HTTP response status code")
            .isEqualTo(200);

        then(result.getResponse().getHeader(HttpHeaders.CONTENT_TYPE))
            .as("Check if content type is JSON")
            .isEqualTo("application/json");
        
        response.andExpect(jsonPath("$.id",is(BOOK_ID)))
            .andExpect(jsonPath("$.isbn",is(book.getIsbn())))
            .andExpect(jsonPath("$.title",is(book.getTitle())));
    }
    @Test
    public void givenBookExists_whenPutBook_thenSucceeds() throws Exception {
        // Given
        given(bookRepository.existsById(BOOK_ID)).willReturn(true);
        given(bookRepository.findById(BOOK_ID)).willReturn(Optional.of(book));

        // When
        ResultActions response = mockMvc.perform(put("/api/books/{bookID}",BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book2)));

        MvcResult result = response.andDo(print()).andReturn();

        // Then
        then(result.getResponse().getStatus())
                .as("Check HTTP response status code")
                .isEqualTo(200);
    }
}
