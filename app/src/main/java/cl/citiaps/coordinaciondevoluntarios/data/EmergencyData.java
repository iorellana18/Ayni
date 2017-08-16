package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;

/**
 * Created by Mok on 14-09-16.
 */

@SuppressWarnings("serial")
public class EmergencyData implements Serializable{
    private int Id;
    private String title;
    private UserData user;
    private String meeting_point_address;
    private String region;
    private String createAt;
    private EmergencyType Emergency_type;
    private float place_latitude;
    private float place_longitude;
    private float place_radius;
    private String description;

    public String getFullAdress(){
        return meeting_point_address+", "+region;
    }

    private String parseDate(String oldDate){
        String[] parser = oldDate.split("T");
        String[] orden = parser[0].split("-");
        return orden[2]+"/"+orden[1]+"/"+orden[0];
    }

    public void setId(int Id){this.Id = Id;}
    public int getId(){return Id;}

    public void setTitle(String title){this.title = title;}
    public String getTitle(){return title;}

    public void setUser(UserData user){this.user = user;}
    public UserData getUser(){return user;}

    public void setMeeting_point_address(String meeting_point_address){this.meeting_point_address =
            meeting_point_address;}
    public String getMeeting_point_address(){return meeting_point_address;}

    public void setRegion(String region){this.region = region;}
    public String getRegion(){return region;}

    public void setCreateAt(String createAt){this.createAt = createAt;}
    public String getCreateAt(){return createAt;}
    public String getFormatedDate(){return parseDate(createAt);}

    public void setEmergency_type(EmergencyType Emergency_type){this.Emergency_type = Emergency_type;}
    public EmergencyType getEmergency_type(){return Emergency_type;}

    public void setPlace_latitude(float place_latitude){this.place_latitude = place_latitude;}
    public float getPlace_latitude(){return place_latitude;}

    public void setPlace_longitude(float place_longitude){this.place_longitude = place_longitude;}
    public float getPlace_longitude(){return place_longitude;}

    public void setPlace_radius(float place_radius){this.place_radius = place_radius;}
    public float getPlace_radius(){return place_radius;}

    public void setDescription(String description){this.description = description;}
    public String getDescription(){return description;}
}

