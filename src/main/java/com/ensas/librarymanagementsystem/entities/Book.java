package com.ensas.librarymanagementsystem.entities;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Slf4j
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 6,max = 30)
    @Column(length = 55, nullable = false,unique = true)
    private String isbn;
    @NotBlank
    @Size(min = 6,max = 25)
    @Column(length = 100, nullable = false,unique = true)
    private String name;
    @NotBlank
    @Size(min = 15)
    @Column(nullable = false)
    private String description;
    private int quantity;

    @ManyToMany
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")})
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Borrow> borrows;

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }


}
