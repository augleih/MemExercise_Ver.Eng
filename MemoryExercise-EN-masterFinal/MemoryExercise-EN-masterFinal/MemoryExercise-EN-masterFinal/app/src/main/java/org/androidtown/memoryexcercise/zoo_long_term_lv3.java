package org.androidtown.memoryexcercise;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class zoo_long_term_lv3 extends AppCompatActivity {
    String[] suggestAct = {"wearing sunglasses", "wearing glasses", "wearing hat",
            "fed", "taking a nap", "running", "dancing", "sleeping",
            "studying", "fighting each other"};
    String[] suggestAnimal = {"pig", "goat", "sheep", "cow", "horse",
            "polar bear", "lion", "leopard", "fox", " monkey", "kangaroo", "koala",
            "tiger", "rabbit", "penguin"};


    NotificationManager mNotiManager;
    TextView taskText, suggestText, timeText;
    Button startBtn, startBtn2;
    Random random;
    CountDownTimer timer;
    ImageView backAnimal;// 11/14 추가
    ImageView backAnimal2;
    ArrayList<Integer> backAnimalList = new ArrayList<Integer>(5);//11/14

    int timerVal;
    int act, animal;
    //private static final String TAG = "zoo_long_term";
    final static int myNoti = 0;

    private static final long START_TIME_IN_MILLIS = 60000;
    private static final long START_TIME_IN_MILLIS2 = 180000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis, mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoo_long_term_lv3);

        mNotiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        SharedPreferences settings = getSharedPreferences("myPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        random = new Random();
        backAnimalList.add(R.drawable.longlion);//11/14 추가
        backAnimalList.add(R.drawable.longmonkey);
        backAnimalList.add(R.drawable.longcoa);
        backAnimalList.add(R.drawable.longcat);
        backAnimalList.add(R.drawable.longdog);

        taskText = (TextView)findViewById(R.id.taskText);
        timeText = (TextView)findViewById(R.id.timeText);
        suggestText = (TextView)findViewById(R.id.suggestQuest);
        startBtn = (Button)findViewById(R.id.startBtn);
        startBtn2 = (Button)findViewById(R.id.startBtn2);

        timeText.setVisibility(GONE);
        act = random.nextInt(10);

        animal = random.nextInt(15);
        backAnimal=(ImageView)findViewById(R.id.backAnimal); // 11/14 추가

        backAnimal.setImageResource(backAnimalList.get(animal%5));//11/14



        taskText.setText("In zoo,\nAnimal " + suggestAct[act] +
                " is,\n(" + suggestAnimal[animal] + ").");
        taskText.setTextSize(30);
        taskText.setTextColor(Color.BLACK);

        editor.putInt("Act", act);
        editor.putInt("Animal", animal);
        editor.commit();

        startBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                suggestText.setVisibility(GONE);
                taskText.setVisibility(GONE);
                startBtn.setVisibility(GONE);
                backAnimal.setVisibility(GONE);//11/14 추가-1번버튼
                startBtn2.setVisibility(GONE);
                timeText.setVisibility(VISIBLE);
                handler.sendEmptyMessageDelayed(0, 60000);
                startTimer();
            }
        });

        startBtn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                suggestText.setVisibility(GONE);
                taskText.setVisibility(GONE);
                startBtn.setVisibility(GONE);
                backAnimal.setVisibility(GONE);//11/14 추가-1번버튼
                startBtn2.setVisibility(GONE);
                timeText.setVisibility(VISIBLE);
                handler.sendEmptyMessageDelayed(0, 180000);
                startTimer2();
            }
        });
    }

    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        updateButtons();

        System.out.println("onStart,, " + mTimerRunning + ", " + mTimeLeftInMillis);
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();
            }
        }
    }

    private void startTimer() {
        if(mTimerRunning == false)
            mTimeLeftInMillis = START_TIME_IN_MILLIS;

        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        System.out.println("call startTimer,, " + mTimerRunning + ", " + mTimeLeftInMillis);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                System.out.println("onTick,, " + mTimerRunning + ", " + mTimeLeftInMillis);
            }
            @Override
            public void onFinish() {
                System.out.println("onFinish,,1 " + mTimerRunning + ", " + mTimeLeftInMillis);
                Intent intent = new Intent(getApplicationContext(), zoo_long_term_answering_lv3.class);
                startActivity(intent);
                mTimerRunning = false;
                updateButtons();
                System.out.println("onFinish2,, " + mTimerRunning + ", " + mTimeLeftInMillis);
            }
        }.start();
        mTimerRunning = true;
        updateButtons();
    }

    private void startTimer2() {
        if(mTimerRunning == false)
            mTimeLeftInMillis = START_TIME_IN_MILLIS2;

        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        System.out.println("call startTimer,, " + mTimerRunning + ", " + mTimeLeftInMillis);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                System.out.println("onTick,, " + mTimerRunning + ", " + mTimeLeftInMillis);
            }
            @Override
            public void onFinish() {
                System.out.println("onFinish,,1 " + mTimerRunning + ", " + mTimeLeftInMillis);
                Intent intent = new Intent(getApplicationContext(), zoo_long_term_answering_lv3.class);
                startActivity(intent);
                mTimerRunning = false;
                updateButtons();
                System.out.println("onFinish2,, " + mTimerRunning + ", " + mTimeLeftInMillis);
            }
        }.start();
        mTimerRunning = true;
        updateButtons();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000 / 3600) % 24;
        int minutes = (int) (mTimeLeftInMillis / 1000 / 60) % 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        timeText.setText("Question starts in\n" + timeLeftFormatted);
    }

    private void updateButtons(){
        if(mTimerRunning){
            suggestText.setVisibility(GONE);
            taskText.setVisibility(GONE);
            startBtn.setVisibility(GONE);
            backAnimal.setVisibility(GONE);//11/14 추가
            startBtn2.setVisibility(GONE);
            timeText.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();

        System.out.println("onStop,, " + mTimerRunning + ", " + mTimeLeftInMillis);
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            //notification 객체 생성(상단바에 보여질 아이콘, 메세지, 도착시간 정의)
            Notification noti = new Notification(R.drawable.logo//알림창에 띄울 아이콘
                    , "Time to check your memory", //간단 메세지
                    System.currentTimeMillis()); //도착 시간

            //기본으로 지정된 소리를 내기 위해
            noti.defaults = Notification.DEFAULT_SOUND;

            //알림 소리를 한번만 내도록
            noti.flags = Notification.FLAG_ONLY_ALERT_ONCE;

            //확인하면 자동으로 알림이 제거 되도록
            noti.flags = Notification.FLAG_AUTO_CANCEL;

            //사용자가 알람을 확인하고 클릭했을때 새로운 액티비티를 시작할 인텐트 객체
            Intent intent = new Intent(zoo_long_term_lv3.this, zoo_long_term_answering_lv3.class);

            //새로운 태스크(Task) 상에서 실행되도록(보통은 태스크1에 쌓이지만 태스크2를 만들어서 전혀 다른 실행으로 관리한다)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //인텐트 객체를 포장해서 전달할 인텐트 전달자 객체
            PendingIntent pIndent = PendingIntent.getActivity(zoo_long_term_lv3.this, 0, intent, 0);

            //상단바를 드래그 했을때 보여질 내용 정의하기
            Notification.Builder builder = new Notification.Builder(zoo_long_term_lv3.this)
                    .setContentIntent(pIndent)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("Time to check your memory")
                    .setContentText("What is the animal suggested yesterday?")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
            noti = builder.build();
            mNotiManager.notify(myNoti, noti);
        }
    };

    public void onBackPressed() {
        if(!mTimerRunning)
            dialogShow();
        else{
            zoo_long_term_lv3.super.onBackPressed();
        }
    }
    protected void onResume(){
        super.onResume();
        if(mCountDownTimer != null && mTimeLeftInMillis == 0)
            mCountDownTimer.onFinish();
    }

    public void dialogShow() {
        //게임 종료 알림
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("End game");
        builder.setCancelable(false);
        builder.setMessage("Would you like to end the game?\n(* Game data will be removed if you restart)");
        builder.setPositiveButton("next game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), zoo_long_term_lv3.class);
                        startActivity(intent);
                        finish();
                    }
                });
        builder.setNegativeButton("Home",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        zoo_long_term_lv3.super.onBackPressed();
                        finish();
                    }
                });
        builder.show();
    }
}