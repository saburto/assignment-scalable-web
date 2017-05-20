package com.github.saburto.assignment.repository;

import com.github.saburto.assignment.data.Side;

public class FileNoYetExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileNoYetExistsException(String id, Side side) {
        super(String.format("File for Id [%s] not exists yet for side %s", id, side));
    }
}
