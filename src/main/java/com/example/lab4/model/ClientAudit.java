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
@Table(name = "client_audit", schema = "public", catalog = "lszfngcv")
public class ClientAudit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name_old", nullable = true, length = 50)
    private String nameOld;
    @Basic
    @Column(name = "nem_new", nullable = true, length = 50)
    private String nemNew;
    @Basic
    @Column(name = "city_old", nullable = true, length = 50)
    private String cityOld;
    @Basic
    @Column(name = "city_new", nullable = true, length = 50)
    private String cityNew;
    @Basic
    @Column(name = "email_old", nullable = true, length = 50)
    private String emailOld;
    @Basic
    @Column(name = "email_new", nullable = true, length = 50)
    private String emailNew;

    public ClientAudit(String nameOld, String nemNew, String cityOld, String cityNew, String emailOld, String emailNew) {
        this.nameOld = nameOld;
        this.nemNew = nemNew;
        this.cityOld = cityOld;
        this.cityNew = cityNew;
        this.emailOld = emailOld;
        this.emailNew = emailNew;
    }
}
