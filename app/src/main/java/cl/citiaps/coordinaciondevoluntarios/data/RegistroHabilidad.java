package cl.citiaps.coordinaciondevoluntarios.data;

import java.util.List;

/**
 * Created by Ian on 17-08-2017.
 */

public class RegistroHabilidad {

    private int volunteer_status_id;
    private int user_id;
    private List<Abilities> Abilities;
    private String token;


    public RegistroHabilidad(int user_id, List<Abilities> Abilities, String token){
        setVolunteer_status_id(1);
        setAbilities(Abilities);
        setToken(token);
        setUser_id(user_id);
    }

    public void setVolunteer_status_id(int volunteer_status_id){this.volunteer_status_id = volunteer_status_id;}
    public void setUser_id(int user_id){this.user_id = user_id;}
    public void setAbilities(List<Abilities> Abilities){this.Abilities = Abilities;}
    public void setToken(String token){this.token = token;}

    public int getVolunteer_status_id(){return  volunteer_status_id;}
    public int getUser_id(){return user_id;}
    public List<Abilities> getAbilities(){return  Abilities;}
    public String getToken(){return token;}
}
