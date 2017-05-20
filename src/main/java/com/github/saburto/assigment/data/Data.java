package com.github.saburto.assigment.data;

public class Data {

    private final byte[] bytes;

    public Data(byte[] bytes) {
        this.bytes = bytes;
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
    
    

}
