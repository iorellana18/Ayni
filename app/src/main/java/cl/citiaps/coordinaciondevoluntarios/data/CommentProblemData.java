package cl.citiaps.coordinaciondevoluntarios.data;

/**
 * Created by Joaco on 01-06-17.
 */

public class CommentProblemData {
    private int id;
    private int user_id;
    private int problem_id;
    private String date;
    private String user_firstname;
    private String user_lastname;
    private String description;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNombre_voluntario() {
        return user_firstname+" "+user_lastname;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String text) {
        this.description = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }
}
