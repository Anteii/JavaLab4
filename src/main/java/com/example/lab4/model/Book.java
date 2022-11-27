package com.example.lab4.model;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book implements Serializable, Mappable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "book_id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    @Basic
    @Column(name = "author_name", nullable = false, length = 50)
    private String authorName;
    @Basic
    @Column(name = "genre", nullable = false, length = 50)
    private String genre;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private Double price;

    public Book(String title, String authorName, String genre, Double price) {
        update(title, authorName, genre, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!Objects.equals(id, book.id)) return false;
        if (!Objects.equals(title, book.title)) return false;
        if (!Objects.equals(authorName, book.authorName)) return false;
        if (!Objects.equals(genre, book.genre)) return false;
        return Objects.equals(price, book.price);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    public void update(String title, String authorName, String genre, Double price) {
        this.title = title;
        this.authorName = authorName;
        this.genre = genre;
        this.price = price;
    }


    @Override
    public Map<String, String> toMap(){
        return Map.of("id", String.valueOf(id),
                "title", String.valueOf(title),
                "authorName", String.valueOf(authorName),
                "genre", String.valueOf(genre),
                "price", String.valueOf(price));
    }

    public static Book fromMap(Map<String, String> map){
        return map == null ? new Book() : new Book(Integer.valueOf(map.get("id")), map.get("title"),
                map.get("authorName"), map.get("genre"), Double.valueOf(map.get("price")));
    }
}
