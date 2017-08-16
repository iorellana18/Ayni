package cl.citiaps.coordinaciondevoluntarios.data;

/**
 * Created by Ian on 09-08-2017.
 */

public class MissionResponse {
    private int mission_id;
    private MissionData Mission;

    public void setMission_id(int mission_id){this.mission_id = mission_id;}
    public int getMission_id(){return mission_id;}

    public void setMission(MissionData Mission){this.Mission = Mission;}
    public MissionData getMission(){return Mission;}
}
