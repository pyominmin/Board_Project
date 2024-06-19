package project.board.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.board.entity.BoardEntity;
import project.board.service.BoardService;
import project.board.service.LoginService;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private LoginService loginService;


    public BoardController(BoardService boardService) {
    }

    // 글작성
    @GetMapping("/write")
    public String boardWrite(HttpSession session, Model model) {
        if(session.getAttribute("loginEmail") != null) {
            model.addAttribute("isLogin", true);
            return "html/boardWrite";
        } else {
            // 로그인 실패
            model.addAttribute("isLogin", false);
            model.addAttribute("message", "로그인 이후 게시글 작성을 이용하실 수 있습니다.");
            model.addAttribute("searchUrl", "/member/login");
            return "html/message";
        }
    }

    // 글작성 처리
    @PostMapping("/writepro")
    public String boardWritePro(BoardEntity board, Model model, MultipartFile file) throws Exception {
        boardService.boardWrite(board, file);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "html/message"; // 또는 필요한 페이지로 리다이렉트
    }

    // 게시글 리스트 처리
    @GetMapping("/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                            HttpSession session) {
        Page<BoardEntity> page;
        System.out.println( session.getAttribute("loginEmail"));

        if (searchKeyword == null || searchKeyword.isEmpty()) {
            page = boardService.boardList(pageable);
        } else {
            page = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = page.getNumber() + 1;
        int startPage = Math.max(nowPage - 2, 1);
        int endPage = Math.min(nowPage + 3, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("searchKeyword", searchKeyword); // 검색 키워드 추가

        // 로그인 상태면 true
        if(session.getAttribute("loginEmail") != null) {
            model.addAttribute("isLogin", true);
        } else {
            model.addAttribute("isLogin", false);
        }

        return "html/boardList";
    }

    // 특정 게시글 불러오기
    @GetMapping("/view/{id}")
    public String boardView(@PathVariable Integer id, Model model,
                            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                            @RequestParam(value = "page", required = false) Integer page, HttpSession session) {
//        System.out.println("사용자가 이용중이던 페이지는 " + page);
//        System.out.println("사용자가 검색했던 검색어는 " + searchKeyword);

        model.addAttribute("board", boardService.boardView(id));
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("page", page);

        // 로그인 상태면 true
        if(session.getAttribute("loginEmail") != null) {
            model.addAttribute("isLogin", true);
        } else {
            model.addAttribute("isLogin", false);
        }

        return "html/boardView";
    }


    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String boardDelete(@PathVariable Integer id, Model model) {
        boardService.boardDelete(id);

        model.addAttribute("message", "글 삭제가 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "html/message";
    }

    // 게시글 수정
    @GetMapping("/modify/{id}")
    public String boardModify(@PathVariable Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "html/boardModify";
    }

    // 게시글 수정 처리
    @PostMapping("/update/{id}")
    public String boardUpdate(@PathVariable Integer id, BoardEntity board, Model model, MultipartFile file) throws Exception {

        BoardEntity boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.boardWrite(boardTemp, file);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        
        return  "html/message";
    }
}
