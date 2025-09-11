package com.wbf.mutuelle.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String category;
    private BigDecimal amount;
    private Date event_date;
    @ManyToMany
private List<Member>members;

}