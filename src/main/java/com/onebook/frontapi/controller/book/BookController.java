package com.onebook.frontapi.controller.book;

import com.onebook.frontapi.dto.book.BookDTO;
import com.onebook.frontapi.service.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final BookService bookService;

    @GetMapping("/newbooks")
    public String newBooks(Model model, Pageable pageable) {
        Page<BookDTO> bookDTOPage = bookService.newBooks(pageable);
        log.info("page: {}", bookDTOPage);
        model.addAttribute("bookList", bookDTOPage.getContent());

        return "index";
    }


    @GetMapping("/bookDetail")
    public String bookDetail(@RequestParam("bookId") long bookId, Model model) {
        BookDTO book = bookService.getBook(bookId);

        log.info("bookId: {}", book.getBookId());
        log.info("bookTitle: {}", book.getTitle());
        model.addAttribute("book", book);

        return "book/bookDetail";
    }
}