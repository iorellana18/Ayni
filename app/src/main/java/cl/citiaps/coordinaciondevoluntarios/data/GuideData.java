package cl.citiaps.coordinaciondevoluntarios.data;

/**
 * Created by diego on 30-04-17.
 */

public class GuideData {

    public  GuideData(String file_name){
        setFile_name(file_name);
    }
    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    private String file_name;
}
