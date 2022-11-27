package com.example.lab4.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@EqualsAndHashCode
@Data
@Entity
@NoArgsConstructor
@Table(name = "audit", schema = "public", catalog = "lszfngcv")
public class DBChange {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "entity", nullable = false, length = 50)
    private String entity;
    @Basic
    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;
    @Basic
    @Column(name = "entry_id", nullable = false)
    private Integer entryId;
    @Basic
    @Column(name = "time", nullable = false)
    private Date time;

    public DBChange(String entity, Integer entryId, String eventType, Date time) {
        this.entity = entity;
        this.entryId = entryId;
        this.eventType = eventType;
        this.time = time;
    }
}
