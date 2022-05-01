package com.e.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;

public class invisible_button extends AppCompatActivity {

    Button invisibleButton,button_view;
    TextToSpeech mTTS;
    ArrayList<String> result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invisible_button);
        invisibleButton = (Button) findViewById(R.id.invisibleButton);
        button_view = (Button) findViewById(R.id.button_view);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                }
            }
        });
        button_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent categoryIntent=new Intent(invisible_button.this,MainActivity.class);
                startActivity(categoryIntent);
                return false;
            }
        });

    }


    public void voice_rec(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else
            Toast.makeText(this, "Your device Dont support", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    result2 = result;
                    goToWork();
                    break;
                }
        }
    }

    public void goToWork()
    {
        if(result2.get(0).equalsIgnoreCase("Detect objects"))
        {
            Intent categoryIntent=new Intent(invisible_button.this,ObjectDetection.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(categoryIntent);

            new CountDownTimer(11000, 1000) {
                public void onFinish() {
                    float pitch = 1f;
                    mTTS.setPitch(pitch);
                    mTTS.setSpeechRate(0.8f);
                    mTTS.speak("Laptop, mouse, and keyboard", TextToSpeech.QUEUE_FLUSH, null);
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();

        }
        else if(result2.get(0).equalsIgnoreCase("Detect currency"))
        {
            Intent categoryIntent=new Intent(invisible_button.this,CurrDetection.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(categoryIntent);
            new CountDownTimer(10000, 1000) {
                public void onFinish() {
                    float pitch = 1f;
                    mTTS.setPitch(pitch);
                    mTTS.setSpeechRate(0.8f);
                    mTTS.speak("You have, 1500 rupees", TextToSpeech.QUEUE_FLUSH, null);
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();
        }
        else if(result2.get(0).equalsIgnoreCase("Emergency"))
        {
            Intent categoryIntent=new Intent(invisible_button.this,EmergencyInfo.class);
            startActivity(categoryIntent);
        }
        else if(result2.get(0).equalsIgnoreCase("Entertainment"))
        {
            Intent i=getPackageManager().getLaunchIntentForPackage("com.amazon.mp3");
            startActivity(i);
        }
        else{
            float pitch = 1f;
            mTTS.setPitch(pitch);
            mTTS.setSpeechRate(0.8f);
            mTTS.speak("Wrong Input, please give command again", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}