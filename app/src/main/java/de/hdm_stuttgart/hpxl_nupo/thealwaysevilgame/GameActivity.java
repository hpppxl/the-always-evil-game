package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.Game;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.LancasterStemmer;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.StopWordFilter;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.Tokenizer;

public class GameActivity extends AppCompatActivity implements
        RecognitionListener, MediaPlayer.OnCompletionListener{

    private static final String LOG_TAG = GameActivity.class.getSimpleName();
    private static final int INITIAL_QUEUE_CAPACITY = 10;
    private TextView returnedText;
    private ImageButton speakButton;
    private boolean performingSpeechSetup = false;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private Game mGame = new Game();
    private Queue<String> mPlaybackQueue = new ArrayBlockingQueue<>(INITIAL_QUEUE_CAPACITY);



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        returnedText = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        speakButton = (ImageButton) findViewById(R.id.speakButton);

        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 4000);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 4000);


        mMediaPlayer.setOnCompletionListener(this);

        playMediaFile("village.ogg");
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        disableSpeakButton();
    }

    @Override
    public void onError(int errorCode) {
        if (performingSpeechSetup && errorCode == SpeechRecognizer.ERROR_NO_MATCH) return;
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        returnedText.setText(errorMessage);
        disableSpeakButton();
        playRandomWhatSound();
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
     }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
        performingSpeechSetup = false;
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = matches.get(0);

        List<String> list = LancasterStemmer.stemAll(StopWordFilter.filter(Tokenizer.tokenize(text)));
        List<String> nextSoundFiles = mGame.parseSpeechInput(list);

        if(nextSoundFiles == null || nextSoundFiles.size() == 0){
            playRandomWhatSound();
        }else {
            mPlaybackQueue.addAll(nextSoundFiles);
            flushPlaybackQueue();
        }
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    private void playMediaFile(String fileName){
        mPlaybackQueue.add(fileName);
        flushPlaybackQueue();
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(!flushPlaybackQueue()){
            mMediaPlayer.reset();
            performingSpeechSetup = true;
            speech.startListening(recognizerIntent);
            enableSpeakButton();
        }
    }

    public void enableSpeakButton(){
        speakButton.setBackgroundResource(R.drawable.mic_background_active);
    }
    public void disableSpeakButton(){
        speakButton.setBackgroundResource(R.drawable.mic_background_inactive);
    }

    public boolean flushPlaybackQueue(){
        try {
            String soundName = mPlaybackQueue.poll();
            if(soundName != null) {
                AssetFileDescriptor fileDescriptor = this.getAssets().openFd(soundName);
                mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                fileDescriptor.close();
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void playRandomWhatSound(){
        mPlaybackQueue.add("globalSounds/what" + (int)((Math.random()*4)+1) + ".ogg");
        flushPlaybackQueue();
    }
}
