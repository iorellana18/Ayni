package cl.citiaps.coordinaciondevoluntarios.data;

/**
 * Created by Ian on 04-08-2017.
 */

public class LoginResponse {
    private int code;
    private String message;

    public void setCode(int code){this.code = code;}
    public int getCode(){return code;}

    public void setMessage(String message){this.message = message;}
    public String getMessage(){return  message;}
}
