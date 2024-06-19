package project.board.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.board.dto.MemberDTO;
import project.board.entity.MemberEntity;
import project.board.repository.MemberRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public void save(MemberDTO memberDTO, String memberPassword) {
        memberDTO.setMemberPassword(passwordEncoder.encode(memberPassword));
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public boolean emailExists(String memberEmail) {
        return memberRepository.existsByMemberEmail(memberEmail);
    }

}
