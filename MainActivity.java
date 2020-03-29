package com.example.ttos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
   private TextToSpeech textToSpeech;
   private EditText  ed1;
   private SeekBar sPitch,sSpeed;
   private Button speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status==TextToSpeech.SUCCESS){
                    int result=textToSpeech.setLanguage(Locale.ENGLISH);

                    if (result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language not supported");
                    }else{
                        speak.setEnabled(true);
                    }
                }else{
                    Log.e("TTS","Initialisation failed");
                }
            }
        });

        ed1 = findViewById(R.id.ed1);
        sPitch=findViewById(R.id.sPitch);
        sSpeed=findViewById(R.id.sSpeed);
        speak=findViewById(R.id.speak);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }
    private void speak(){
        String text=ed1.getText().toString();
        float pitch=(float) sPitch.getProgress() / 50;
        if (pitch<0.1) pitch=0.1f;
        float speed=(float) sSpeed.getProgress() / 50;
        if (speed<0.1) speed=0.1f;

        textToSpeech.setPitch(pitch);
        textToSpeech.setSpeechRate(speed);
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onDestroy() {

        if(textToSpeech==null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();

    }
}