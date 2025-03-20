package com.example.post2.service;

import com.example.post2.dto.*;
import com.example.post2.entity.Post;
import com.example.post2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostSaveResponseDto savePost(PostSaveRequestDto requestDto) {
        Post post = new Post(requestDto.getTitle());
        postRepository.save(post);
        return new PostSaveResponseDto(post.getId(), post.getTitle());
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Post not found with id " + id)
        );

        return new PostResponseDto(post.getId(), post.getTitle());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> new PostResponseDto(post.getId(), post.getTitle()))
                .collect(Collectors.toList());
    }

    @Transactional
    public PostUpdateResponseDto updatePost(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Post not found with id " + id)
        );

        post.updateTitle(requestDto.getTitle());
        return new PostUpdateResponseDto(post.getId(), post.getTitle());
    }

    @Transactional
    public void deletePost(Long id) {
        if(!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post not found with id " + id);
        }

        postRepository.deleteById(id);
    }
}
