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

import java.util.ArrayList;
import java.util.Collections;

import static android.view.View.VISIBLE;

//언제 getIntent 로 받는 value 값좀 정리해야겠다. 그리고 sublevel 불필요해보인다. 그것도 한번 자세히 보고 정리해보자.
public class excercise4Activity_first extends AppCompatActivity {
    SharedPreferences sh;
    SharedPreferences.Editor toEdit;
    //UI 변순
    TextView ex4Question1;
    TextView ex4Question2;
    LinearLayout fruitLinear1;
    RelativeLayout fruitRelative2;
    TextView countResult;
    TextView countdown;
    Button homebtn,nextbtn;

    //타이머
    CountDownTimer timer = null;

    //타이머 카운트
    int count = 4;
    static float level=(float)0.5 ;//난이도 조절. ////////////////////////강지호
    static int sublevel = 0;////////////////////////강지호
    //과일 이미지뷰
    ImageView fruit1;
    ImageView fruit2;
    ImageView fruit3;
    ImageView fruit4;
    ImageView fruit5;
    ImageView fruit6;

    ImageView fruit1Answer;
    ImageView fruit2Answer;
    ImageView fruit3Answer;
    ImageView fruit4Answer;
    ImageView fruit5Answer;
    ImageView fruit6Answer;


    //문제 과일 이미지 뷰
    //ImageView fruitImage0;
    ImageView fruitImage1;
    //정답 과일
    int[] answer;

    //정답 수
    int answerCount = 0;
    static int answerNumber = 1;/////////강지호
    static int currentPoint;////////////////6/1
    int highestPoint;
    int num = 1;
    int gameCount;
    int randNumber1, randNumber2;////////////////////////강지호


    static int barCheck=0;////////////////5/26

    //기회 부여
    int chance = 0;

    //스테이지 부여
    static int stageNumber = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_excercise4_first);

        Intent intent = getIntent();
        currentPoint = intent.getIntExtra("currentPoint",0);
        gameCount = intent.getIntExtra("count",0);

        //UI 가져오기
        ex4Question1 = (TextView) findViewById(R.id.ex4Question1);
        ex4Question2 = (TextView) findViewById(R.id.ex4Question2);
//fruitLinear0 =(LinearLayout)findViewById(R.id.fruitLinear0);/////////강지호
        fruitLinear1 = (LinearLayout) findViewById(R.id.fruitLinear1);
        fruitRelative2 = (RelativeLayout) findViewById(R.id.fruitRelative2);



        countResult = (TextView) findViewById(R.id.countResult);
        countdown = (TextView) findViewById(R.id.countdown);

        fruit1 = (ImageView) findViewById(R.id.fruit1);
        fruit2 = (ImageView) findViewById(R.id.fruit2);
        fruit3 = (ImageView) findViewById(R.id.fruit3);
        fruit4 = (ImageView) findViewById(R.id.fruit4);
        fruit5 = (ImageView) findViewById(R.id.fruit5);
        fruit6 = (ImageView) findViewById(R.id.fruit6);

        fruit1Answer = (ImageView) findViewById(R.id.fruit1Answer);
        fruit2Answer = (ImageView) findViewById(R.id.fruit2Answer);
        fruit3Answer = (ImageView) findViewById(R.id.fruit3Answer);
        fruit4Answer = (ImageView) findViewById(R.id.fruit4Answer);
        fruit5Answer = (ImageView) findViewById(R.id.fruit5Answer);
        fruit6Answer = (ImageView) findViewById(R.id.fruit6Answer);

        homebtn = (Button)findViewById(R.id.homebtn);
        nextbtn = (Button)findViewById(R.id.nextbtn);

        // fruitImage1 = (ImageView)findViewById(R.id.fruitImage0);
        fruitImage1 = (ImageView) findViewById(R.id.fruitImage1);
        //과일 이미지 UI들 ARRAY

        ImageView[] fruits = {fruit1, fruit2, fruit3, fruit4, fruit5, fruit6};


        //        ImageView[] fruits = {sfruit1, sfruit2, sfruit3, sfruit4, sfruit5, sfruit6};
        //문제 과일 이미지 array
        ImageView[] fruitsAnswerImages = {fruitImage1};/////////강지호 <--답의 이미지를 지정하는 과정이다.
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
                --count;
                countdown.setText("Questions will be asked\nin " + count + " sec");
            }


            @Override
            public void onFinish() {
                count = 3;
                ex4Question1.setVisibility(View.INVISIBLE);
                //fruitLinear0.setVisibility(View.INVISIBLE);
                fruitLinear1.setVisibility(View.INVISIBLE);
                countdown.setVisibility(View.INVISIBLE);
                ex4Question2.setVisibility(VISIBLE);
                //sfruitRelative.setVisibility(VISIBLE);///////////////////7/24
                fruitRelative2.setVisibility(View.VISIBLE);/////////////////
                countResult.setVisibility(VISIBLE);
                homebtn.setVisibility(View.VISIBLE);
                nextbtn.setVisibility(View.VISIBLE);


            }
        }.start();


        randomFruits(fruits, fruitsAnswerImages);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excercise4Activity_first.super.onBackPressed();
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), excercise4Activity_first.class);
                intent.putExtra("currentPoint", currentPoint);
                intent.putExtra("count",gameCount);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


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


    }

    public void randomFruits(ImageView[] fruits, ImageView[] fruitsAnswerImages) {
        //랜덤숫자 만들기(처음에 이미지 질문 할 2가지 과일종류)
        randNumber1 = (int) (Math.random() * 10000%6);


        // 9개의 과일 이미지를 섞기 위해서
        ArrayList<Integer> fruitImages = new ArrayList<Integer>(6);

        fruitImages.add(R.drawable.fru1);
        fruitImages.add(R.drawable.fru2);
        fruitImages.add(R.drawable.fru3);
        fruitImages.add(R.drawable.fru4);
        fruitImages.add(R.drawable.fru5);
        fruitImages.add(R.drawable.fru6);

        //섞는다.
        Collections.shuffle(fruitImages);

        //9개의 과일이미지를 섞은 값을 차례대로 넣어준다.
        for (int i = 0; i < fruitImages.size(); i++) {
            fruits[i].setImageResource(fruitImages.get(i));
        }

        //잘섞였나 확인
        if (level <= 1) {////////////////////////강지호
            System.out.println("fruits " + randNumber1);
            System.out.println("fruits " + fruits[1].getDrawable());
            fruitsAnswerImages[0].setImageResource(fruitImages.get(randNumber1));
        } else {
            System.out.println("fruits " + randNumber1);
            System.out.println("fruits " + fruits[1].getDrawable());

            //2개의 과일 이미지 문제용으로 사용한다.//////////////////////여기도 수정
            fruitsAnswerImages[0].setImageResource(fruitImages.get(randNumber1));
        }
        if(level == (float)0.5){
            answer = new int[1];
            answer[0] = randNumber1;
        }//정답 넣기
        else if (level == 1) {////////////////////////강지호
            answer = new int[2];
            answer[0] = randNumber1;
        } else {
            answer = new int[4];
            answer[0] = randNumber1;
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
                //게임 종료 알림
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Correct!");
                currentPoint++;
                setStar(currentPoint);
                // builder.setMessage("맞았습니다.\n(다음문제로 넘어가시겠습니까?)");
                builder.setPositiveButton("next game",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), excercise4Activity_first.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("currentPoint", currentPoint);
                                intent.putExtra("count",gameCount);
                                startActivity(intent);
                                finish();
                            }
                        });


                builder.setNegativeButton("Home",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
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
        else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("Incorrect!");
                if(answer[0] == 0) {
                    fruit1Answer.setImageResource(R.drawable.answer);
                    fruit1Answer.setVisibility(VISIBLE);
                }
                else if(answer[0] == 1) {
                    fruit2Answer.setImageResource(R.drawable.answer);
                    fruit2Answer.setVisibility(VISIBLE);
                }
                else if(answer[0] == 2) {
                    fruit3Answer.setImageResource(R.drawable.answer);
                    fruit3Answer.setVisibility(VISIBLE);
                }
                else if(answer[0] == 3) {
                    fruit4Answer.setImageResource(R.drawable.answer);
                    fruit4Answer.setVisibility(VISIBLE);
                }
                else if(answer[0] == 4) {
                    fruit5Answer.setImageResource(R.drawable.answer);
                    fruit5Answer.setVisibility(VISIBLE);
                }
                else if(answer[0] == 5) {
                    fruit6Answer.setImageResource(R.drawable.answer);
                    fruit6Answer.setVisibility(VISIBLE);
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
                        sublevel=0;/////////////8/15
                        excercise4Activity_first.super.onBackPressed();
                    }
                });
        builder.show();
    }

    /////강지호 추가
    public void checkLevel() {
        /*
        if(sublevel>=5){
            System.out.println("LEVEL : 2");
            answerNumber = 2;
            level=1;
            //fruitLinear0.setVisibility(View.INVISIBLE);
            // fruitLinear2.setVisibility(View.VISIBLE);
        }
        */

        if (sublevel < 5) {////////////////////////강지호/////////////////////////////8/15
            System.out.println("LEVEL : 1");
            answerNumber = 1;///////////////////////////////////////추가
        }
    }

    public void setStar(int current){
        int star;
        sh = getSharedPreferences("level credential",0);
        toEdit = sh.edit();
            star = sh.getInt("game4level1", 0);
            if(current > star)
                toEdit.putInt("game4level1",current);
        toEdit.commit();

    }


}
