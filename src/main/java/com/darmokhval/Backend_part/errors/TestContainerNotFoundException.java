package com.darmokhval.Backend_part.errors;

public class TestContainerNotFoundException extends RuntimeException{
    public TestContainerNotFoundException(Integer id) {
        super(String.format("Test container with id %d not found", id));
    }
    public TestContainerNotFoundException(String grade) {
        super(String.format("Test container for grade %s not found", grade));
    }
}
