package com.tapia.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserManagerException extends Exception {

    private ExceptionDetail exceptionDetail;

    public UserManagerException(String message){
        super(message);
        exceptionDetail = new ExceptionDetail();
    }

    public UserManagerException(String message, HttpStatus httpStatus) {
        super(message);
        exceptionDetail = new ExceptionDetail();
        exceptionDetail.setStatus(httpStatus);
        exceptionDetail.setMessage(message);
    }

}
