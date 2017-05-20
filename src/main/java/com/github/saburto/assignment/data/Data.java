package com.github.saburto.assignment.data;

public class Data {

    private final byte[] bytes;
    private final String id;

    public Data(byte[] bytes, String id) {
        this.bytes = bytes;
        this.id = id;
    }

    public boolean isSameSize(Data data2) {
        return bytes.length == data2.bytes.length;
    }

    public int size() {
        return bytes.length;
    }

    public boolean hasSameByte(int index, Data rigth) {
        return bytes[index] == rigth.bytes[index];
    }

    public String getId() {
        return id;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
