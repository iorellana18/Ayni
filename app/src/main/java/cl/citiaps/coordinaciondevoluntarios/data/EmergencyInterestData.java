package cl.citiaps.coordinaciondevoluntarios.data;

/**
 * Created by kayjt on 18-10-2016.
 */

public class EmergencyInterestData {

    private boolean interest;
    private int emergency_id;
    private int volunteer_id;
    private int user_id;
    private boolean error;


    public EmergencyInterestData(boolean interest, int emergency_id, int volunteer_id, int user_id) {
        this.interest = interest;
        this.emergency_id = emergency_id;
        this.volunteer_id = volunteer_id;
        this.user_id = user_id;
    }

    public EmergencyInterestData() {

    }

    public boolean isInterest() {
        return interest;
    }

    public void setInterest(boolean interest) {
        this.interest = interest;
    }

    public int getEmergency_id() {
        return emergency_id;
    }

    public void setEmergency_id(int emergency_id) {
        this.emergency_id = emergency_id;
    }

    public int getVolunteer_id() {
        return volunteer_id;
    }

    public void setVolunteer_id(int volunteer_id) {
        this.volunteer_id = volunteer_id;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
