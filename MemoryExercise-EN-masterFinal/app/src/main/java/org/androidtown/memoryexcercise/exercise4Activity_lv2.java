package org.androidtown.memoryexcercise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

import static android.view.View.VISIBLE;

public class exercise4Activity_lv2 extends AppCompatActivity {
    SharedPreferences sh;
    SharedPreferences.Editor toEdit;
    //UI 변순
    TextView ex4Question1;
    TextView ex4Question2;

    LinearLayout fruitLinear0;
    LinearLayout fruitLinear1;
    LinearLayout fruitLinear2;///////////////////강지호
    RelativeLayout fruitRelative2;
    RelativeLayout sfruitRelative;//////////7/23

    TextView countResult;
    TextView countdown;

    //타이머
    CountDownTimer timer = null;

    //타이머 카운트
    int count = 4;
    int gameCount;

    int gameLevel;
    static float level=(float)1.5 ;//난이도 조절. ////////////////////////강지호
    static int sublevel=0;////////////////////////강지호
    //과일 이미지뷰
    ImageView fruit1;
    ImageView fruit2;
    ImageView fruit3;
    ImageView fruit4;
    ImageView fruit5;
    ImageView fruit6;
    ImageView fruit7;
    ImageView fruit8;
    ImageView fruit9;

    ImageView fruit1Answer;
    ImageView fruit2Answer;
    ImageView fruit3Answer;
    ImageView fruit4Answer;
    ImageView fruit5Answer;
    ImageView fruit6Answer;
    ImageView fruit7Answer;
    ImageView fruit8Answer;
    ImageView fruit9Answer;



    //문제 과일 이미지 뷰
    //ImageView fruitImage0;
    ImageView fruitImage1;
    ImageView fruitImage2;
    ImageView fruitImage3;/////////강지호
    ImageView fruitImage4;/////////강지호
    Button homebtn,nextbtn;
    //정답 과일
    int[] answer;

    //정답 수
    int answerCount = 0;
    static int answerNumber = 3;/////////강지호
    static int currentPoint;////////////////6/1
    int highestPoint;
    int num;

    static int barCheck=0;////////////////5/26

    //기회 부여
    int chance = 0;

    //스테이지 부여
    static int stageNumber = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_exercise4_lv2);


        Intent intent = getIntent();
        currentPoint = intent.getIntExtra("currentPoint",0);
        gameCount = intent.getIntExtra("count",0);
        gameLevel = intent.getIntExtra("level",10);
        if(gameLevel != 10)
            level = (float)gameLevel;


        //UI 가져오기
        ex4Question1 = (TextView) findViewById(R.id.ex4Question1);
        ex4Question2 = (TextView) findViewById(R.id.ex4Question2);
//fruitLinear0 =(LinearLayout)findViewById(R.id.fruitLinear0);/////////강지호
        fruitLinear1 = (LinearLayout) findViewById(R.id.fruitLinear1);
        fruitLinear2 = (LinearLayout) findViewById(R.id.fruitLinear2);///////////////강지호
        fruitRelative2 = (RelativeLayout) findViewById(R.id.fruitRelative2);


        countResult = (TextView) findViewById(R.id.countResult);
        countdown = (TextView) findViewById(R.id.countdown);


        fruit1 = (ImageView) findViewById(R.id.fruit1);
        fruit2 = (ImageView) findViewById(R.id.fruit2);
        fruit3 = (ImageView) findViewById(R.id.fruit3);
        fruit4 = (ImageView) findViewById(R.id.fruit4);
        fruit5 = (ImageView) findViewById(R.id.fruit5);
        fruit6 = (ImageView) findViewById(R.id.fruit6);
        fruit7 = (ImageView) findViewById(R.id.fruit7);
        fruit8 = (ImageView) findViewById(R.id.fruit8);
        fruit9 = (ImageView) findViewById(R.id.fruit9);




        fruit1Answer = (ImageView) findViewById(R.id.fruit1Answer);
        fruit2Answer = (ImageView) findViewById(R.id.fruit2Answer);
        fruit3Answer = (ImageView) findViewById(R.id.fruit3Answer);
        fruit4Answer = (ImageView) findViewById(R.id.fruit4Answer);
        fruit5Answer = (ImageView) findViewById(R.id.fruit5Answer);
        fruit6Answer = (ImageView) findViewById(R.id.fruit6Answer);
        fruit7Answer = (ImageView) findViewById(R.id.fruit7Answer);
        fruit8Answer = (ImageView) findViewById(R.id.fruit8Answer);
        fruit9Answer = (ImageView) findViewById(R.id.fruit9Answer);

        homebtn = (Button)findViewById(R.id.homebtn);
        nextbtn = (Button)findViewById(R.id.nextbtn);


        // fruitImage1 = (ImageView)findViewById(R.id.fruitImage0);
        fruitImage1 = (ImageView) findViewById(R.id.fruitImage1);
        fruitImage2 = (ImageView) findViewById(R.id.fruitImage2);
        fruitImage3 = (ImageView) findViewById(R.id.fruitImage3);/////////강지호
        fruitImage4 = (ImageView) findViewById(R.id.fruitImage4);/////////강지호
        //과일 이미지 UI들 ARRAY

        ImageView[] fruits = {fruit1, fruit2, fruit3, fruit4, fruit5, fruit6, fruit7, fruit8, fruit9};


        //        ImageView[] fruits = {sfruit1, sfruit2, sfruit3, sfruit4, sfruit5, sfruit6};
        //문제 과일 이미지 array
        ImageView[] fruitsAnswerImages = {fruitImage1, fruitImage2,fruitImage3, fruitImage4};/////////강지호 <--답의 이미지를 지정하는 과정이다.
        // 틀렸는지 맞았는지 (0,X) 나타내줄 이미지
        //ImageView[] fruitsAnswer = {fruit1Answer, fruit2Answer, fruit3Answer, fruit4Answer, fruit5Answer, fruit6Answer, fruit7Answer, fruit8Answer, fruit9Answer};

        //스테이지
//pointText2.setVisibility(View.INVISIBLE);//5/26
        //pointText2.setText("Current point: "+ ((barCheck+1)*10)+ " Highest point: "+dbHelper.getResult("game4")+////////////////6/1
        //      "\n\n Required point for next level "+ num*50);



        stageNumber++;
        checkLevel();/////////강지호
        countResult.setText("Select ("+answerCount + "/"+answerNumber+")");

        System.out.println(stageNumber+" sub: "+sublevel);
        //카운트 다운
        timer = new CountDownTimer(3000, 990) {
            @Override
            public void onTick(long millisUntilFinished) {
                count--;
                countdown.setText("Questions will be asked\nin " + count + " sec");
            }


            @Override
            public void onFinish() {
                count = 3;
                ex4Question1.setVisibility(View.INVISIBLE);
                //fruitLinear0.setVisibility(View.INVISIBLE);
                fruitLinear1.setVisibility(View.INVISIBLE);
                fruitLinear2.setVisibility(View.INVISIBLE);
                countdown.setVisibility(View.INVISIBLE);


                ex4Question2.setVisibility(VISIBLE);
                //sfruitRelative.setVisibility(VISIBLE);///////////////////7/24
                fruitRelative2.setVisibility(View.VISIBLE);/////////////////
                countResult.setVisibility(VISIBLE);
                homebtn.setVisibility(View.VISIBLE);
                nextbtn.setVisibility(View.VISIBLE);
            }
        }.start();

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise4Activity_lv2.super.onBackPressed();
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), exercise4Activity_lv2.class);
                intent.putExtra("currentPoint", currentPoint);
                intent.putExtra("count",gameCount);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });




        randomFruits(fruits, fruitsAnswerImages);



        fruit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int found = 0;
                for (int i = 0; i < (2 * level); i++) {/////////강지호
                    if (answer[i] == 0) {
                        // Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_LONG).show();



                        found = 1;
                        fruit1Answer.setImageResource(R.drawable.answer);
                        sublevel++;/////////강지호
                        answerCount++;
                        countResult.setText("Select (" + answerCount + "/" + answerNumber + ")");
                        break;
                    }
                }
                if (found == 0) {
                    //  Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_LONG).show();
                    fruit1Answer.setImageResource(R.drawable.fail);
                    chanceFail();
                }
                fruit1Answer.setVisibility(VISIBLE);
                fruit1.setEnabled(false);

                if (answerCount == answerNumber) {
                    barCheck++;//5/26
                    nextStage(1);
                }
            }
        });

        fruit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int found = 0;

                for (int i = 0; i < (2 * level); i++) {/////////강지호
                    if (answer[i] == 1) {

                        //pointText.setText("Stage: "+(2 * level));//////////////6/1
                        //  Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_LONG).show();
                        found = 1;
                        fruit2Answer.setImageResource(R.drawable.answer);
                        sublevel++;/////////강지호
                        answerCount++;
                        countResult.setText("Select (" + answerCount + "/" + answerNumber + ")");
                        break;
                    }
                }
                if (found == 0) {
                    //   Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_LONG).show();
                    fruit2Answer.setImageResource(R.drawable.fail);
                    chanceFail();
                }
                fruit2Answer.setVisibility(VISIBLE);
                fruit2.setEnabled(false);

                if (answerCount == answerNumber) {
                    barCheck++;//5/26
                    nextStage(1);
                }
            }
        });

        fruit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int found = 0;
                for (int i = 0; i < (2 * level); i++) {/////////강지호
                    if (answer[i] == 2) {

                        //  pointText.setText("Stage: "+(2 * level));//////////////6/1
                        //   Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_LONG).show();
                        found = 1;
                        fruit3Answer.setImageResource(R.drawable.answer);
                        sublevel++;/////////강지호
                        answerCount++;
                        countResult.setText("Select (" + answerCount + "/" + answerNumber + ")");
                        break;
                    }
                }
                if (found == 0) {
                    //  Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_LONG).show();
                    fruit3Answer.setImageResource(R.drawable.fail);
                    chanceFail();
                }
                fruit3Answer.setVisibility(VISIBLE);
                fruit3.setEnabled(false);

                if (answerCount == answerNumber) {
                    barCheck++;//5/26
                    nextStage(1);
                }
            }
        });

        fruit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int found = 0;
                for (int i = 0; i < (2 * level); i++) {/////////강지호
                    if (answer[i] == 3) {

                        //  pointText.setText("Stage: "+(2 * level));//////////////6/1
                        //    Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_LONG).show();
                        found = 1;
                        fruit4Answer.setImageResource(R.drawable.answer);
                        sublevel++;/////////강지호
                        answerCount++;
                        countResult.setText("Select (" + answerCount + "/" + answerNumber + ")");
                        break;
                    }
                }
                if (found == 0) {
                    //  Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_LONG).show();
                    fruit4Answer.setImageResource(R.drawable.fail);
                    chanceFail();
                }

                fruit4Answer.setVisibility(VISIBLE);
                fruit4.setEnabled(false);

                if (answerCount == answerNumber) {
                    barCheck++;//5/26
                    nextStage(1);
                }
            }
        });

        fruit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int found = 0;
                for (int i = 0; i < (2 * level); i++) {/////////강지호
                    if (answer[i] == 4) {

                        //pointText.setText("Stage: "+(2 * level));//////////////6/1
                        //  Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_LONG).show();
                        found = 1;
                        fruit5Answer.setImageResource(R.drawable.answer);
                        sublevel++;/////////강지호
                        answerCount++;
                        countResult.setText("Select (" + answerCount + "/" + answerNumber + ")");
                        break;
                    }
                }
                if (found == 0) {
                    //  Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_LONG).show();
                    fruit5Answer.setImageResource(R.drawable.fail);
                    chanceFail();
                }
                ;

                fruit5Answer.setVisibility(VISIBLE);
                fruit5.setEnabled(false);

                if (answerCount == answerNumber) {
                    barCheck++;//5/26
                    nextStage(1);
                }
            }
        });

        fruit6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int found = 0;
                for (int i = 0; i < (2 * level); i++) {/////////강지호
                    if (answer[i] == 5) {

                        //  pointText.setText("Stage: "+(2 * level));//////////////6/1
                        //   Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_LONG).show();
                        found = 1;
                        fruit6Answer.setImageResource(R.drawable.answer);
                        sublevel++;/////////강지호
                        answerCount++;
                        countResult.setText("Select (" + answerCount + "/" + answerNumber + ")");
                        break;
                    }
                }
                if (found == 0) {
                    //  Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_LONG).show();
                    fruit6Answer.setImageResource(R.drawable.fail);
                    chanceFail();
                }
                fruit6Answer.setVisibility(VISIBLE);
                fruit6.setEnabled(false);

                if (answerCount == answerNumber) {
                    barCheck++;//5/26
                    nextStage(1);
                }
            }
        });

        fruit7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int found = 0;
                for (int i = 0; i < (2 * level); i++) {/////////강지호
                    if (answer[i] == 6) {

                        // pointText.setText("Stage: "+(2 * level));//////////////6/1
                        //  Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_LONG).show();
                        found = 1;
                        fruit7Answer.setImageResource(R.drawable.answer);
                        sublevel++;/////////강지호
                        answerCount++;
                        countResult.setText("Select (" + answerCount + "/" + answerNumber + ")");
                        break;
                    }
                }
                if (found == 0) {
                    //  Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_LONG).show();
                    fruit7Answer.setImageResource(R.drawable.fail);
                    chanceFail();
                }

                fruit7Answer.setVisibility(VISIBLE);
                fruit7.setEnabled(false);
                if (answerCount == answerNumber) {
                    barCheck++;//5/26
                    nextStage(1);
                }
            }
        });

        fruit8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int found = 0;
                for (int i = 0; i < (2 * level); i++) {/////////강지호
                    if (answer[i] == 7) {

                        // pointText.setText("Stage: "+(2 * level));//////////////6/1
                        //  Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_LONG).show();
                        found = 1;
                        fruit8Answer.setImageResource(R.drawable.answer);
                        sublevel++;/////////강지호
                        answerCount++;
                        countResult.setText("Select (" + answerCount + "/" + answerNumber + ")");
                        break;
                    }
                }
                if (found == 0) {
                    //  Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_LONG).show();
                    fruit8Answer.setImageResource(R.drawable.fail);
                    chanceFail();
                }

                fruit8Answer.setVisibility(VISIBLE);
                fruit8.setEnabled(false);

                if (answerCount == answerNumber) {
                    barCheck++;//5/26
                    nextStage(1);
                }
            }
        });

        fruit9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int found = 0;
                for (int i = 0; i < (2 * level); i++) { //////////////강지호
                    if (answer[i] == 8) {

                        //pointText.setText("Stage: "+(2 * level));//////////////6/1
                        //Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_LONG).show();
                        found = 1;
                        fruit9Answer.setImageResource(R.drawable.answer);
                        sublevel++;/////////강지호
                        answerCount++;
                        countResult.setText("Select (" + answerCount + "/" + answerNumber + ")");
                        break;
                    }
                }
                if (found == 0) {
                    //Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_LONG).show();
                    fruit9Answer.setImageResource(R.drawable.fail);
                    chanceFail();
                }
                fruit9Answer.setVisibility(VISIBLE);
                fruit9.setEnabled(false);

                if (answerCount == answerNumber) {
                    barCheck++;//5/26
                    nextStage(1);
                }
            }
        });
    }

    public void randomFruits(ImageView[] fruits, ImageView[] fruitsAnswerImages) {
        int randNumber1, randNumber2, randNumber3, randNumber4;////////////////////////강지호
        //랜덤숫자 만들기(처음에 이미지 질문 할 2가지 과일종류)
        randNumber1 = (int) (Math.random() * 4);
        randNumber2 = (int) (Math.random() * 4);
        randNumber3 = ((int) (Math.random() * 4) + 5);
        randNumber4 = ((int) (Math.random() * 4 )+ 5);

        if (level == 1)////////////////////////강지호
        {
            while (true) {
                if (randNumber1 == randNumber2) {
                    randNumber1 = (int) (Math.random() * 4);
                    randNumber2 = (int) (Math.random() * 4);
                } else {
                    break;
                }
            }
        } else {
            //랜덤 숫자가 둘이 같다면 다시 만들어준다.
            while (true) {
                if (randNumber1 == randNumber2) {
                    randNumber1 = (int) (Math.random() * 4);
                    randNumber2 = (int) (Math.random() * 4);
                }
                else if (randNumber3 == randNumber4) {
                    randNumber3 = ((int) (Math.random() * 4 )+ 5);
                    randNumber4 = ((int) (Math.random() * 4 )+ 5);
                }else
                    break;
            }
        }////////////////


        // 9개의 과일 이미지를 섞기 위해서
        ArrayList<Integer> fruitImages = new ArrayList<Integer>(9);

        fruitImages.add(R.drawable.fru1);
        fruitImages.add(R.drawable.fru2);
        fruitImages.add(R.drawable.fru3);
        fruitImages.add(R.drawable.fru4);
        fruitImages.add(R.drawable.fru5);
        fruitImages.add(R.drawable.fru6);
        fruitImages.add(R.drawable.fru7);
        fruitImages.add(R.drawable.fru8);
        fruitImages.add(R.drawable.fru9);

        //섞는다.
        Collections.shuffle(fruitImages);

        //9개의 과일이미지를 섞은 값을 차례대로 넣어준다.
        for (int i = 0; i < fruitImages.size(); i++) {
            fruits[i].setImageResource(fruitImages.get(i));
        }

        //잘섞였나 확인
        if (level == 1) {////////////////////////강지호
            System.out.println("fruits " + randNumber1 + "frutis2" + randNumber2);
            System.out.println("fruits " + fruits[1].getDrawable());
            fruitsAnswerImages[0].setImageResource(fruitImages.get(randNumber1));
            fruitsAnswerImages[1].setImageResource(fruitImages.get(randNumber2));
        } else {
            System.out.println("fruits " + randNumber1 + "frutis2" + randNumber2);
            System.out.println("fruits " + fruits[1].getDrawable());
            System.out.println("fruits3 " + randNumber3 + "frutis4" + randNumber4);
            System.out.println("fruits " + fruits[2].getDrawable());

            //2개의 과일 이미지 문제용으로 사용한다.//////////////////////여기도 수정
            fruitsAnswerImages[0].setImageResource(fruitImages.get(randNumber1));
            fruitsAnswerImages[1].setImageResource(fruitImages.get(randNumber2));
            fruitsAnswerImages[2].setImageResource(fruitImages.get(randNumber3));
            fruitsAnswerImages[3].setImageResource(fruitImages.get(randNumber4));
        }
        if(level == (float)0.5){
            answer = new int[1];
            answer[0] = randNumber1;
        }//정답 넣기
        else if (level == 1) {////////////////////////강지호
            answer = new int[2];
            answer[0] = randNumber1;
            answer[1] = randNumber2;
        } else {
            answer = new int[4];
            answer[0] = randNumber1;
            answer[1] = randNumber2;
            answer[2] = randNumber3;
            answer[3] = randNumber4;
        }
    }////////////



    public void chanceFail() {
        //틀린경우

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

        nextStage(0);
    }

    public void nextStage(int i) {
        //정답 시
        gameCount++;
        if (i == 1) {
            currentPoint ++;
            //게임 종료 알림
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Correct!");
                setStar(currentPoint);
                // builder.setMessage("맞았습니다.\n(다음문제로 넘어가시겠습니까?)");
                builder.setPositiveButton("next game",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), exercise4Activity_lv2.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("currentPoint", currentPoint);
                                intent.putExtra("count",gameCount);
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton("Home",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                fruitImage2.setVisibility(View.INVISIBLE);/////////////8/15
                                fruitLinear2.setVisibility(View.INVISIBLE);/////////////8/15
                                stageNumber = 0;
                                finish();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(178, 255, 255, 255)));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                wmlp.y = 550;
                dialog.show();

        } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Incorrect!");
            for(int j = 0 ; j < 2*level ; j++){
                if(answer[j] == 0) {
                    fruit1Answer.setImageResource(R.drawable.answer);
                    fruit1Answer.setVisibility(VISIBLE);
                }
                else if(answer[j] == 1) {
                    fruit2Answer.setImageResource(R.drawable.answer);
                    fruit2Answer.setVisibility(VISIBLE);
                }
                else if(answer[j] == 2) {
                    fruit3Answer.setImageResource(R.drawable.answer);
                    fruit3Answer.setVisibility(VISIBLE);
                }
                else if(answer[j] == 3) {
                    fruit4Answer.setImageResource(R.drawable.answer);
                    fruit4Answer.setVisibility(VISIBLE);
                }
                else if(answer[j] == 4) {
                    fruit5Answer.setImageResource(R.drawable.answer);
                    fruit5Answer.setVisibility(VISIBLE);
                }
                else if(answer[j] == 5) {
                    fruit6Answer.setImageResource(R.drawable.answer);
                    fruit6Answer.setVisibility(VISIBLE);
                }
                else if(answer[j] == 6){
                    fruit7Answer.setImageResource(R.drawable.answer);
                    fruit7Answer.setVisibility(VISIBLE);
                }
                else if(answer[j] == 7){
                    fruit8Answer.setImageResource(R.drawable.answer);
                    fruit8Answer.setVisibility(VISIBLE);
                }
                else if(answer[j] == 8){
                    fruit9Answer.setImageResource(R.drawable.answer);
                    fruit9Answer.setVisibility(VISIBLE);
                }
            }

                //builder.setMessage("틀렸습니다.\n(다음문제로 넘어가시겠습니까?)");
                builder.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.setNegativeButton("Home",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                fruitImage2.setVisibility(View.INVISIBLE);/////////////8/15
                                fruitLinear2.setVisibility(View.INVISIBLE);/////////////8/15
                                stageNumber = 0;
                                finish();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(178, 255, 255, 255)));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                wmlp.y = 550;
                dialog.show();

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
                        barCheck=0;/////6/1
                        level = (float)0.5;
                        sublevel=0;
                        fruitImage2.setVisibility(View.INVISIBLE);/////////////8/15
                        fruitLinear2.setVisibility(View.INVISIBLE);/////////////8/15
                        exercise4Activity_lv2.super.onBackPressed();
                        finish();
                    }
                });
        builder.show();
    }

    /////강지호 추가
    public void checkLevel() {
        if(level == 2){
            System.out.println("LEVEL : 4");
            answerNumber = 4;
            level=2;
            // fruitLinear0.setVisibility(View.INVISIBLE);
            fruitImage2.setVisibility(VISIBLE);
            fruitLinear2.setVisibility(VISIBLE);
            // fruitLinear2.setVisibility(View.VISIBLE);
        }
        else{
            System.out.println("LEVEL : 3");
            answerNumber = 3;
            level=(float)1.5;
//   fruitLinear0.setVisibility(View.INVISIBLE);
            fruitImage4.setVisibility(View.INVISIBLE);
            // fruitLinear2.setVisibility(View.VISIBLE);
        }
    }

    public void setStar(int current){
        int star;
        sh = getSharedPreferences("level credential",0);
        toEdit = sh.edit();
        if(level == 1.5) {
            star = sh.getInt("game4level3", 0);
            if (current > star)
                toEdit.putInt("game4level3", current);
        }
        if(level == 2){
            star = sh.getInt("game4level4",0);
            if(current > star)
                toEdit.putInt("game4level4",0);
        }
        toEdit.commit();

    }
}
