package com.example.javas3.model.comment;

import com.example.javas3.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, length = 2000)
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "rating")
    private int rating;

    @ElementCollection
    @CollectionTable(name = "comment_photos", joinColumns = @JoinColumn(name = "comment_id"))
    @Column(name = "photo_url")
    private List<String> photos;
}
