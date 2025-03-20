package com.example.post2.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private final String content;

    public CommentUpdateRequestDto(String content) {
        this.content = content;
    }
}
