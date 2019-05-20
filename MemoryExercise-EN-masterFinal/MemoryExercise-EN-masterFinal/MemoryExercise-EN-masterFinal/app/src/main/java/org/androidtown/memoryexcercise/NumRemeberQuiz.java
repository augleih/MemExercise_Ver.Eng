package org.androidtown.memoryexcercise;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class NumRemeberQuiz extends AppCompatActivity {
    //변수
    SharedPreferences randNumPref;
    SharedPreferences roundPref;
    SharedPreferences timesPref;
    SharedPreferences scorePref;
    SharedPreferences sh;
    SharedPreferences.Editor toEdit;

    Button homebtn,nextbtn;

    int randomNumber;
    int count = 0,count2=5;
    int round;
    int currentPoint,highestPoint1,level;
    ProgressBar bar;
    CountDownTimer timer2;
    boolean isRunning = false;
    //editText
    EditText editText;
    TextView checkdown,numText;
    boolean isNextStage = false;


    //스테이지 부여
    static int stageNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_remeber_quiz);

        checkdown = (TextView)findViewById(R.id.checktime);
        numText = (TextView)findViewById(R.id.numText);

        homebtn = (Button)findViewById(R.id.homebtn);
        nextbtn = (Button)findViewById(R.id.nextbtn);

        editText = (EditText) findViewById(R.id.editText2);
        editText.requestFocus();

        randNumPref = getSharedPreferences("randNum", MODE_PRIVATE);
        randomNumber = randNumPref.getInt("randNum", 0);

//        roundPref = getSharedPreferences("round",MODE_PRIVATE);
//        round = roundPref.getInt("round",1);
//
//        timesPref = getSharedPreferences("times",MODE_PRIVATE);
//        times = timesPref.getInt("times",1);
//
//        scorePref = getSharedPreferences("score",MODE_PRIVATE);
//        score = scorePref.getInt("score",0);

        Intent intent1 = getIntent();
        round = intent1.getIntExtra("round",10);
        count = intent1.getIntExtra("count",0);
        level = intent1.getIntExtra("level",1);
        currentPoint = intent1.getIntExtra("currentPoint",0);
        highestPoint1 = intent1.getIntExtra("highestPoint",0);

        timer2 = new CountDownTimer(5000, 980) {
            @Override
            public void onTick(long millisUntilFinished) {
                checkdown.setVisibility(View.VISIBLE);
                checkdown.setText(count2 + " sec remaining");
                count2--;
            }
            public void onFinish() {
                count2 = 6;
                checkdown.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(NumRemeberQuiz.this);
                builder.setCancelable(false);
                builder.setTitle("Timeout");

                builder.setPositiveButton("Next Game",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), NumRemember.class);
                                intent.putExtra("round", round);
                                intent.putExtra("currentPoint", currentPoint);
                                intent.putExtra("level", level);
                                intent.putExtra("count", count);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton("Home",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                            }
                        });
                if(!NumRemeberQuiz.this.isFinishing()) {
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }.start();


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String numStr = editText.getText().toString();
                    if (numStr.equals("") || numStr.equals("-")) {// 사용자가 입력을 하지 않거나 숫자가 아닌 값을 입력한 경우
                        Toast.makeText(getApplicationContext(), "Enter the number", Toast.LENGTH_SHORT).show(); // 안내 메시지

                        return true; // 키보드가 남아있음. return false하면 키보드 내려감.
                    }

                    int number = Integer.parseInt(editText.getText().toString());
                    checkNumber(number);
                    return false;
                }
                return false;
            }
        });

        //스테이지

        stageNumber++;

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumRemember.round = 10;
                NumRemeberQuiz.super.onBackPressed();

            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NumRemember.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("round",round);
                intent.putExtra("level",level);
                intent.putExtra("currentPoint", currentPoint);
                intent.putExtra("count", count);
                startActivity(intent);
            }
        });
    }

    public void checkNumber(int number) {
        count++;
        if (isCorrect(number)) {//맞았을 때
//            if(times<3) {
//                times++;
//
//                SharedPreferences.Editor editor = timesPref.edit();
//                editor.putInt("times", times);
//                editor.commit();
//
//                score++;
//                SharedPreferences.Editor scoreEditor = scorePref.edit();
//                scoreEditor.putInt("score", score);
//                scoreEditor.commit();
//
//                if(times==3) {
//                    if(round!=3){
//                        times=1;
//                        SharedPreferences.Editor editor2 = timesPref.edit();
//                        editor2.putInt("times", times);
//                        editor2.commit();
//                        round++;
//                        SharedPreferences.Editor editor3 = roundPref.edit();
//                        editor3.putInt("round", round);
//                        editor3.commit();}
//                }
//            }
//            Intent intent = new Intent(getApplicationContext(), Correct.class);
//            startActivity(intent);
//            finish();

            //난이도 상승

            //게임 종료 알림
                timer2.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Correct!");
                currentPoint++;
                setStar(currentPoint);
                homebtn.setVisibility(View.INVISIBLE);
                nextbtn.setVisibility(View.INVISIBLE);
                ImageView image = new ImageView(getApplicationContext());
                image.setImageResource(R.drawable.star);
                builder.setView(image);
                //builder.setMessage("맞았습니다.\n(다음문제로 넘어가시겠습니까?)\n");
                builder.setPositiveButton("next",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), NumRemember.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("round",round);
                                intent.putExtra("level",level);
                                intent.putExtra("currentPoint", currentPoint);
                                intent.putExtra("count", count);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.setNegativeButton("Home",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                stageNumber = 0;
                                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                NumRemeberQuiz.super.onBackPressed();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                wmlp.y = 550;
                dialog.show();
            }
         else {//틀렸을 때
//            if(times<3) {
//                times++;
//
//                SharedPreferences.Editor editor = timesPref.edit();
//                editor.putInt("times", times);
//                editor.commit();
//
//                if(times==3) {
//                    if(round!=3){
//                        times=1;
//                        SharedPreferences.Editor editor2 = timesPref.edit();
//                        editor2.putInt("times", times);
//                        editor2.commit();
//                        round++;
//                        SharedPreferences.Editor editor3 = roundPref.edit();
//                        editor3.putInt("round", round);
//                        editor3.commit();
//                    }
//                }
//            }
//            Intent intent = new Intent(getApplicationContext(), Incorrect.class);
//            startActivity(intent);
//            finish();

            // 난이도 하강
           // NumRemember.round /= 10;

            // 틀렸을 때 진동
            startVibrate();

                timer2.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Incorrect!");
            numText.setVisibility(View.VISIBLE);
            editText.setVisibility(View.INVISIBLE);
            checkdown.setVisibility(View.INVISIBLE);
            numText.setText(randomNumber + "");
             builder.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.setNegativeButton("Home",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                stageNumber = 0;
                                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                finish();
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

    @Override
    protected void onStart() {
        super.onStart();

        /*
        Thread thread1 = new Thread(new Runnable(){
            public void run(){
                try{
                    for(int i=0; i < 5&& isRunning; i++){
                        Thread.sleep(1000);
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }}
                    catch(Exception ex){
                    Log.e("NumRemberQuiz","Exception in processing message",ex);
                }
            }
        });
        isRunning = true;
        thread1.start();
        */
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public boolean isCorrect(int number) {
        if (number == randomNumber)
            return true;
        else
            return false;
    }

    @Override
    public void onBackPressed() {
        dialogShow();
    }

    public void dialogShow(){
        //게임 종료 알림
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("End game");
        builder.setCancelable(false);
        builder.setMessage("Would you like to end the game?\n(* Game data will be removed)");
        builder.setPositiveButton("Next Game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        immhide.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                    }
                });
        builder.setNegativeButton("Home",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NumRemember.round = 10;
                        NumRemeberQuiz.super.onBackPressed();
                    }
                });
        builder.show();
    }

    public void startVibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }

    /*
    public class ProgressHandler extends Handler {
        public void handleMessage(Message msg){
            bar.incrementProgressBy(20);
            if(bar.getProgress() == bar.getMax())
                return;
        }
    }
    */
    public void setStar(int current){
        int star;
        sh = getSharedPreferences("level credential",0);
        toEdit = sh.edit();
        if(level == 1) {
            star = sh.getInt("game3level1", 0);
            if(current > star)
                toEdit.putInt("game3level1",current);
        }
        else if(level == 2) {
            star = sh.getInt("game3level2", 0);
            if(current > star)
                toEdit.putInt("game3level2",current);
        }
        else if(level == 3) {
            star = sh.getInt("game3level3", 0);
            if(current > star)
                toEdit.putInt("game3level3",current);
        }
        else {
            star = sh.getInt("game3level4", 0);
            if(current > star)
                toEdit.putInt("game3level4",current);
        }

        toEdit.commit();

    }
}

