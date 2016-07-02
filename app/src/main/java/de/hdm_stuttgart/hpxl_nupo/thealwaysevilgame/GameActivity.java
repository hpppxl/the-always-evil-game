package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.feedback.FeedbackAgent;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.feedback.FeedbackMessage;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.Game;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.InventoryItem;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.PlaceManager;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.LancasterStemmer;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.StopWordFilter;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.Tokenizer;

public class GameActivity extends AppCompatActivity implements
        RecognitionListener, MediaPlayer.OnCompletionListener {

    private static final String LOG_TAG = GameActivity.class.getSimpleName();
    private static final int INITIAL_QUEUE_CAPACITY = 10;
    private static final int PERMISSION_REQUEST_CODE = 0x01;
    public static final String EXTRA_START_LOCATION = "start_location";
    public static final String EXTRA_LOCATION_CLEARING = "clearing";
    public static final String EXTRA_LOCATION_VILLAGE = "village";

    private TextView returnedText;
    private ImageButton speakButton;
    private boolean performingSpeechSetup = false;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private Game mGame = new Game();
    private Queue<String> mPlaybackQueue = new ArrayBlockingQueue<>(INITIAL_QUEUE_CAPACITY);
    private FeedbackAgent mFeedbackAgent = new FeedbackAgent();
    private boolean mGameShouldEnd = false;
    private Switch mAutoListen;
    private ImageButton mListenBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        returnedText = (TextView) findViewById(R.id.textView1);
        speakButton = (ImageButton) findViewById(R.id.speakButton);
        mAutoListen = (Switch)findViewById(R.id.sw_auto_listen);
        mListenBtn = (ImageButton) findViewById(R.id.speakButton);

        mListenBtn.setEnabled(false);
        mListenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen();
            }
        });

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en-US");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                "en-US");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 4000);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 4000);

        mMediaPlayer.setOnCompletionListener(this);

        //checking for record audio permission
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if(permissionStatus != PackageManager.PERMISSION_GRANTED){
            Log.i(LOG_TAG, "permission for record audio denied initially");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        }else{
            //we already have the permission
            startGame();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startGame();
            }else{
                //too bad, inform the user
                mGameShouldEnd = true;
                playMediaFile("globalSounds/no_mic_access.ogg");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
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

        FeedbackMessage feedback = new FeedbackMessage(getApplicationContext());
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        feedback.setRecognizedSentences(matches);

        String allTexts = "";
        for (String text : matches) {
            allTexts += text + " ";
        }

        List<String> list = LancasterStemmer.stemAll(StopWordFilter.filter(Tokenizer.tokenize(allTexts)));
        feedback.setStemmedWords(list);

        if (list.isEmpty()) {
            playRandomWhatSound();
            mFeedbackAgent.send(feedback);
            return;
        }

        feedback.setLocationBefore(mGame.getPlaceManager().getLocationName());
        feedback.setInventoryBefore(mGame.getInventoryManager().getItemList());

        List<String> nextSoundFiles = mGame.parseSpeechInput(list);
        feedback.setPlaybackQueue(nextSoundFiles);

        feedback.setLocationAfter(mGame.getPlaceManager().getLocationName());
        feedback.setmInventoryAfter(mGame.getInventoryManager().getItemList());

        mFeedbackAgent.send(feedback);


        mPlaybackQueue.addAll(nextSoundFiles);
        flushPlaybackQueue();
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    private void playMediaFile(String fileName) {
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
        mMediaPlayer.reset();
        if (!flushPlaybackQueue()) {
            if(mGameShouldEnd){
                //the game has ended due to a user action
                this.finish();
            }else {
                if(mAutoListen.isChecked()) {
                    listen();
                }else{
                    mListenBtn.setEnabled(true);
                }
            }
        }
    }

    public void listen(){
        mListenBtn.setEnabled(false);
        performingSpeechSetup = true;
        speech.startListening(recognizerIntent);
        enableSpeakButton();
    }

    public void enableSpeakButton() {
        speakButton.setBackgroundResource(R.drawable.mic_background_active);
    }

    public void disableSpeakButton() {
        speakButton.setBackgroundResource(R.drawable.mic_background_inactive);
    }

    public boolean flushPlaybackQueue() {
        try {
            String soundName = mPlaybackQueue.poll();
            if (soundName != null) {
                AssetFileDescriptor fileDescriptor = this.getAssets().openFd(soundName);
                try {
                    mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                    fileDescriptor.close();
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                }catch (IllegalStateException e){
                    Log.e(LOG_TAG, "illegal state in media player", e);
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void playRandomWhatSound() {
        playMediaFile("globalSounds/what_0" + (int) ((Math.random() * 8)) + ".ogg");
    }

    public void startGame(){
        String startLocation = getIntent().getExtras().getString(EXTRA_START_LOCATION);

        if(startLocation.equals(EXTRA_LOCATION_VILLAGE)) {
            mGame.getInventoryManager().add(InventoryItem.GOLDEN_COIN);
            mGame.getPlaceManager().setLocation(PlaceManager.PlaceIdentifier.ENTRANCE);
            playMediaFile("entrance/entrance_00.ogg");
        }else {
            playMediaFile("clearing/clearing_00.ogg");
        }
    }
}
