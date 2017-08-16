package cl.citiaps.coordinaciondevoluntarios.data;

import java.io.Serializable;

/**
 * Created by Ian on 31-07-2017.
 */

@SuppressWarnings("serial")
public class Abilities implements Serializable{
    private int Id;
    private String ability;
    private boolean checked;

    public Abilities(int Id, String ability, boolean checked){
        setId(Id);
        setAbility(ability);
        setChecked(checked);
    }

    public void setId(int Id){this.Id = Id;}
    public int getId(){return Id;}

    public void setAbility(String ability){this.ability = ability;}
    public String getAbility(){return ability;}

    public void setChecked(boolean checked){this.checked = checked;}
    public boolean getChecked(){return checked;}
}
