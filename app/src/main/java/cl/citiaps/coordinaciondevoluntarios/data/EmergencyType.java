package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;

/**
 * Created by Ian on 31-07-2017.
 */

@SuppressWarnings("serial")
public class EmergencyType implements Serializable{
    private int Id;
    private String type;


    public EmergencyType(int Id, String type) {
        setId(Id);
        setType(type);
    }

    public void setId(int Id){this.Id = Id;}
    public void setType(String type){this.type = type;}

    public int getId(){return Id;}
    public String getType(){return type;}
}
