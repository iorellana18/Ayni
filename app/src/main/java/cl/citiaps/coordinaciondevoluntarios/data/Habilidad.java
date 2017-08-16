package cl.citiaps.coordinaciondevoluntarios.data;

/**
 * Created by Joaco on 09-05-17.
 */

public class Habilidad {
    private int id;
    private String name;
    private String description;
    private boolean checked;

    public Habilidad(String name, String description, int id, boolean estado) {
        this.name = name;
        this.description = description;
        this.checked = estado;
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
