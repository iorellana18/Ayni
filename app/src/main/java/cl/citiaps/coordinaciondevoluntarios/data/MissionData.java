package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kayjt on 21-09-2016.
 */

@SuppressWarnings("serial")
public class MissionData implements Serializable{

    private int Id;
    private EmergencyData Emergency;
    private float meeting_point_latitude;
    private float meeting_point_longitude;
    private String description;
    private String start_date;
    private String finish_date;
    private String title;
    private String meeting_point_address;
    private List<Abilities> Abilities;
    private List<Files> Files;
    private List<ProblemData> Problems;



    private String parseDate(String oldDate){
        String[] parser = oldDate.split("T");
        String[] orden = parser[0].split("-");
        return orden[2]+"/"+orden[1]+"/"+orden[0];
    }

    public void setId(int Id){this.Id = Id;}
    public int getId(){return Id;}

    public void setEmergency(EmergencyData Emergency){this.Emergency = Emergency;}
    public EmergencyData getEmergency(){return Emergency;}

    public void setMeeting_point_latitude(float meeting_point_latitude){this.meeting_point_latitude
        = meeting_point_latitude;}
    public float getMeeting_point_latitude(){return meeting_point_latitude;}

    public void setMeeting_point_longitude(float meeting_point_longitude){
        this.meeting_point_longitude = meeting_point_longitude;}
    public float getMeeting_point_longitude(){return meeting_point_longitude;}

    public void setDescription(String description){this.description = description;}
    public String getDescription(){return description;}

    public void setStart_date(String start_date){this.start_date = start_date;}
    public String getStart_date(){return parseDate(start_date);}

    public void setFinish_date(String finish_date){this.finish_date = finish_date;}
    public String getFinish_date(){return parseDate(finish_date);}

    public void setTitle(String title){this.title = title;}
    public String getTitle(){return title;}

    public void setMeeting_point_address(String meeting_point_address){this.meeting_point_address =
        meeting_point_address;}
    public String getMeeting_point_address(){return meeting_point_address;}

    public void setAbilities(List<Abilities> Abilities){this.Abilities = Abilities;}
    public List<Abilities> getAbilities(){return  Abilities;}

    public void setFiles(List<Files> Files){this.Files = Files;}
    public List<Files> getFiles(){return Files;}

    public void setProblems(List<ProblemData> Problems){this.Problems = Problems;}
    public List<ProblemData> getProblems(){return Problems;}
}

