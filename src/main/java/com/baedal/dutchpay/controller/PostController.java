package com.baedal.dutchpay.controller;

import com.baedal.dutchpay.controller.request.PostRequestDto;
import com.baedal.dutchpay.controller.response.ResponseDto;
import com.baedal.dutchpay.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/api/auth/post")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto,
                                     HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }
    //게시글전체조회
    @GetMapping(value = "/api/post")
    public ResponseDto<?> getAllPosts(@PageableDefault(page = 0, size = 10, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getAllPost(pageable);
    }
    //게시글 하나만조회
    @GetMapping(value = "/post/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시글 수정
    @PutMapping(value = "/api/auth/post/{id}")
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,
                                     HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);
    }

    // 게시글 삭제
    @DeleteMapping(value = "/api/auth/post/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id,
                                     HttpServletRequest request) {
        return postService.deletePost(id, request);
    }

}
