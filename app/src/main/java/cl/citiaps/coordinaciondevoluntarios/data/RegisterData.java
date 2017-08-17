package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ian on 17-08-2017.
 */
@SuppressWarnings("serial")
public class RegisterData implements Serializable{
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String confirmPass;
    private int contact_phone_number;
    private int emergency_phone_number;
    private String birthday;
    private Boolean life_insurance;
    private int user_type_id;
    private Boolean enabled;
    private int Id;
    

    public RegisterData(String first_name, String last_name, String email, String password, String confirmPass){
        setConfirmPass(confirmPass);
        setEmail(email);
        setPassword(password);
        setFirst_name(first_name);
        setLast_name(last_name);
    }

    public RegisterData(RegisterData registerData, int contact_phone_number,
                        int emergency_phone_number, String birthday, Boolean life_insurance
                        , int user_type_id, Boolean enabled){
        setContact_phone_number(contact_phone_number);
        setEmergency_phone_number(emergency_phone_number);
        setBirthday(birthday);
        setConfirmPass(registerData.getConfirmPass());
        setEmail(registerData.getEmail());
        setPassword(registerData.getPassword());
        setFirst_name(registerData.getFirst_name());
        setLast_name(registerData.getLast_name());
        setLife_insurance(life_insurance);
        setUser_type_id(user_type_id);
        setEnabled(enabled);
    }

    public void setFirst_name(String first_name){this.first_name = first_name;}
    public void setLast_name(String last_name){this.last_name = last_name;}
    public void setEmail(String email){this.email = email;}
    public void setPassword(String password){this.password = password;}
    public void setConfirmPass(String confirmPass){this.confirmPass = confirmPass;}
    public void setBirthday(String birthday){this.birthday = birthday;}
    public void setContact_phone_number(int contact_phone_number){this.contact_phone_number = contact_phone_number;}
    public void setEmergency_phone_number(int emergency_phone_number){this.emergency_phone_number = emergency_phone_number;}
    public void setLife_insurance(Boolean life_insurance){this.life_insurance = life_insurance;}
    public void setEnabled(Boolean enabled){this.enabled = enabled;}
    public void setUser_type_id(int user_type_id){this.user_type_id = user_type_id;}
    public void setId(int Id){this.Id = Id;}

    public String getFirst_name(){return first_name;}
    public String getLast_name(){return last_name;}
    public String getPassword(){return password;}
    public String getEmail(){return email;}
    public String getConfirmPass(){return  confirmPass;}
    public int getContact_phone_number(){return contact_phone_number;}
    public int getEmergency_phone_number(){return emergency_phone_number;}
    public String getFecha(){return birthday;}
    public Boolean getLife_insurance(){return  life_insurance;}
    public Boolean getEnabled(){return enabled;}
    public int getUser_type_id(){return user_type_id;}
    public int getId(){return Id;}
}
