package project.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {


    @RequestMapping("/")
    public String home(){
        System.out.println("HomeController.index");
        return "redirect:board/list"; // => templates 폴더의 main/index.html을 찾아감
    }


}
