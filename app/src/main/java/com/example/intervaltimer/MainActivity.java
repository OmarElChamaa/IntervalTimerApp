package com.example.intervaltimer;

import androidx.appcompat.app.AppCompatActivity;


import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private long roundTimeSaver;
    public long roundTimeMs;
    public int repsLeft;


    public int minutesToSec(EditText min){
        String value =min.getText().toString();
        return Integer.parseInt(value)*60;
    }

    public int edtTxtToInt(EditText value){
        String values =value.getText().toString();
        return Integer.parseInt(values);
    }

    public String timeToString(long ms){

        long minutes = (ms / 1000) / 60;
        long seconds = (ms / 1000) % 60;
        String result=(minutes+":"+seconds);
        return result;
    }

    //Setter
    public void setTime(long time) {
        this.roundTimeSaver= time;
    }
    //Getter
    public long getTime() {
        return roundTimeSaver;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button startBtn=findViewById(R.id.fightBtn);
        startBtn.setOnClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }



    @Override
    public void onClick (View view){
        final EditText repNbr= findViewById(R.id.reps);
        repNbr.getText().toString();
        final EditText rndLgtMin=findViewById(R.id.edtTxtMinsRnd);
        rndLgtMin.getText().toString();
        final EditText rndLgtSec=findViewById(R.id.edtTxtSecRds);
        rndLgtSec.getText().toString();
        final EditText pauseMin=findViewById(R.id.edtTxtPauseMin);
        pauseMin.getText().toString();
        final EditText pauseSec=findViewById(R.id.edtTextPauseSec);
        pauseSec.getText().toString();

        final TextView pauseScrn=findViewById(R.id.pauseScreen);

        final CountDownTimer[] fightTimer = new CountDownTimer[1];


        View layout2 = findViewById(R.id.layout2);
        TextView roundTime = findViewById(R.id.fightTime);


        int seconds = minutesToSec(rndLgtMin)+edtTxtToInt(rndLgtSec);
        roundTimeMs=seconds*1000;
        setTime(roundTimeMs);

        long pauseTimeMs=edtTxtToInt(pauseMin)*60000+edtTxtToInt(pauseSec)*1000;

        //int repsLeft=edtTxtToInt(repNbr);

        final MediaPlayer mp = MediaPlayer.create(this,R.raw.beep);
        final MediaPlayer startBell= MediaPlayer.create(this,R.raw.start);

        switch (view.getId()) {
            case  R.id.fightBtn:

                Button start = findViewById(R.id.fightBtn);
                start.setClickable(false);

                View layout = findViewById(R.id.LayoutToBlur);
                layout.setAlpha(0.3f);

                TextView mTextField=(TextView) findViewById(R.id.countdownTimer);
                mTextField.setVisibility(mTextField.VISIBLE);

                repsLeft=edtTxtToInt(repNbr);


                final CountDownTimer timerToStart=new CountDownTimer(6000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        mTextField.setText(""+(millisUntilFinished / 1000));
                        mp.start();
                    }

                    public void onFinish() {
                        mTextField.setVisibility(mTextField.INVISIBLE);
                        startBell.start();
                        layout2.setVisibility(View.VISIBLE);



                            System.out.println(repsLeft);
                            fightTimer[0] =new CountDownTimer(roundTimeMs,1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    roundTime.setVisibility(roundTime.VISIBLE);
                                    pauseScrn.setVisibility(pauseScrn.INVISIBLE);
                                    roundTime.setText(timeToString(millisUntilFinished));

                                }

                                @Override
                                public void onFinish() {
                                    roundTime.setVisibility(roundTime.INVISIBLE);
                                    startBell.start();
                                    if (repsLeft!=0){
                                        final CountDownTimer pauseTimer = new CountDownTimer(pauseTimeMs, 1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                pauseScrn.setVisibility(pauseScrn.VISIBLE);
                                                String timeLeft = timeToString(millisUntilFinished);
                                                pauseScrn.setText("PAUSE TIME: \n" + timeLeft + "\n REPS LEFT: \n" + repsLeft);
                                            }

                                            @Override
                                            public void onFinish() {
                                                pauseScrn.setVisibility(pauseScrn.INVISIBLE);
                                                roundTime.setVisibility(roundTime.VISIBLE);
                                                if(repsLeft>0){
                                                    repsLeft--;
                                                    startBell.start();
                                                    fightTimer[0].start();
                                                }

                                            }

                                        }.start();
                                    }
                                }
                            }.start();


                    }
                }.start();


                layout2.setVisibility(View.INVISIBLE);
                roundTime.setVisibility(View.VISIBLE);
                start.setClickable(true);


                break;
            default:
                break;
        }

    }



}