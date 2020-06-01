package io.swagger.api;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-06-01T13:01:38.029+05:30")

public class ApiException extends Exception{
    private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
