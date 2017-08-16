package cl.citiaps.coordinaciondevoluntarios.data;

import java.util.Date;

/**
 * Created by kayjt on 19-10-2016.
 */

public class MissionRecruitData {

    private int mission_id;
    private int volunteer_id;
    private int user_id;
    private boolean answer; // accepted_status
    private int recruiting_status;
    private Date start_date;
    private Date end_date;
    private boolean error;

    public MissionRecruitData(int mission_id, int volunteer_id, int user_id, boolean answer, int recruiting_status, Date start_date, Date end_date) {
        this.mission_id = mission_id;
        this.volunteer_id = volunteer_id;
        this.user_id = user_id;
        this.answer = answer;
        this.recruiting_status = recruiting_status;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public MissionRecruitData(int mission_id, int volunteer_id, boolean answer) {
        this.mission_id = mission_id;
        this.volunteer_id = volunteer_id;
        this.answer = answer;
    }

    public MissionRecruitData() {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public int getRecruiting_status() {
        return recruiting_status;
    }

    public void setRecruiting_status(int recruiting_state) {
        this.recruiting_status = recruiting_state;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getMission_id() {
        return mission_id;
    }

    public void setMission_id(int mission_id) {
        this.mission_id = mission_id;
    }

    public int getVolunteer_id() {
        return volunteer_id;
    }

    public void setVolunteer_id(int volunteer_id) {
        this.volunteer_id = volunteer_id;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
