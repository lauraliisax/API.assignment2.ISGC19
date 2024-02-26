package com.example.demo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="book")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String author;
    @Column
    private String category;
    @Column
    private int yearPublished;
}
