package com.wbf.mutuelle.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity

@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private  Long id;

    public Notification(Long id, String msg, Date send_date, Date event_date, String receiver, String phone, String role) {
        this.id= id;
        this.msg = msg;
        this.send_date = send_date;
        this.event_date = event_date;
        this.receiver = receiver;
        this.phone = phone;
        this.role = role;
    }

    private  String msg;
    private Date send_date;
    private Date event_date;
    private String  receiver;
    private String phone;
    private String role;
}
