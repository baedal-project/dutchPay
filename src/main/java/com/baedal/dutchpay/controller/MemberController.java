package com.baedal.dutchpay.controller;

import com.baedal.dutchpay.controller.request.LoginRequestDto;
import com.baedal.dutchpay.controller.request.SignUpRequestDto;
import com.baedal.dutchpay.controller.response.ResponseDto;
import com.baedal.dutchpay.entity.Member;
import com.baedal.dutchpay.entity.jwttoken.JwtTokenDto;
import com.baedal.dutchpay.jwt.TokenProvider;
import com.baedal.dutchpay.jwt.userdetails.UserDetailsImpl;
import com.baedal.dutchpay.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;


    // 회원가입
    @PostMapping("/api/members/signup")
    public ResponseDto<?> signup(@RequestBody SignUpRequestDto requestDto) {
        return memberService.signup(requestDto);
    }

    // 로그인
    @PostMapping("/api/members/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto,
                                HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    //토큰재발급
    @GetMapping("/api/auth/members/reissue")
    public ResponseDto<?> reissue(
            @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response
    ) {
        Member member = userDetails.getMember();
        JwtTokenDto tokenDto = tokenProvider.generateTokenDto(member);
        memberService.tokenToHeaders(tokenDto, response);

        return ResponseDto.success("재발급 완료");
    }

    // 로그아웃
    @PostMapping("/api/auth/members/logout")
    public ResponseDto<?> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }

    @PostMapping("/api/members/check/email")
    public ResponseDto<?> emailCheck(@RequestBody SignUpRequestDto requestDto){
        return memberService.checkDupEmail(requestDto);
    }

    @PostMapping("/api/members/check/nick")
    public ResponseDto<?> nickCheck(@RequestBody SignUpRequestDto requestDto){
        return memberService.checkDupNickname(requestDto);
    }
}
