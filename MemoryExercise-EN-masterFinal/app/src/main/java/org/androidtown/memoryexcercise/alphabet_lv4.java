package org.androidtown.memoryexcercise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
public class alphabet_lv4 extends AppCompatActivity {
    SharedPreferences sh;
    SharedPreferences.Editor toEdit;
    TextView gameExplain,gameExplain2, textCenter, textQuestion, noti;
    GridLayout ansGrid;
    TextView[] text;
    Random random;
    int[] randNum;
    String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    String[] number = {"①", "②", "③", "④"};
    String ans = "";
    String[] answer = new String[4];
    int count = 0;
    int currentPoint = 0;
    int timerVal;
    int num = 4;
    CountDownTimer timer;
    BackgroundTask task;
    TextView[] ansBtn;
    ImageView answerImg1,answerImg2,answerImg3,answerImg4;
    Button homebtn,nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        count = intent.getIntExtra("count",0);
        currentPoint = intent.getIntExtra("currentPoint",0);

        super.onCreate(savedInstanceState);
        System.out.println("test get in onCreate");
        setContentView(R.layout.activity_alphabet_lv4);

        gameExplain = (TextView) findViewById(R.id.gameExplain);
        gameExplain2 = (TextView)findViewById(R.id.gameExplain2);
        textQuestion = (TextView) findViewById(R.id.textQuestion);
        ansGrid = (GridLayout) findViewById(R.id.ansGrid);
        text = new TextView[4];
        text[0] = (TextView) findViewById(R.id.text0);
        text[1] = (TextView) findViewById(R.id.text1);
        text[2] = (TextView) findViewById(R.id.text2);
        text[3] = (TextView) findViewById(R.id.text3);

        textCenter = (TextView) findViewById(R.id.textCenter);
        noti = (TextView)findViewById(R.id.noti);

        random = new Random();
        randNum = new int[4];

        ansBtn = new TextView[4];
        ansBtn[0] = (TextView) findViewById(R.id.ansBtn0);
        ansBtn[1] = (TextView) findViewById(R.id.ansBtn1);
        ansBtn[2] = (TextView) findViewById(R.id.ansBtn2);
        ansBtn[3] = (TextView) findViewById(R.id.ansBtn3);
        answerImg1 = (ImageView)findViewById(R.id.answer1);
        answerImg2 = (ImageView)findViewById(R.id.answer2);
        answerImg3 = (ImageView)findViewById(R.id.answer3);
        answerImg4 = (ImageView)findViewById(R.id.answer4);
        homebtn = (Button)findViewById(R.id.homebtn);
        nextbtn = (Button)findViewById(R.id.nextbtn);

        timerVal = 0;
        timer = new CountDownTimer(3000, 980) {
            @Override
            public void onTick(long millisUntilFinished) {
                textCenter.setText("" + (3 - timerVal));
                timerVal++;
            }

            @Override
            public void onFinish() {
                timerVal = 0;
                memoryTest();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("test get in onStart");
        // 4개의 알파벳을 random으로 고른다. 0 ~ 25

        for (int i = 0; i < 4; i++) {
            randNum[i] = random.nextInt(61);
            answer[i] = alphabet[randNum[i]];
            ans += answer[i];
        }

        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING)
            task.cancel(true);

        System.out.println("test end in onStart");
        task = new BackgroundTask();
        task.execute(0);
    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        boolean flag = true;
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Integer doInBackground(Integer... values) {
            System.out.println("test get in doInBackground");

            // 3개의 알파벳을 차례로 깜박이며 보여주기.
            for (int i = 0; i < 4; i++) {
                if (isCancelled()) break; //*** 이게 없으면 종료에 시간이 걸리므로 back pressed 후 다시 시작할 때 느린 시작 현상
//                try {
//                    Thread.sleep(990); // 1초 간격으로
//                } catch(InterruptedException e) {
//                    e.printStackTrace();
//                }

                for (int j = 0; j < 4; j++) {
                    if (isCancelled()) break; //***
                    try {
                        Thread.sleep(990); // 1초 간격으로
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 보여주기
                    publishProgress(i);
                }
            }
            System.out.println("test end in doInBackground");
            // 1초 쉬었다가 onPostExecute()로 넘어간다.
            try {
                Thread.sleep(990);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return values[0];
        }

        protected void onProgressUpdate(Integer... values) {
            int idx = values[0];

            if (flag) {
                flag = false;
                text[idx].setText(alphabet[randNum[idx]]);
            } else {
                flag = true;
                text[idx].setText("  ");
            }
        }

        protected void onPostExecute(Integer result) {
            gameExplain.setVisibility(View.GONE);
            gameExplain2.setVisibility(View.GONE);
            noti.setVisibility(View.GONE);

            for (int i = 0; i < 4; i++) {
                text[i].setVisibility(View.GONE);
            }

            textCenter.setVisibility(View.VISIBLE);
            //underLine.setVisibility(View.GONE);

            //5초 뒤에 기억력 테스트 화면으로 이동
            timer.cancel();
            timer.start();
        }

        protected void onCancelled() {
        }
    }

    void memoryTest() {
        String suggestion = answer[0] + alphabet[random.nextInt(61)] + alphabet[random.nextInt(61)] + alphabet[random.nextInt(61)];
        int chance;

        gameExplain.setVisibility(View.VISIBLE);
        gameExplain.setText("\n" +
                "Respond to\nthe following question");

        textCenter.setVisibility(View.GONE);
        homebtn.setVisibility(View.VISIBLE);
        nextbtn.setVisibility(View.VISIBLE);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
                    System.out.println("test on back pressed");
                    task.cancel(true);
                    System.out.println("test on back pressed 2");
                }
                alphabet_lv4.super.onBackPressed();
            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), alphabet_lv4.class);
                intent.putExtra("count",count);
                intent.putExtra("currentPoint",currentPoint);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        String[] candidate = new String[4];
        // 먼저 정답이 아닌 4개의 서로 다른(정답과도 다른) 후보(선택지)를 만든다.
        for (int i = 0; i < 4; i++) {
            String tmp = "";
            for (int j = 0; j < 4; j++) { // 무작위로 알파벳 4개를 골라서 만든다.
                if(random.nextBoolean()){
                    tmp += answer[random.nextInt(3)];
                }
                else {
                    tmp += alphabet[random.nextInt(61)];
                }
            }

            int cnt = 0;
            for (int j = 0; j < i; j++) { // 정답 및 앞의 선택지들과 다른지 체크
                if (!tmp.equals(ans) && !tmp.equals(candidate[j])) cnt++;
            }

            if (cnt == i) candidate[i] = number[i] + " " + tmp; //모두 다르면 확정
            else i--; //아닐 경우 다시...
        }
        // 그리고 4개의 선택지 중 하나의 선택지를 랜덤하게 골라서 정답을 넣는다.
        int ansIdx = random.nextInt(4);
        candidate[ansIdx] = number[ansIdx] + " " + ans;

        while(true){
            chance = random.nextInt(4);
            if(!candidate[chance].substring(2).equals(ans)) {
                candidate[chance] = "";
                candidate[chance] = number[chance] + " " + suggestion;
                break;
            }
        }

        for (int i = 0; i < 4; i++) { // 4개의 선택지 setting.
            ansBtn[i].setText(candidate[i]);
        }

        ansGrid.setVisibility(View.VISIBLE);
        textQuestion.setVisibility(View.VISIBLE);
        count++;
        for (int i = 0; i < 4; i++) {
            final int idx = i;
            final int aIdx = ansIdx;
            ansBtn[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (idx == aIdx) { // 정답을 선택
                        Alphabet_round.round++;
                        Alphabet_round.current = Alphabet_round.round * 10;

                            //게임 종료 알림
                            AlertDialog.Builder builder = new AlertDialog.Builder(alphabet_lv4.this);
                            builder.setCancelable(false);
                            builder.setTitle("Correct!");
                            currentPoint++;
                            setStar(currentPoint);
                            //builder.setMessage("맞았습니다.\n(다음문제로 넘어가시겠습니까?)");
                            builder.setPositiveButton("next game",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getApplicationContext(), alphabet_lv4.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("count",count);
                                            intent.putExtra("currentPoint",currentPoint);
                                            startActivity(intent);
                                        }
                                    });
                            builder.setNegativeButton("Home",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
                                                System.out.println("test on back pressed");
                                                task.cancel(true);
                                                System.out.println("test on back pressed 2");
                                            }
                                            finish();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(178, 255, 255, 255)));
                            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                            wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                            wmlp.y = 550;
                            dialog.show();

                    } else { // 오답을 선택
                        startVibrate();
                        //게임 종료 알림


                            AlertDialog.Builder builder = new AlertDialog.Builder(alphabet_lv4.this);
                            builder.setCancelable(false);
                            builder.setTitle("Incorrect!");
                            //builder.setMessage("틀렸습니다.\n(다음문제로 넘어가시겠습니까?)");

                        if(aIdx == 0)
                            answerImg1.setVisibility(View.VISIBLE);
                        else if(aIdx == 1)
                            answerImg2.setVisibility(View.VISIBLE);
                        else if(aIdx ==2)
                            answerImg3.setVisibility(View.VISIBLE);
                        else if(aIdx == 3)
                            answerImg4.setVisibility(View.VISIBLE);

                            builder.setPositiveButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            builder.setNegativeButton("Home",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
                                                System.out.println("test on back pressed");
                                                task.cancel(true);
                                                System.out.println("test on back pressed 2");
                                            }
                                            finish();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(178, 255, 255, 255)));
                            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                            wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                            wmlp.y = 550;
                            dialog.show();

                    }
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            System.out.println("test on back pressed");
            task.cancel(true);
            System.out.println("test on back pressed 2");
        }
    }

    @Override
    public void onBackPressed() {
        dialogShow();
    }

    public void dialogShow() {
        //게임 종료 알림
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("End game");
        builder.setCancelable(false);
        builder.setMessage("Would you like to end the game?\n(* Game data will be removed)");
        builder.setPositiveButton("next game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton("Home",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
                            System.out.println("test on back pressed");
                            task.cancel(true);
                            System.out.println("test on back pressed 2");
                        }
                        alphabet_lv4.super.onBackPressed();
                    }
                });
        builder.show();
    }

    public void startVibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }

    public void setStar(int current){
        int star;
        sh = getSharedPreferences("level credential",0);
        toEdit = sh.edit();
        star = sh.getInt("game5level4", 0);
        if(current > star)
            toEdit.putInt("game5level4",current);
        toEdit.commit();

    }
}