package com.example.lab4.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "book_audit", schema = "public", catalog = "lszfngcv")
public class BookAudit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "title_old", nullable = true, length = 50)
    private String titleOld;
    @Basic
    @Column(name = "title_new", nullable = true, length = 50)
    private String titleNew;
    @Basic
    @Column(name = "author_name_old", nullable = true, length = 50)
    private String authorNameOld;
    @Basic
    @Column(name = "author_name_new", nullable = true, length = 50)
    private String authorNameNew;
    @Basic
    @Column(name = "genre_old", nullable = true, length = 50)
    private String genreOld;
    @Basic
    @Column(name = "genre_new", nullable = true, length = 50)
    private String genreNew;
    @Basic
    @Column(name = "price_old", nullable = true, precision = 0)
    private Double priceOld;
    @Basic
    @Column(name = "price_new", nullable = true, precision = 0)
    private Double priceNew;

    public BookAudit(String titleOld, String titleNew, String authorNameOld, String authorNameNew, String genreOld, String genreNew, Double priceOld, Double priceNew) {
        this.titleOld = titleOld;
        this.titleNew = titleNew;
        this.authorNameOld = authorNameOld;
        this.authorNameNew = authorNameNew;
        this.genreOld = genreOld;
        this.genreNew = genreNew;
        this.priceOld = priceOld;
        this.priceNew = priceNew;
    }
}
