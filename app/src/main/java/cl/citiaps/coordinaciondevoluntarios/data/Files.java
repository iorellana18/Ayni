package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;

/**
 * Created by Ian on 02-08-2017.
 */
@SuppressWarnings("serial")
public class Files implements Serializable{
    private int Id;
    private int mission_id;
    private String file;


    public void setId(int Id){this.Id = Id;}
    public int getId(){return Id;}

    public void setMission_id(int mission_id){this.mission_id = mission_id;}
    public int getMission_id(){return mission_id;}

    public void setFile(String file){this.file = file;}
    public String getFile(){return file;}
}
