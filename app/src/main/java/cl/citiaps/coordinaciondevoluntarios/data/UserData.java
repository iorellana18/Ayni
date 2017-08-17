package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Joaco on 13-04-17.
 */
@SuppressWarnings("serial")
public class UserData implements Serializable{
    private int Id;
    private String email;
    private String first_name;
    private String last_name;
    private String contact_phone_number;
    private String emergency_phone_number;
    private boolean life_insurance;
    private int userID;
    private Volunteer Volunteer;


    public UserData(String first_name, String last_name, String contact_phone_number, String
                    emergency_phone_number, boolean life_insurance){
        setFirst_name(first_name);
        setLast_name(last_name);
        setContact_phone_number(contact_phone_number);
        setEmergency_phone_number(emergency_phone_number);
        setLife_insurance(life_insurance);
    }

    public void setVolunteer(Volunteer Volunteer){this.Volunteer = Volunteer;}
    public Volunteer getVolunteer(){return Volunteer;}

    public void setId(int Id){this.Id = Id;}
    public int getId(){return Id;}

    public int getUserID() {
        return userID;
    }

    public String getContact_phone_number() {
        return contact_phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getEmergency_phone_number() {
        return emergency_phone_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setContact_phone_number(String contact_phone_number) {
        this.contact_phone_number = contact_phone_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmergency_phone_number(String emergency_phone_number) {
        this.emergency_phone_number = emergency_phone_number;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public void setLife_insurance(boolean life_insurance) { this.life_insurance=life_insurance;}

    public boolean getLife_insurance() {return life_insurance;}


}
