package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;
import java.net.UnknownServiceException;

/**
 * Created by Ian on 10-08-2017.
 */
@SuppressWarnings("serial")
public class Answer implements Serializable{
    private int Id;
    private String createAt;
    private String answer;
    private UserData User;


    public void setId(int Id){this.Id = Id;}
    public int getId(){return Id;}

    public void setCreateAt(String createAt){this.createAt = createAt;}
    public String getCreateAt(){return createAt;}

    public void setAnswer(String answer){this.answer = answer;}
    public String getAnswer(){return answer;}

    public void setUser(UserData User){this.User = User;}
    public UserData getUser(){return User;}
}
