package project.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.board.dto.CommentDTO;
import project.board.entity.CommentEntity;
import project.board.service.CommentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentDTO commentDTO) {
//        try {
            if (commentDTO.getContent() == null || commentDTO.getContent().trim().equals("")) {
                return ResponseEntity.badRequest().body("댓글을 입력해주세요.");
            } else {
                CommentEntity commentEntity = CommentEntity.toEntity(commentDTO);
                commentService.addComment(commentEntity);
            }


            return ResponseEntity.ok("댓글이 성공적으로 추가되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("댓글 추가 중 오류가 발생했습니다.");
//        }
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByBoardId(@PathVariable Long boardId) {
        List<CommentDTO> comments = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.ok(comments);
    }


}
