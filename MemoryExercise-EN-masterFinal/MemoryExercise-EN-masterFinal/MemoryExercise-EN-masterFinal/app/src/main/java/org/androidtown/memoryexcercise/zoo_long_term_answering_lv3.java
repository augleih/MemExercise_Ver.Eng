package org.androidtown.memoryexcercise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class zoo_long_term_answering_lv3 extends AppCompatActivity {
    String[] suggestAct = {"wearing sunglasses", "wearing glasses", "wearing hat",
            "fed", "taking a nap", "running", "dancing", "sleeping",
            "studying", "fighting each other"};
    String[] suggestAnimal = {"pig", "goat", "sheep", "cow", "horse",
            "polar bear", "lion", "leopard", "fox", " monkey", "kangaroo", "koala",
            "tiger", "rabbit", "penguin"};
    String[] number = {"①", "②", "③", "④"};

    int[] candidate, candBehavior;
    int animal, act, answer, ansCnt = 0;
    boolean ans1, ans2;
    TextView taskText;
    Random random;
    Button[] answerBtn = new Button[6];

    NotificationManager notiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoo_long_term_answering_lv3);

        SharedPreferences settings = getSharedPreferences("myPref", Activity.MODE_PRIVATE);
        animal = settings.getInt("Animal", 0);
        act = settings.getInt("Act", 0);

        notiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notiManager.cancel(zoo_long_term.myNoti);
        answerBtn[0] = (Button)findViewById(R.id.answer1);
        answerBtn[1] = (Button)findViewById(R.id.answer2);
        answerBtn[2] = (Button)findViewById(R.id.answer3);
        answerBtn[3] = (Button)findViewById(R.id.answer4);

        taskText = (TextView)findViewById(R.id.taskText);
        taskText.setText("In zoo,\nAnimal (          ) is,\n(          )");
        taskText.setTextSize(30);
        taskText.setTextColor(Color.BLACK);

        memoryTest();
    }

    void memoryTest(){
        random = new Random();
        candidate = new int[]{0, 0, 0, 0};
        candBehavior = new int[]{0, 0, 0, 0};

        int answer, cand, candBehave, cnt = 0;
        // 먼저 정답이 아닌 4개의 서로 다른(정답과도 다른) 후보(선택지)를 만든다.
        for (int i = 0; i < 4; i++) {
            cand = random.nextInt(15);
            candBehave = random.nextInt(10);

            if(cand == animal && i > 0 || candBehave == act){
                i--;
            }
            else if(cand == animal && i == 0 || candBehave == act){
                i = 0;
            }
            else {
                candidate[i] = cand;
                candBehavior[i] = candBehave;
            }

            for (int j = 0; j < i; j++) {
                if (candidate[i] != candidate[j] && candBehavior[i] != candBehavior[j]){
                    cnt++;
                }
            }
            if(cnt != i) {
                i--;
                cnt = 0;
            }
        }
        // 그리고 4개의 선택지 중 하나의 선택지를 랜덤하게 골라서 정답을 넣는다
        answer = random.nextInt(4);
        candidate[answer] = animal;
        for (int i = 0; i < 4; i++) { // 4개의 선택지 setting.
            answerBtn[i].setText(number[i] + " " + suggestAct[candBehavior[i]] + ",\n     " + suggestAnimal[candidate[i]]);
        }

        for (int i = 0; i < 4; i++) {
            final int idx = i;
            final int aIdx = answer;
            answerBtn[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (idx == aIdx) { // 정답을 선택

                        //게임 종료 알림
                        AlertDialog.Builder builder = new AlertDialog.Builder(zoo_long_term_answering_lv3.this);
                        builder.setCancelable(false);
                        builder.setTitle("Correct!");


                        builder.setPositiveButton("next game",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), zoo_long_term_lv3.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        builder.setNegativeButton("Home",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                        wmlp.y = 550;
                        dialog.show();

                    } else { // 오답을 선택
                        //게임 종료 알림
                        AlertDialog.Builder builder = new AlertDialog.Builder(zoo_long_term_answering_lv3.this);
                        builder.setCancelable(false);
                        builder.setTitle("Incorrect!");
                        //builder.setMessage("틀렸습니다.\n(다음문제로 넘어가시겠습니까?)");
                        builder.setPositiveButton("next game",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), zoo_long_term_lv3.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                        builder.setNegativeButton("Home",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        //finish();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                        wmlp.y = 550;
                        dialog.show();
                    }
                }
            });
        }
    }
}