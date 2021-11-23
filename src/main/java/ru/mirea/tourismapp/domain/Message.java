package ru.mirea.tourismapp.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="msges")

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long userId;
    private String postDate;
}
