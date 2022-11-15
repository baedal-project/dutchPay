package com.baedal.dutchpay.service;

import com.baedal.dutchpay.controller.request.PostRequestDto;
import com.baedal.dutchpay.controller.response.PostResponseDto;
import com.baedal.dutchpay.controller.response.ResponseDto;
import com.baedal.dutchpay.entity.Member;
import com.baedal.dutchpay.entity.Post;
import com.baedal.dutchpay.jwt.TokenProvider;
import com.baedal.dutchpay.repositrory.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .location(requestDto.getLocation())
                .pay(requestDto.getPay())
                .when(requestDto.getWhen())
                .num(requestDto.getNum())
                .member(member)
                .build();

        postRepository.save(post);

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getMember().getNickname())
                        .location(post.getLocation())
                        .pay(post.getPay())
                        .when(post.getWhen())
                        .num(post.getNum())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }
    //게시글 전체조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPost(Pageable pageable) {
        return ResponseDto.success(postRepository.findAll(pageable));
    }
    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }
        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getMember().getNickname())
                        .location(post.getLocation())
                        .pay(post.getPay())
                        .when(post.getWhen())
                        .num(post.getNum())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }
    // 게시글 수정
    @Transactional
    public ResponseDto<Post> updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        post.update(requestDto);
        return ResponseDto.success(post);
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST","작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
        return ResponseDto.success("delete success");
    }
    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
