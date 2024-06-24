package project.board.controller;



import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.board.dto.MemberDTO;
import project.board.entity.MemberEntity;
import project.board.repository.MemberRepository;
import project.board.service.MemberService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자 주입
    //자동적으로 service class에 대한 객체를 주입을 받는다.
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 전달해온 값을 받는다. RequestParam이라는걸 이용해서 담겨온 값을 옮겨 담는다.
    // 파라미터를 String 변수로 받아서 service class로 넘겨주고 service class에서는 repository로 넘겨주고
    // repository에서는 데이터베이스로 넘기는 작업


    //이메일 중복인지 가입 시 체크하는 항목
    @PostMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String memberEmail) {
        boolean exists = memberService.emailExists(memberEmail);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }


    @PostMapping("join/join")
    public String save(@ModelAttribute MemberDTO memberDTO, Model model) {

        log.info("memerDTO={}", memberDTO);
        String birthDate = memberDTO.getMemberYear() + memberDTO.getMemberMonth() + memberDTO.getMemberDay();
        memberDTO.setBirthDateFormatted(birthDate);

        System.out.println("Formatted Birth Date: " + memberDTO.getBirthDateFormatted());
        System.out.println(memberDTO.toString());

        try {
            memberService.save(memberDTO, memberDTO.getMemberPassword());
            return "/html/loginForm";
        } catch (Exception e) {
            log.error("Error while encrypting password", e);
            return "error"; // 적절한 오류 페이지로 리다이렉션
        }
    }
}