package com.baedal.dutchpay.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String location;
    private Long pay;
    private String time;
    private Long num;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
