package com.baedal.dutchpay.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private String location;
    private Long pay;
    private String time;
    private Long num;

//  private boolean deleted = false;
}