/**
 * Created by : Alan Nascimento on 09/12/2022
 * inside the package - com.myapi.controllers
 */
package com.myapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserException extends RuntimeException{

    private static final long serialVersionUID = -6221868913026910626L;

    public UserException(String mensagem ) {
        super(mensagem);
    }

}
