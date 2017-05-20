package com.github.saburto.assignment.rest;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataRequest {
    private final byte[] data;

    @JsonCreator
    public DataRequest(@JsonProperty("data") String data) {
        this.data = Base64.getDecoder().decode(data);
    }

    public byte[] getData() {
        return data;
    }
}
