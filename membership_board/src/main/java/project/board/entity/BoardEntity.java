package project.board.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
<<<<<<< HEAD
    private Integer id;
=======
    private Long id;
>>>>>>> Board

    @Column(name = "user_id", nullable = false)
    private String user_id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filepath")
    private String filepath;

<<<<<<< HEAD
=======
    @Column(name = "view_count")
    private int viewCount;

//    @Column(name = "like_count")
//    private int likeCount;

>>>>>>> Board
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
