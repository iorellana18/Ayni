package cl.citiaps.coordinaciondevoluntarios.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by kayjt on 13-09-2016.
 */
public interface ApiInterface {
    String API_URL = "http://158.170.66.37/api/";
    String API_URL2 = "http://158.170.66.37/api/";
    //String API_URL2 = "http://158.170.140.28:3000/";

    @POST("login")
    Call<LoginData> login(@Body LoginData loginData); // Listo

    @GET("emergency/")
    Call<List<EmergencyData>> emergencies(); //Listo

    @GET("mision/invitaciones/")
    Call<List<MissionResponse>> misionList(@Header("Authorization")String token); // Listo

    @GET("mission/{missionID}")
    Call<MissionData> getMissionData(@Path("missionID") int missionID); // Listo

    @GET("ability/")
    Call<List<Abilities>> getAbilities(); // Listo

    @GET("usuario/informacion/")
    Call<UserData> getUserLoged(@Header("Authorization")String token); // Listo

    @GET("mision/activa/")
    Call<List<MissionResponse>> getActiveMission(@Header("Authorization")String token); // Listo

    @GET("user/{userID}")
    Call<UserData> getUser(@Path("userID") int userID); // Listo

    @GET("problem/{problemId}")
    Call<ProblemData> getProblem(@Path("problemId") int problemId); // Listo

    @GET("auth/refresh_token")
    Call<LoginData> getToken(@Header("Authorization")String token); // Listo

    @POST("user/apptoken")
    Call<AppTokenData> updateToken(@Body AppTokenData appTokenData); // Listo

    @POST("user/")
    Call<RegisterData> postRegister(@Body RegisterData registerData);

    @POST("volunteer/")
    Call<RegistroHabilidad> postAbilities(@Body RegistroHabilidad abilities);
}
