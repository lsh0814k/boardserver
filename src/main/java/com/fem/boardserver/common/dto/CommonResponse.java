package com.fem.boardserver.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
    private HttpStatus status;
    private String message;
    private T result;
}
