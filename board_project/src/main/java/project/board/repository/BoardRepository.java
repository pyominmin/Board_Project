package project.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.board.dto.BoardDTO;
import project.board.entity.BoardEntity;

import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    Page<BoardEntity> findByTitleContaining(String searchKeyword, Pageable pageable);


}
