package com.projeto3.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class ConflictException extends  RuntimeException{
    public ConflictException(String mensagem){
        super(mensagem);
    }
    public ConflictException(String mensagem, Throwable throwable){
        super(mensagem);
    }
}

//O Throwable serve para guardar a causa
// original de um erro dentro da sua própria exceção personalizada