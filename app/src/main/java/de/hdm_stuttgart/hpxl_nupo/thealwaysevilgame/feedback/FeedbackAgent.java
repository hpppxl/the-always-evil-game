package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.feedback;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by daniel on 09/06/16.
 */
public class FeedbackAgent {

    // region Constants
    private static final String CLASS_TAG = FeedbackAgent.class.getSimpleName();
    // endregion
    // region Properties & Members
    // endregion
    // region Constructors
    // endregion
    // region Methods
    public void send(FeedbackMessage message){
        if(isConnected(message.getContext())) {
            MessageSender sender = new MessageSender();
            String mes = message.toJson().toString();
            sender.execute(message);
        }else{
            Log.d(CLASS_TAG, "Could not send Debug connection... No Network connection");
        }
    }

    public boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }
    // endregion
    // region Inner Classes / Interfaces
    private class MessageSender extends AsyncTask<FeedbackMessage, Integer, Integer>{

        public static final String BACKEND_URL = "http://46.101.225.184:8090/feedbackMessage/";

        @Override
        protected Integer doInBackground(FeedbackMessage... params) {
            Integer result = 0;
            for(FeedbackMessage message : params) {
                URL url = null;

                try {
                    //create the url specific for the device
                    url = new URL(BACKEND_URL + message.getDeviceId());
                }catch(MalformedURLException e){
                    Log.e(CLASS_TAG, "Cannot create URL", e);
                    return null;
                }
                HttpURLConnection connection = null;
                Integer response = HttpURLConnection.HTTP_INTERNAL_ERROR;
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);
                    connection.setChunkedStreamingMode(0);

                    OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                    String payload = message.toJson().toString();
                    out.write(payload.getBytes());
                    out.flush();

                    response = connection.getResponseCode();
                }catch (IOException e){
                    Log.e(CLASS_TAG, "Cannot send Feedback", e);
                    return null;
                }finally {
                    if(connection != null) {
                        connection.disconnect();
                    }
                }

                //save the highest code as response
                //since http error codes are bigger than success codes
                if(response > result){
                    result = response;
                }
            }

            return result;
        }
    }

    // endregion

}
