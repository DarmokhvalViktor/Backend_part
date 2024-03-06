package com.darmokhval.Backend_part.exception;

public class WorksheetNotFoundException extends RuntimeException{
    public WorksheetNotFoundException(Integer id) {
        super(String.format("Worksheet with id %d not found", id));
    }
}
