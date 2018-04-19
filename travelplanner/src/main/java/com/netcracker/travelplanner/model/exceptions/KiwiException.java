package com.netcracker.travelplanner.model.exceptions;

public class KiwiException extends APIException {

    public KiwiException(){};

    public KiwiException(Throwable throwable){
        this.initCause(throwable);
    }

    @Override
    public String getMessage() {
        return "Kiwi integration error";
    }
}
