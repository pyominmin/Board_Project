package project.board.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.board.entity.MemberEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor //기본생성자를 자동으로 만들어준다
@ToString
// DTO는 회원정보에 필요한 내용을 필드로 정의
public class MemberDTO {
    private Long num;
    private Long memberNum;
    private String birthDateFormatted;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberYear;
    private String memberMonth;
    private String memberDay;
    private String memberPno;
    private String nickname;
    private String memberGender;

    public MemberDTO(Long memberNum, String memberEmail, String memberPassword, String memberName,
                     String memberYear, String memberMonth, String memberDay,
                     String memberGender, String memberPno, String birthDateFormatted,
                     String nickname) {

        this.memberNum = memberNum;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberYear = memberYear;
        this.memberMonth = memberMonth;
        this.memberDay = memberDay;
        this.memberPno = memberPno;
        this.birthDateFormatted = birthDateFormatted;
        this.nickname = nickname;
        this.memberGender = memberGender;
    }


    public static MemberDTO fromEntity(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberNum(memberEntity.getNum()); // 이 부분 수정
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setBirthDateFormatted(memberEntity.getBirthDateFormatted());
        memberDTO.setMemberPno(memberEntity.getMemberPno());
        memberDTO.setNickname(memberEntity.getNickname());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        return memberDTO;
    }


}

