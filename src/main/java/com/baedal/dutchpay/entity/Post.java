package com.baedal.dutchpay.entity;

import com.baedal.dutchpay.controller.request.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Long pay;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private Long num;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)

    private Member member;
    public void update(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.location = postRequestDto.getLocation();
        this.num = postRequestDto.getNum();
        this.pay = postRequestDto.getPay();
        this.time = postRequestDto.getTime();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}
