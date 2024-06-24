package project.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import project.board.dto.CommentDTO;

import java.time.LocalDateTime;

@Entity
@Data
public class CommentEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "board_id", nullable = false) // name이라는 것은 DB에 생성될 컬럼의 이름을 뜻한다.
        private BoardEntity board;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id", nullable = false)
        private MemberEntity member;

        @Column(name = "content", nullable = false)
        private String content;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @PrePersist
        protected void onCreate() {
            createdAt = LocalDateTime.now();
        }

        public static CommentEntity toEntity(CommentDTO commentDTO) {
                CommentEntity commentEntity = new CommentEntity();
                commentEntity.setContent(commentDTO.getContent());

                // Set BoardEntity
                BoardEntity boardEntity = new BoardEntity();
                boardEntity.setId(commentDTO.getBoardId()); // Assuming BoardEntity has getId() method
                commentEntity.setBoard(boardEntity);

                // Set MemberEntity
                MemberEntity memberEntity = new MemberEntity();
                memberEntity.setNum(commentDTO.getMemberId()); // Assuming MemberEntity has getId() method
                commentEntity.setMember(memberEntity);

                return commentEntity;
        }


}
