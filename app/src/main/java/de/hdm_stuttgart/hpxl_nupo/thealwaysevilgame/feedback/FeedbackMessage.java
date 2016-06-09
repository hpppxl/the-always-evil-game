package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.feedback;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by daniel on 09/06/16.
 */
public class FeedbackMessage {
    // region Constants
    private static final String CLASS_TAG = FeedbackMessage.class.getSimpleName();
    // endregion

    // region Properties & Members
    private String mDeviceId;
    private Context mContext;
    private List<String> mRecognizedSentences;
    private List<String> mStemmedWords;
    private List<String> mPlaybackQueue;

    private String mLocationBefore;
    private String mLocationAfter;
    private List<String> mInventoryBefore;
    private List<String> mInventoryAfter;
    // endregion

    // region Constructors
    public FeedbackMessage(Context context) {
        this.mContext = context;
        this.mDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    // endregion

    // region Methods
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("locationBefore", this.mLocationBefore);
            jsonObject.accumulate("locationAfter", this.mLocationAfter);
            jsonObject.accumulate("recognizedSentences", this.mRecognizedSentences);
            jsonObject.accumulate("stemmedWords", this.mStemmedWords);
            jsonObject.accumulate("playbackQueueGenerated", this.mPlaybackQueue);
            jsonObject.accumulate("inventoryBefore", this.mInventoryBefore);
            jsonObject.accumulate("inventoryAfter", this.mInventoryAfter);
        } catch (JSONException e) {
            Log.e(CLASS_TAG, "could not create Json object", e);
        }
        return jsonObject;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public Context getContext() {
        return mContext;
    }

    public void setmInventoryAfter(List<String> mInventoryAfter) {
        this.mInventoryAfter = mInventoryAfter;
    }

    public void setRecognizedSentences(List<String> mRecognizedWords) {
        this.mRecognizedSentences = mRecognizedWords;
    }

    public void setStemmedWords(List<String> mStemmedWords) {
        this.mStemmedWords = mStemmedWords;
    }

    public void setPlaybackQueue(List<String> mPlaybackQueue) {
        this.mPlaybackQueue = mPlaybackQueue;
    }

    public void setLocationBefore(String mLocationBefore) {
        this.mLocationBefore = mLocationBefore;
    }

    public void setLocationAfter(String mLocationAfter) {
        this.mLocationAfter = mLocationAfter;
    }

    public void setInventoryBefore(List<String> mInventoryBefore) {
        this.mInventoryBefore = mInventoryBefore;
    }
    // endregion
    // region Inner Classes / Interfaces
    // endregion
}
