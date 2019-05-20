package org.androidtown.memoryexcercise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import static android.view.View.VISIBLE;

public class Game2Activity extends AppCompatActivity {
    public ImageButton[] buttonArr = new ImageButton[9];
    public ImageView[] checkArr = new ImageView[9];
    public String buttonId[] = {"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"};
    TextView textView1, textView2, textView3, textView4, textView5, textView6;
    public ConstraintLayout buttonLayout;


    Random random;
    BackgroundTask task;
    Button homeBtn,nextBtn;
    final int randNum[] = new int[4];
    boolean[] ansCheck = new boolean[4];
    int ansCnt = 0;
    int timerVal;
    int timerCount = 3;
    int playChance = 2;
    int level,count = 1;
    int currentPoint ;
    int highestPoint;
    SharedPreferences sh;
    SharedPreferences.Editor toEdit;
    static int stageNumber = 0;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);


        Intent intent1 = getIntent();
        level = intent1.getIntExtra("level",1);
        currentPoint = intent1.getIntExtra("currentPoint",0);
        count = intent1.getIntExtra("gameCount",1);

        random = new Random();
        buttonArr[0] = (ImageButton) findViewById(R.id.c0);
        buttonArr[1] = (ImageButton) findViewById(R.id.c1);
        buttonArr[2] = (ImageButton) findViewById(R.id.c2);
        buttonArr[3] = (ImageButton) findViewById(R.id.c3);
        buttonArr[4] = (ImageButton) findViewById(R.id.c4);
        buttonArr[5] = (ImageButton) findViewById(R.id.c5);
        buttonArr[6] = (ImageButton) findViewById(R.id.c6);
        buttonArr[7] = (ImageButton) findViewById(R.id.c7);
        buttonArr[8] = (ImageButton) findViewById(R.id.c8);

        checkArr[0] = (ImageView) findViewById(R.id.answer0);
        checkArr[1] = (ImageView) findViewById(R.id.answer1);
        checkArr[2] = (ImageView) findViewById(R.id.answer2);
        checkArr[3] = (ImageView) findViewById(R.id.answer3);
        checkArr[4] = (ImageView) findViewById(R.id.answer4);
        checkArr[5] = (ImageView) findViewById(R.id.answer5);
        checkArr[6] = (ImageView) findViewById(R.id.answer6);
        checkArr[7] = (ImageView) findViewById(R.id.answer7);
        checkArr[8] = (ImageView) findViewById(R.id.answer8);

        homeBtn = (Button)findViewById(R.id.homebtn);
        nextBtn = (Button)findViewById(R.id.nextbtn);

        textView1 = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        //textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        buttonLayout = (ConstraintLayout)findViewById(R.id.buttonLayout);

        //문제 수증가
        stageNumber++;

        timerVal = 0;
        timer = new CountDownTimer(3000, 990) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView3.setText("" + (3 - timerVal));
                timerVal++;
            }

            @Override
            public void onFinish() {
                timerVal = 0;
                MemoryTest();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 서로 다른 random number 4개(0 ~ 8)를 찾는다.
        for (int i = 0; i < level; i++) {
            int tmp = random.nextInt(9);
            int cnt = 0;
            for (int j = 0; j < i; j++) {
                if (tmp != randNum[j]) cnt++;
            }

            if (cnt == i) randNum[i] = tmp;
            else i--;
        }

        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING)
            task.cancel(true);

        task = new BackgroundTask();
        task.execute(0);
    }

    @Override
    protected void onStop() {
        super.onStop();

        task.cancel(true);
        finish(); // 다른 화면으로 넘어갈 때, 종료시킨다.
    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        boolean flag = true;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Integer doInBackground(Integer... values) {
            // 4개의 버튼을 4번 깜박이기
            for (int i = 0; i < 6; i++) { // n번 깜박이기 위해서 n*2번 post()호출
                if (isCancelled()) break; //*** 이게 없으면 종료에 시간이 걸리므로 back pressed 후 다시 시작할 때 느린 시작 현상
                try {
                    Thread.sleep(950); // 1초 간격으로
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //깜박이기
                publishProgress();

            }

            //1초 쉬었다가 onPostExecute()로 넘어간다.
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return values[0];
        }

        protected void onProgressUpdate(Integer... values) {

            if (flag) { // 4개의 버튼을 빨간색으로 바꿔준다.
                flag = false;
                for (int i = 0; i < level; i++) {
                    int idx = randNum[i];
                    buttonArr[idx].setImageResource(R.drawable.oval2);
                }
            } else { // 4개의 버튼을 원래의 색으로 바꿔준다.
                flag = true;
                for (int i = 0; i < level; i++) {
                    int idx = randNum[i];
                    buttonArr[idx].setImageResource(R.drawable.oval1);
                }
                timerCount--;
                if (timerCount != 0)
                    textView2.setText("After "+timerCount + " blinks, the problem will appear");


            }

        }

        protected void onPostExecute(Integer result) {
//            for (int i = 0; i < num; i++) {
//                buttonArr[i].setVisibility(View.INVISIBLE);
//            }
            buttonLayout.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(VISIBLE);

            //5초 뒤에 기억력 테스트 화면으로 이동
            timer.cancel();
            timer.start();
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }

    void MemoryTest() {
//        for (int i = 0; i < 9; i++) {
//            buttonArr[i].setVisibility(View.VISIBLE);
//        }

        buttonLayout.setVisibility(VISIBLE);
        textView1.setVisibility(VISIBLE);
        textView3.setVisibility(View.GONE);
        homeBtn.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.VISIBLE);
        textView1.setText("Find\n blinking circles");

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game2Activity.super.onBackPressed();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Game2Activity.class);
                intent.putExtra("level",level);
                intent.putExtra("currentPoint",currentPoint);
                intent.putExtra("gameCount",++count);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }
        });
        // 찾은 깜박이 수 표시
        textView4.setVisibility(VISIBLE);
        textView6.setVisibility(VISIBLE);

        for (int i = 0; i < level; i++) {
            ansCheck[i] = false;
        }

        ansCnt = 0;
        textView4.setText("Circles found(" + ansCnt + "/" + level +")");

        for (int i = 0; i < 9; i++) {
            final int buttonIdx = i;
            buttonArr[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    boolean isAnswer = false;
                    System.out.println("test : 클릭하면 여기 들어와 : " + isAnswer);
                    for (int j = 0; j < level; j++) {
                        checkArr[buttonIdx].setImageResource(R.drawable.checked);
                        checkArr[buttonIdx].setVisibility(View.VISIBLE);
                        if (buttonIdx == randNum[j]) {
                            if (!ansCheck[j]) {
                                ansCheck[j] = true;

                               // buttonArr[buttonIdx].setImageResource(R.drawable.oval2);
                                isAnswer = true;
                                ansCnt++;
                                textView4.setText("Circles found(" + ansCnt +"/" + level +")");
                                break;
                            } else { // 이미 고른 답을 또 눌렀다면
                                isAnswer = true;
                            }
                        }
                    }
                    System.out.println("test : " + isAnswer);
                    if (!isAnswer) { // 고른 것이 답이 아니라면
                        // Insert -> show correct answer code here
                        startVibrate();
                        for(int i = 0 ; i < level ; i++){
                            buttonArr[randNum[i]].setImageResource(R.drawable.oval2);
                        }
                            //게임 종료 알림
                            AlertDialog.Builder builder = new AlertDialog.Builder(Game2Activity.this);
                            builder.setCancelable(false);
                            builder.setTitle("Incorrect!");
                            //builder.setMessage("틀렸습니다.\n(다음문제로 넘어가시겠습니까?)");
                            builder.setPositiveButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                        AlertDialog dialog = builder.create();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(178, 255, 255, 255)));
                        dialog.show();


                        System.out.println("test : 여기 들어오나?");
                    }

                    if (ansCnt == level) { // 4개의 깜박이를 모두 찾은 경우, -> 다음 게임 시작 or 홈화면 선택.
                        currentPoint++;
                        //게임 종료 알림
                        ImageView image = new ImageView(getApplicationContext());
                        image.setImageResource(R.drawable.star);
                            AlertDialog.Builder builder = new AlertDialog.Builder(Game2Activity.this);
                            builder.setCancelable(false);
                            builder.setView(image);
                            builder.setTitle("Correct!");
                            setStar(currentPoint);
                            //builder.setMessage("맞았습니다.\n(다음문제로 넘어가시겠습니까?)");
                            builder.setPositiveButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                        builder.setNegativeButton("Home",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Game2Activity.super.onBackPressed();
                                    }
                                });

                        AlertDialog dialog = builder.create();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(178, 255, 255, 255)));
                        dialog.show();
                    }
                }
            });
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
                        stageNumber = 0;
                        Game2Activity.super.onBackPressed();
                    }
                });
        builder.show();
    }

    public void startVibrate()
    {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }

    public void setStar(int current){
        int star;
        sh = getSharedPreferences("level credential",0);
        toEdit = sh.edit();
        if(level == 1) {
            star = sh.getInt("game2level1", 0);
            if(current > star)
                toEdit.putInt("game2level1",current);
        }
        else if(level == 2) {
            star = sh.getInt("game2level2", 0);
            if(current > star)
                toEdit.putInt("game2level2",current);
        }
        else if(level == 3) {
            star = sh.getInt("game2level3", 0);
            if(current > star)
                toEdit.putInt("game2level3",current);
        }
        else {
            star = sh.getInt("game2level4", 0);
            if(current > star)
                toEdit.putInt("game2level4",current);
        }

        toEdit.commit();

    }

}
