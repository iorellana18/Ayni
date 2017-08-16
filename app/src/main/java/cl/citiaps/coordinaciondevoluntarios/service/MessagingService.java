package cl.citiaps.coordinaciondevoluntarios.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Map;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.activity.IndexNoEmergencyActivity;
import cl.citiaps.coordinaciondevoluntarios.activity.LoginActivity;

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = "MessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        Log.d(TAG, "***  onMessageReceived() ***" );
        //create notification
        Map<String, String> map = remoteMessage.getData();
        String messageType="";
        String eventId="";
        String title="";
        String body="";
        for(String key:map.keySet()){
            Log.d(TAG, key + " - " + map.get(key));
            String value = map.get(key);
            if (key.equals("type")){
                messageType = map.get(key);
            }else if (key.equals("id")){
                eventId = map.get(key);
            }else if(key.equals("title")){
                title = map.get(key);
            }else if(key.equals("body")){
                body = map.get(key);
            }
        }
        Log.d(TAG, "messageType: " + messageType );


        //validar si hay usuario conectado
        //Para verificar si se inicio sesion revisar que existan estas preferencias guardadas
        SharedPreferences prefs = getSharedPreferences(getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE);

        int missionNmb = prefs.getInt(getString(R.string.mission_number_preference_key), 0);
        int emergencyNmb = prefs.getInt(getString(R.string.emergencies_number_preference_key),0);
        int idPreferenceKey = prefs.getInt(getString(R.string.volunteer_id_preference_key), -1);
        Log.d(TAG, "Datos antes --> Misiones: " + String.valueOf(missionNmb) + " Emergencias: " + String.valueOf(emergencyNmb) );
        SharedPreferences.Editor editor = prefs.edit();

        //aumenta número de notificaciones sin leer
        if(messageType.equals("emergency")){
            emergencyNmb = emergencyNmb +1;
            editor.putInt(getString(R.string.emergencies_number_preference_key), emergencyNmb);
            Log.d(TAG, "emergencia guardada");
        } else if (messageType.equals("mission")) {
            missionNmb = missionNmb + 1;
            editor.putInt(getString(R.string.mission_number_preference_key), missionNmb);
            Log.d(TAG, "mision guardada");
        }
        editor.commit();

        missionNmb = prefs.getInt(getString(R.string.mission_number_preference_key), 0);
        emergencyNmb = prefs.getInt(getString(R.string.emergencies_number_preference_key),0);
        Log.d(TAG, "Datos guardados --> Misiones: " + String.valueOf(missionNmb) + " Emergencias: " + String.valueOf(emergencyNmb) );


        //si hay usuario conectado
        if (idPreferenceKey != -1){
            //Se aumenta cantidad de emergencias o misiones
            //recibe emergencia
            if(messageType.equals("emergency")){
                openEmergency(eventId);
            } else if (messageType.equals("mission")) {
                openMission(eventId);
            }

        }


        createNotification(title, body);

    }

    private void openEmergency(String emergencyId){

    }

    private void openMission(String missionId){

    }

    private void createNotification(String title, String body){
        Intent intent = new Intent( this , LoginActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String messageBody = body;
        String messageTitle = title;
        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.drawable.ic_ico_voluntarios)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setVibrate(new long[] {1000,1000,1000})
                .setContentIntent(resultIntent)
                ;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
}
