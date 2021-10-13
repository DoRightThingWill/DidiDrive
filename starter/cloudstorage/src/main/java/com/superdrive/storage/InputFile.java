package com.superdrive.storage;

public class InputFile {

    private String fileName;
    private String uri;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString (){

        return "fileName -->"+fileName+"; uri-->"+uri;
    }
}
