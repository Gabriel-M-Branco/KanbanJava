package com.kanbanjava.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RespostaApi<T> {
    private T data;
    private String message;
    private int status;
}
