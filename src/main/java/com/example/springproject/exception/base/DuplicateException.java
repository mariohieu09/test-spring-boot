package com.example.springproject.exception.base;

import lombok.Data;

@Data
public class DuplicateException extends BaseException{

    public DuplicateException(String code){
        setCode(code);
        setStatus(StatusConstants.BAD_REQUEST);
    };
}
