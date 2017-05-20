package com.github.saburto.assigment.repository;

public class IdAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public IdAlreadyExistsException(String id, String side) {
        super(String.format("Id [%s] already exists for side %s", id, side));
    }
}
