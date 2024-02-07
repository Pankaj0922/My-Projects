package com.bookstore.controller;

import com.bookstore.entity.MyBookList;
import com.bookstore.service.MyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService service;

    @Autowired
    private MyBookService bookService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/book_register")
    public String bookRegister() {

        return "book_register";
    }

    @GetMapping("/available_books")
    public ModelAndView getAllBook() {

        List<Book> list = service.getAllBook();
        return new ModelAndView("bookList", "book", list);
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b) {
        service.save(b);

        return "redirect:/available_books";
    }

    @GetMapping("/my_book")
    public String getMyBooks(Model model) {
        List<MyBookList> list = bookService.getAllMyBooks();
        model.addAttribute("book", list);
        return "myBooks";
    }

    @GetMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        Book b = service.getBookById(id);
        MyBookList list = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice());
        bookService.saveMyBook(list);
        return "redirect:/my_book";
    }

    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        Book b = service.getBookById(id);
        model.addAttribute("book", b);
        return "bookEdit";
    }

    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        service.deleteById(id);
        return "redirect:/available_books";
    }

}
