package com.example.demo.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {
    private Boolean success;
    private String status;
    private T data;

    public ApiResponse( String status, T data) {
        this.status = status;
        this.data = data;
    }

}
