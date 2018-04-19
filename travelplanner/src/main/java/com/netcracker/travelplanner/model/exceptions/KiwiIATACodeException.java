package com.netcracker.travelplanner.model.exceptions;

public class KiwiIATACodeException extends Throwable {

    public KiwiIATACodeException(){}

    public KiwiIATACodeException(Throwable throwable){
        this.initCause(throwable);
    }

    @Override
    public String getMessage() {
        return "IATA Code wasn't found";
    }
}
