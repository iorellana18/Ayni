package cl.citiaps.coordinaciondevoluntarios.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Joaco on 12-04-17.
 */

public interface otherInterface {
    public static final String API_URL = "http://192.168.100.109:3000/";

    @POST("crear_usuario")
    Call<LoginData> postRegister(@Body LoginData loginData);
}
