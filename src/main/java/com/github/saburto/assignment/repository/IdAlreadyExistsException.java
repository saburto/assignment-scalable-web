package com.github.saburto.assignment.repository;

import com.github.saburto.assignment.data.Side;

public class IdAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public IdAlreadyExistsException(String id, Side side) {
        super(String.format("Id [%s] already exists for side %s", id, side));
    }
}
