package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ian on 01-08-2017.
 */
@SuppressWarnings("serial")
public class Volunteer implements Serializable{
    private int Id;
    private int volunteer_status_id;
    private int user_id;
    private List<Abilities> Abilities;



    public void setId(int Id){this.Id = Id;}
    public int getId(){return Id;}

    public void setVolunteer_status_id(int volunteer_status_id){this.volunteer_status_id = volunteer_status_id;}
    public int getVolunteer_status_id(){return volunteer_status_id;}

    public void setUser_id(int user_id){this.user_id = user_id;}
    public int getUser_id(){return user_id;}

    public void setAbilities(List<Abilities> Abilities){this.Abilities = Abilities;}
    public List<Abilities> getAbilities(){return Abilities;}
}

