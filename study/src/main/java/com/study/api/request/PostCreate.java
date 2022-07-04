package com.study.api.request;

import javax.validation.constraints.NotBlank;

import com.study.api.exception.InvalidRequest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Getter
@Setter
public class PostCreate {
    
    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (this.title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 바보를 사용 할 수 없습니다.");
        }
    }
}
