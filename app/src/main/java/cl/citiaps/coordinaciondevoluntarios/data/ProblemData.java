package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by diego on 11-04-17.
 */

@SuppressWarnings("serial")
public class ProblemData implements Serializable{

    private int Id;
    private String createAt;
    private String title;
    private int status;
    private String assertiveness_text;
    private int mission_id;
    private int user_id;
    private String description;
    private List<Answer> Answer;


    private String parseDate(String oldDate){
        String[] parser = oldDate.split("T");
        return parser[0];
    }

    public void setId(int Id){this.Id = Id;}
    public int getId(){return Id;}

    public void setCreateAt(String createAt){this.createAt = createAt;}
    public String getCreateAt(){return createAt;}

    public void setTitle(String title){this.title = title;}
    public String getTitle(){return title;}

    public void setStatus(int status){this.status = status;}
    public int getStatus(){return status;}

    public void setAssertiveness_text(String assertiveness_text){this.assertiveness_text = assertiveness_text;}
    public String getAssertiveness_text(){return assertiveness_text;}

    public void setMission_id(int mission_id){this.mission_id = mission_id;}
    public int getMission_id(){return mission_id;}

    public void setUser_id(int user_id){this.user_id = user_id;}
    public int getUser_id(){return user_id;}

    public void setDescription(String description){this.description = description;}
    public String getDescription(){return description;}

    public void setAnswer(List<Answer> Answer){this.Answer = Answer;}
    public List<Answer> getAnswer(){return Answer;}
}
