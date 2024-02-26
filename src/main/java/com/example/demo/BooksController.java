package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BooksController {

    @Autowired //an annotation for automatic dependency injection
    private BooksRepository booksRep;

    // Read operation
    @GetMapping("/books") //adds the GET endpoint
    public ResponseEntity<List<Books>> getAllBooks()
    {
        try {
            List<Books> booksList = new ArrayList<>();
            booksRep.findAll().forEach(booksList::add);

            if (booksList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(booksList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}") //adds the GET endpoint
    public ResponseEntity<Books> getBooksById(@PathVariable Long id) {
        Optional<Books> booksObj = booksRep.findById(id);
        if (booksObj.isPresent()) {
            return new ResponseEntity<>(booksObj.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books") //adds the POST endpoint
    public ResponseEntity<Books> addBooks(@RequestBody Books newBooks) {
        try {
            Books booObj = booksRep.save(newBooks);
            return new ResponseEntity<>(booObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books/{id}")//adds the POST endpoint
    public ResponseEntity<Books> updateBooks(@PathVariable Long id, @RequestBody Books books) {
        try {
            Optional<Books> booksData = booksRep.findById(id);
            if (booksData.isPresent()) {
                Books updatedBooksData = booksData.get();
                updatedBooksData.setTitle(books.getTitle());
                updatedBooksData.setDescription(books.getDescription());
                updatedBooksData.setAuthor(books.getAuthor());
                updatedBooksData.setCategory(books.getCategory());
                updatedBooksData.setYearPublished(books.getYearPublished());

                Books booObj = booksRep.save(updatedBooksData);
                return new ResponseEntity<>(booObj, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBooks(@PathVariable Long id) {
        try {
            booksRep.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

