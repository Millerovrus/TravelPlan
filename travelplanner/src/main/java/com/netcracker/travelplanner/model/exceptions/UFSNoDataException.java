package com.netcracker.travelplanner.model.exceptions;

public class UFSNoDataException extends APIException {

    public UFSNoDataException(){}

    public UFSNoDataException(Throwable throwable){
        this.initCause(throwable);
    }

    @Override
    public String getMessage() {
        return "UFS parser hasn't returned any data";
    }
}
