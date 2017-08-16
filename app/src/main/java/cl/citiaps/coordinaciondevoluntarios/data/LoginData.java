package cl.citiaps.coordinaciondevoluntarios.data;

import java.util.Date;

/**
 * Created by kayjt on 13-09-2016.
 */
public class LoginData {

    private String username;
    private String password;
    private String token;
    private String expire;

    private int code;

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String parseDate(String oldDate){
        String[] parser = oldDate.split("T");
        String[] orden = parser[0].split("-");
        return orden[2]+"/"+orden[1]+"/"+orden[0];
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}

    public void setExpire(String expire){this.expire = expire;}
    public String getExpire(){return expire;}
    public String getFormatedExpire(){return parseDate(expire);}

    public void setCode(int code){this.code = code;}
    public int getCode(){return code;}
}
