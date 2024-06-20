package project.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.board.entity.BoardEntity;
import project.board.entity.CommentEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment where board_id? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

}
