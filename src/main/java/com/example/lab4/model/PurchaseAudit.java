package com.example.lab4.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@Entity
@Table(name = "purchase_audit", schema = "public", catalog = "lszfngcv")
public class PurchaseAudit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "book_id_old", nullable = true)
    private Integer bookIdOld;
    @Basic
    @Column(name = "book_id_new", nullable = true)
    private Integer bookIdNew;
    @Basic
    @Column(name = "client_id_old", nullable = true)
    private Integer clientIdOld;
    @Basic
    @Column(name = "client_id_new", nullable = true)
    private Integer clientIdNew;
    @Basic
    @Column(name = "amount_old", nullable = false)
    private Integer amountOld;
    @Basic
    @Column(name = "amount_new", nullable = false)
    private Integer amountNew;

    public PurchaseAudit(Integer bookIdOld, Integer bookIdNew, Integer clientIdOld, Integer clientIdNew, Integer amountOld, Integer amountNew) {
        this.bookIdOld = bookIdOld;
        this.bookIdNew = bookIdNew;
        this.clientIdOld = clientIdOld;
        this.clientIdNew = clientIdNew;
        this.amountOld = amountOld;
        this.amountNew = amountNew;
    }
}
