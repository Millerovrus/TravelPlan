package com.netcracker.travelplanner.model.exceptions;

public class UFSIOException extends APIException {

    public UFSIOException(){}

    public UFSIOException(Throwable throwable){
        this.initCause(throwable);
    }

    @Override
    public String getMessage() {
        return "UFS parser has thrown IOException";
    }
}
