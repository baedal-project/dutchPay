package com.baedal.dutchpay.entity;

import com.baedal.dutchpay.controller.request.SignUpRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//무분별한 객체 생성을 막는다
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;


    private Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static Member of(SignUpRequestDto requestDto, String encodedPassword) {
        return new Member(requestDto.getEmail(), encodedPassword, requestDto.getNickname());
    }

}
