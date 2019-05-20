package org.androidtown.memoryexcercise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 박재성 on 2017-11-25.
 */

public class Excercise1Activity extends AppCompatActivity {

    //UI 요소들
    SharedPreferences sh;
    SharedPreferences.Editor toEdit;
    Button ex1YesBtn;
    Button ex1NoBtn;

    TextView ex1Question1;
    TextView ex1Question2;
    TextView ex1Question3;
    TextView countdown;
    TextView checkdown;
    ImageView ex1Image,ex1Image2,ex1Image3;
    LinearLayout answerLinear;

    static int problemNumber = 0;

    //타이머
    CountDownTimer timer = null;
    CountDownTimer timer2 = null;

    //카운트
    int count = 4;
    int count2 = 5;
    int level;
    int currentPoint;

    //정답 카운트
    static int answerCount = 0;

    //랜덤변수
    String randomImage1 = null;
    String randomImage2 = null;
    String randomImage3 = null;
    String randomImage4 = null;

    int randomnumber0;
    int randomnumber1;
    int randomnumber2;
    int randomnumber3;
    int randomnumber4;
    int randomnumber5;

    String[] randomCatagory;

    //뒤로가기 버튼 누를시 사용 변수
    int returnValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        level = intent.getIntExtra("level",1);
        super.onCreate(savedInstanceState);
        if(level == 1)
            setContentView(R.layout.excercise1);
        else
            setContentView(R.layout.excercise1_lv2);

        currentPoint = intent.getIntExtra("currentPoint",0);




        //문제 수 초기화
        //counterProblem();

        //UI 가져오기
        ex1YesBtn = (Button) findViewById(R.id.ex1YesBtn);
        ex1NoBtn = (Button) findViewById(R.id.ex1NoBtn);

        ex1Question1 = (TextView) findViewById(R.id.ex1Question1);
        ex1Question2 = (TextView) findViewById(R.id.ex1Question2);
        ex1Question3 = (TextView) findViewById(R.id.ex1Question3);
        countdown = (TextView) findViewById(R.id.countdown);
        checkdown=(TextView)findViewById(R.id.checktime);
        ex1Image = (ImageView) findViewById(R.id.ex1Image);
        ex1Image2 = (ImageView) findViewById(R.id.ex1Image2);
        ex1Image3 = (ImageView) findViewById(R.id.ex1Image3);
        answerLinear = (LinearLayout) findViewById(R.id.answerLinear);

        //ex1Question1.setText(problemNumber + ". " + "사진을 기억하세요");

        if(level == 2)
            ex1Question3.setText("Did you see this image earlier?");
        else if(level == 3)
            ex1Question3.setText("Do these images match with\nimages you saw earlier?");

        //첫 랜덤 초기화
        initRandom();

        //첫 이미지 초기화
        initImage();

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

                ex1Question1.setVisibility(View.INVISIBLE);
                countdown.setVisibility(View.INVISIBLE);
                ex1Question2.setVisibility(View.VISIBLE);
                ex1Question3.setVisibility(View.VISIBLE);
                answerLinear.setVisibility(View.VISIBLE);

                //이미지를 바꿔준다
                changeImage();
                ////////////////////////////////////////////////////
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(Excercise1Activity.this);
                        builder.setCancelable(false);
                        builder.setTitle("Timeout");
                        builder.setPositiveButton("Next Game",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), Excercise1Activity.class);
                                        intent.putExtra("level",level);
                                        intent.putExtra("currentPoint",currentPoint);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                        builder.setNegativeButton("Home",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        answerCount = 0;
                                        problemNumber = 0;
                                        finish();
                                    }
                                });
                        if(!Excercise1Activity.this.isFinishing()) {
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                }.start();
//강지호

            }
        }.start();

        ex1YesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //정답일경우
                boolean check = false;
                if(level == 1 && randomImage3.equals(randomImage1)) check = true;
                if(level == 2 && (randomImage3.equals(randomImage1) || randomImage3.equals(randomImage2))) check = true;
                if(level == 3 && (randomImage3.equals(randomImage1) && randomImage4.equals(randomImage2))) check = true;
                if(check) {

                    //정답갯수 올리기
                    answerCount++;

                    //게임 상황 알림
                    AlertDialog.Builder builder = new AlertDialog.Builder(Excercise1Activity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Correct!");
                    currentPoint++;
                    ImageView image = new ImageView(getApplicationContext());
                    image.setImageResource(R.drawable.star);
                    builder.setView(image);
                    setStar(currentPoint);
                    timer2.cancel();

                    //마지막 문제일경우
                    if(problemNumber == 5)
                    {

                        builder.setMessage("Correct!.\n(This is the last question.)");
                        builder.setNegativeButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        counterProblem();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                        wmlp.y = 550;
                        dialog.show();
                    }

                    //마지막 문제가 아닐 경우
                    else {
                        builder.setPositiveButton("next game",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), Excercise1Activity.class);
                                        intent.putExtra("level",level);
                                        intent.putExtra("currentPoint",currentPoint);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                        builder.setNegativeButton("Home",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        answerCount = 0;
                                        problemNumber = 0;
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

                    //틀린경우
                } else {
                    //게임 상황 알림
                    AlertDialog.Builder builder = new AlertDialog.Builder(Excercise1Activity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Incorrect!");
                    timer2.cancel();


                    //마지막 문제일경우
                    if (problemNumber == 5) {
                        startVibrate();
                        builder.setMessage("Incorrect!\n(This is the last question.)");
                        builder.setNegativeButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
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
                    //마지막 문제가 아닌경우
                    else {
                        startVibrate();
                        builder.setPositiveButton("next game",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), Excercise1Activity.class);
                                        intent.putExtra("level",level);
                                        intent.putExtra("currentPoint",currentPoint);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                        builder.setNegativeButton("Home",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (problemNumber != 5) {
                                            answerCount = 0;
                                            problemNumber = 0;
                                            finish();
                                        }
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
            }
        });

        ex1NoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //정답일경우
                boolean check = false;
                if(level == 1 && randomImage3.equals(randomImage1)) check = true;
                if(level == 2 && (randomImage3.equals(randomImage1) || randomImage3.equals(randomImage2))) check = true;
                if(level == 3 && (randomImage3.equals(randomImage1) && randomImage4.equals(randomImage2))) check = true;
                if (!check) {

                    //정답갯수 올리기
                    answerCount++;

                    //게임 상황 알림
                    AlertDialog.Builder builder = new AlertDialog.Builder(Excercise1Activity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Correct!");
                    currentPoint++;
                    ImageView image = new ImageView(getApplicationContext());
                    image.setImageResource(R.drawable.star);
                    builder.setView(image);
                    setStar(currentPoint);
                    timer2.cancel();

                    //마지막 문제일경우
                    if(problemNumber == 5)
                    {
                        builder.setMessage("Correct!\n(This is the last question.)");
                        builder.setNegativeButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        counterProblem();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                        wmlp.y = 550;
                        dialog.show();
                    }

                    //마지막 문제가 아닐 경우
                    else {
                        builder.setPositiveButton("next game",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), Excercise1Activity.class);
                                        intent.putExtra("level",level);
                                        intent.putExtra("currentPoint",currentPoint);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                        builder.setNegativeButton("Home",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        answerCount = 0;
                                        problemNumber = 0;
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

                    //틀린경우
                } else {
                    //게임 상황 알림
                    AlertDialog.Builder builder = new AlertDialog.Builder(Excercise1Activity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Incorrect!");
                    timer2.cancel();


                    //마지막 문제일경우
                    if (problemNumber == 5) {
                        startVibrate();
                        builder.setNegativeButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        counterProblem();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
                        wmlp.y = 550;
                        dialog.show();
                    }
                    //마지막 문제가 아닌경우
                    else {
                        startVibrate();
                        builder.setPositiveButton("next game",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), Excercise1Activity.class);
                                        intent.putExtra("level",level);
                                        intent.putExtra("currentPoint",currentPoint);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                        builder.setNegativeButton("Home",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (problemNumber != 5) {
                                            answerCount = 0;
                                            problemNumber = 0;
                                            finish();
                                        }
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
            }
        });
    }

    public void counterProblem() {
        problemNumber++;

        // 최대 5문제까지
        if (problemNumber > 5) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Excercise1Activity.this);
            ImageView image = new ImageView(getApplicationContext());
            image.setImageResource(R.drawable.star);
            builder.setView(image);
            builder.setCancelable(false);
            builder.setTitle("Result");
            builder.setMessage("Correct! : " + answerCount );

            answerCount = 0;
            builder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            problemNumber = 0;
                            Excercise1Activity.super.onBackPressed();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void initRandom() {
        //랜덤 카테고리 목록들
        randomCatagory = new String[]{"dog", "object","country"};

        //랜덤 카테고리(0 ~)
        randomnumber0 = (int) (Math.random() * 3);

        //랜덤함수 ( 1 ~ 10 )
        randomnumber1 = (int) (Math.random() * 10) + 1;
        randomnumber2 = (int) (Math.random() * 10) + 1;
        randomnumber3 = (int) (Math.random() * 10) + 1;
        randomnumber4 = (int) (Math.random() * 10) + 1;

        randomImage1 = randomCatagory[randomnumber0] + randomnumber1;
        randomImage2 = randomCatagory[randomnumber0] + randomnumber2;
        randomImage3 = randomCatagory[randomnumber0] + randomnumber3;
        randomImage4 = randomCatagory[randomnumber0] + randomnumber4;

        if(level != 1){
            int setAnswer = (int)(Math.random()*3);
            if(setAnswer != 0) {
                while (randomnumber1 == randomnumber2) {
                    randomnumber2 = (int) (Math.random() * 10) + 1;
                    randomImage2 = randomCatagory[randomnumber0] + randomnumber2;
                }

                randomnumber3 = (int) (Math.random() * 10) + 1;
                randomnumber4 = (int) (Math.random() * 10) + 1;

                while (randomnumber4 == randomnumber3)
                    randomnumber4 = (int) (Math.random() * 10) + 1;

                randomImage3 = randomCatagory[randomnumber0] + randomnumber3;
                randomImage4 = randomCatagory[randomnumber0] + randomnumber4;
            }
            else{
                while (randomnumber1 == randomnumber2) {
                    randomnumber2 = (int) (Math.random() * 10) + 1;
                    randomImage2 = randomCatagory[randomnumber0] + randomnumber2;
                }

                randomImage3 = randomImage1;
                randomImage4 = randomImage2;
            }
        }
        System.out.println("********************************");

        System.out.println(randomImage1);
        System.out.println(randomImage2);
        System.out.println(randomImage3);
        System.out.println(randomImage4);



        /*
        if(level != 1) {
            randomnumber2 = (int) (Math.random() * 10) + 1;
            randomnumber4 = (int) (Math.random() * 10) + 1;

            while (randomnumber2 == randomnumber1)
                randomnumber2 = (int) (Math.random() * 10) + 1;

            randomnumber2 = (int) (Math.random() * 10) + 1;
            randomnumber4 = (int) (Math.random() * 10) + 1;
            while (randomnumber4 == randomnumber3 && randomnumber4 == randomnumber1)
                randomnumber4 = (int) (Math.random() * 10) + 1;

            randomImage2 = randomCatagory[randomnumber0] + randomnumber2;
            randomImage4 = randomCatagory[randomnumber0] + randomnumber4;
        }
        */

        //randomnumber3=((int)(Math.random()*10))/5;//강지호 추가
        /*
        if(randomnumber4==0) {//강지호 추가
            randomnumber1 = (int) (Math.random() * 5) + 1;
            randomnumber2 = (int) (Math.random() * 5) + 1;
        }
        else
        {
            while(!((randomnumber1<11)&&(randomnumber2<11))){
                randomnumber1 = (int) (Math.random() * 5) + 6;
                randomnumber2 = (int) (Math.random() * 5) + 6;
            }
        }
        */

        //랜덤이미지



        //랜덤 결과 출력
        System.out.println("Random result : " + randomImage1 + " ," + randomImage2);
    }

    public void initImage() {
        //첫 이미지

        if (randomImage1.contains("dog")) {
            if (randomImage1.equals("dog1")) {
                ex1Image.setImageResource(R.drawable.dog1);
            } else if (randomImage1.equals("dog2")) {
                ex1Image.setImageResource(R.drawable.dog2);
            } else if (randomImage1.equals("dog3")) {
                ex1Image.setImageResource(R.drawable.dog3);
            } else if (randomImage1.equals("dog4")) {
                ex1Image.setImageResource(R.drawable.dog4);
            } else if (randomImage1.equals("dog5")) {
                ex1Image.setImageResource(R.drawable.dog5);
            } else if (randomImage1.equals("dog6")) {
                ex1Image.setImageResource(R.drawable.dog6);
            } else if (randomImage1.equals("dog7")) {
                ex1Image.setImageResource(R.drawable.dog7);
            } else if (randomImage1.equals("dog8")) {
                ex1Image.setImageResource(R.drawable.dog8);
            } else if (randomImage1.equals("dog9")) {
                ex1Image.setImageResource(R.drawable.dog9);
            } else if (randomImage1.equals("dog10")) {
                ex1Image.setImageResource(R.drawable.dog10);
            }
        } else if (randomImage1.contains("object")) {
            if (randomImage1.equals("object1")) {
                ex1Image.setImageResource(R.drawable.object1);
            } else if (randomImage1.equals("object2")) {
                ex1Image.setImageResource(R.drawable.object2);
            } else if (randomImage1.equals("object3")) {
                ex1Image.setImageResource(R.drawable.object3);
            } else if (randomImage1.equals("object4")) {
                ex1Image.setImageResource(R.drawable.object4);
            } else if (randomImage1.equals("object5")) {
                ex1Image.setImageResource(R.drawable.object5);
            } else if (randomImage1.equals("object6")) {
                ex1Image.setImageResource(R.drawable.object6);
            } else if (randomImage1.equals("object7")) {
                ex1Image.setImageResource(R.drawable.object7);
            } else if (randomImage1.equals("object8")) {
                ex1Image.setImageResource(R.drawable.object8);
            } else if (randomImage1.equals("object9")) {
                ex1Image.setImageResource(R.drawable.object9);
            } else if (randomImage1.equals("object10")) {
                ex1Image.setImageResource(R.drawable.object10);
            }
        }
        else if (randomImage1.contains("country")) {
            if (randomImage1.equals("country1")) {
                ex1Image.setImageResource(R.drawable.country1);
            } else if (randomImage1.equals("country2")) {
                ex1Image.setImageResource(R.drawable.country2);
            } else if (randomImage1.equals("country3")) {
                ex1Image.setImageResource(R.drawable.country3);
            } else if (randomImage1.equals("country4")) {
                ex1Image.setImageResource(R.drawable.country4);
            } else if (randomImage1.equals("country5")) {
                ex1Image.setImageResource(R.drawable.country5);
            } else if (randomImage1.equals("country6")) {
                ex1Image.setImageResource(R.drawable.country6);
            } else if (randomImage1.equals("country7")) {
                ex1Image.setImageResource(R.drawable.country7);
            } else if (randomImage1.equals("country8")) {
                ex1Image.setImageResource(R.drawable.country8);
            } else if (randomImage1.equals("country9")) {
                ex1Image.setImageResource(R.drawable.country9);
            } else if (randomImage1.equals("country10")) {
                ex1Image.setImageResource(R.drawable.country10);
            }
        }



        if(level != 1) {
            if (randomImage2.contains("dog")) {
                if (randomImage2.equals("dog1")) {
                    ex1Image2.setImageResource(R.drawable.dog1);
                } else if (randomImage2.equals("dog2")) {
                    ex1Image2.setImageResource(R.drawable.dog2);
                } else if (randomImage2.equals("dog3")) {
                    ex1Image2.setImageResource(R.drawable.dog3);
                } else if (randomImage2.equals("dog4")) {
                    ex1Image2.setImageResource(R.drawable.dog4);
                } else if (randomImage2.equals("dog5")) {
                    ex1Image2.setImageResource(R.drawable.dog5);
                } else if (randomImage2.equals("dog6")) {
                    ex1Image2.setImageResource(R.drawable.dog6);
                } else if (randomImage2.equals("dog7")) {
                    ex1Image2.setImageResource(R.drawable.dog7);
                } else if (randomImage2.equals("dog8")) {
                    ex1Image2.setImageResource(R.drawable.dog8);
                } else if (randomImage2.equals("dog9")) {
                    ex1Image2.setImageResource(R.drawable.dog9);
                } else if (randomImage2.equals("dog10")) {
                    ex1Image2.setImageResource(R.drawable.dog10);
                }
            } else if (randomImage2.contains("object")) {
                if (randomImage2.equals("object1")) {
                    ex1Image2.setImageResource(R.drawable.object1);
                } else if (randomImage2.equals("object2")) {
                    ex1Image2.setImageResource(R.drawable.object2);
                } else if (randomImage2.equals("object3")) {
                    ex1Image2.setImageResource(R.drawable.object3);
                } else if (randomImage2.equals("object4")) {
                    ex1Image2.setImageResource(R.drawable.object4);
                } else if (randomImage2.equals("object5")) {
                    ex1Image2.setImageResource(R.drawable.object5);
                } else if (randomImage2.equals("object6")) {
                    ex1Image2.setImageResource(R.drawable.object6);
                } else if (randomImage2.equals("object7")) {
                    ex1Image2.setImageResource(R.drawable.object7);
                } else if (randomImage2.equals("object8")) {
                    ex1Image2.setImageResource(R.drawable.object8);
                } else if (randomImage2.equals("object9")) {
                    ex1Image2.setImageResource(R.drawable.object9);
                } else if (randomImage2.equals("object10")) {
                    ex1Image2.setImageResource(R.drawable.object10);
                }
            } else if (randomImage2.contains("country")) {
                if (randomImage2.equals("country1")) {
                    ex1Image2.setImageResource(R.drawable.country1);
                } else if (randomImage2.equals("country2")) {
                    ex1Image2.setImageResource(R.drawable.country2);
                } else if (randomImage2.equals("country3")) {
                    ex1Image2.setImageResource(R.drawable.country3);
                } else if (randomImage2.equals("country4")) {
                    ex1Image2.setImageResource(R.drawable.country4);
                } else if (randomImage2.equals("country5")) {
                    ex1Image2.setImageResource(R.drawable.country5);
                } else if (randomImage2.equals("country6")) {
                    ex1Image2.setImageResource(R.drawable.country6);
                } else if (randomImage2.equals("country7")) {
                    ex1Image2.setImageResource(R.drawable.country7);
                } else if (randomImage2.equals("country8")) {
                    ex1Image2.setImageResource(R.drawable.country8);
                } else if (randomImage2.equals("country9")) {
                    ex1Image2.setImageResource(R.drawable.country9);
                } else if (randomImage2.equals("country10")) {
                    ex1Image2.setImageResource(R.drawable.country10);
                }
            }
        }
    }

    public void changeImage() {
        int ran = (int)(Math.random()*2)+1;
        if(ran == 1){
            randomImage3 = "";
            randomImage3 += randomImage1;
        }
        else
        if (randomImage3.contains("dog")) {
            if (randomImage3.equals("dog1")) {
                ex1Image.setImageResource(R.drawable.dog1);
            } else if (randomImage3.equals("dog2")) {
                ex1Image.setImageResource(R.drawable.dog2);
            } else if (randomImage3.equals("dog3")) {
                ex1Image.setImageResource(R.drawable.dog3);
            } else if (randomImage3.equals("dog4")) {
                ex1Image.setImageResource(R.drawable.dog4);
            } else if (randomImage3.equals("dog5")) {
                ex1Image.setImageResource(R.drawable.dog5);
            } else if (randomImage3.equals("dog6")) {
                ex1Image.setImageResource(R.drawable.dog6);
            } else if (randomImage3.equals("dog7")) {
                ex1Image.setImageResource(R.drawable.dog7);
            } else if (randomImage3.equals("dog8")) {
                ex1Image.setImageResource(R.drawable.dog8);
            } else if (randomImage3.equals("dog9")) {
                ex1Image.setImageResource(R.drawable.dog9);
            } else if (randomImage3.equals("dog10")) {
                ex1Image.setImageResource(R.drawable.dog10);
            }
        } else if (randomImage3.contains("object")) {
            if (randomImage3.equals("object1")) {
                ex1Image.setImageResource(R.drawable.object1);
            } else if (randomImage3.equals("object2")) {
                ex1Image.setImageResource(R.drawable.object2);
            } else if (randomImage3.equals("object3")) {
                ex1Image.setImageResource(R.drawable.object3);
            } else if (randomImage3.equals("object4")) {
                ex1Image.setImageResource(R.drawable.object4);
            } else if (randomImage3.equals("object5")) {
                ex1Image.setImageResource(R.drawable.object5);
            } else if (randomImage3.equals("object6")) {
                ex1Image.setImageResource(R.drawable.object6);
            } else if (randomImage3.equals("object7")) {
                ex1Image.setImageResource(R.drawable.object7);
            } else if (randomImage3.equals("object8")) {
                ex1Image.setImageResource(R.drawable.object8);
            } else if (randomImage3.equals("object9")) {
                ex1Image.setImageResource(R.drawable.object9);
            } else if (randomImage3.equals("object10")) {
                ex1Image.setImageResource(R.drawable.object10);
            }
        }
        else if (randomImage3.contains("country")) {
            if (randomImage3.equals("country1")) {
                ex1Image.setImageResource(R.drawable.country1);
            } else if (randomImage3.equals("country2")) {
                ex1Image.setImageResource(R.drawable.country2);
            } else if (randomImage3.equals("country3")) {
                ex1Image.setImageResource(R.drawable.country3);
            } else if (randomImage3.equals("country4")) {
                ex1Image.setImageResource(R.drawable.country4);
            } else if (randomImage3.equals("country5")) {
                ex1Image.setImageResource(R.drawable.country5);
            } else if (randomImage3.equals("country6")) {
                ex1Image.setImageResource(R.drawable.country6);
            } else if (randomImage3.equals("country7")) {
                ex1Image.setImageResource(R.drawable.country7);
            } else if (randomImage3.equals("country8")) {
                ex1Image.setImageResource(R.drawable.country8);
            } else if (randomImage3.equals("country9")) {
                ex1Image.setImageResource(R.drawable.country9);
            } else if (randomImage3.equals("country10")) {
                ex1Image.setImageResource(R.drawable.country10);
            }
        }
        if(level == 2) {
            ex1Image2.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ex1Image.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            ex1Image.setLayoutParams(params);
        }


        if(level == 3) {
            ran = (int) (Math.random() * 2) + 1;
            ex1Image2.setVisibility(View.VISIBLE);
            if (ran == 1) {
                randomImage4 = "";
                randomImage4 += randomImage2;
            } else if (randomImage4.contains("dog")) {
                if (randomImage4.equals("dog1")) {
                    ex1Image2.setImageResource(R.drawable.dog1);
                } else if (randomImage4.equals("dog2")) {
                    ex1Image2.setImageResource(R.drawable.dog2);
                } else if (randomImage4.equals("dog3")) {
                    ex1Image2.setImageResource(R.drawable.dog3);
                } else if (randomImage4.equals("dog4")) {
                    ex1Image2.setImageResource(R.drawable.dog4);
                } else if (randomImage4.equals("dog5")) {
                    ex1Image2.setImageResource(R.drawable.dog5);
                } else if (randomImage4.equals("dog6")) {
                    ex1Image2.setImageResource(R.drawable.dog6);
                } else if (randomImage4.equals("dog7")) {
                    ex1Image2.setImageResource(R.drawable.dog7);
                } else if (randomImage4.equals("dog8")) {
                    ex1Image2.setImageResource(R.drawable.dog8);
                } else if (randomImage4.equals("dog9")) {
                    ex1Image2.setImageResource(R.drawable.dog9);
                } else if (randomImage4.equals("dog10")) {
                    ex1Image2.setImageResource(R.drawable.dog10);
                }
            } else if (randomImage4.contains("object")) {
                if (randomImage4.equals("object1")) {
                    ex1Image2.setImageResource(R.drawable.object1);
                } else if (randomImage4.equals("object2")) {
                    ex1Image2.setImageResource(R.drawable.object2);
                } else if (randomImage4.equals("object3")) {
                    ex1Image2.setImageResource(R.drawable.object3);
                } else if (randomImage4.equals("object4")) {
                    ex1Image2.setImageResource(R.drawable.object4);
                } else if (randomImage4.equals("object5")) {
                    ex1Image2.setImageResource(R.drawable.object5);
                } else if (randomImage4.equals("object6")) {
                    ex1Image2.setImageResource(R.drawable.object6);
                } else if (randomImage4.equals("object7")) {
                    ex1Image2.setImageResource(R.drawable.object7);
                } else if (randomImage4.equals("object8")) {
                    ex1Image2.setImageResource(R.drawable.object8);
                } else if (randomImage4.equals("object9")) {
                    ex1Image2.setImageResource(R.drawable.object9);
                } else if (randomImage4.equals("object10")) {
                    ex1Image2.setImageResource(R.drawable.object10);
                }
            } else if (randomImage4.contains("country")) {
                if (randomImage4.equals("country1")) {
                    ex1Image2.setImageResource(R.drawable.country1);
                } else if (randomImage4.equals("country2")) {
                    ex1Image2.setImageResource(R.drawable.country2);
                } else if (randomImage4.equals("country3")) {
                    ex1Image2.setImageResource(R.drawable.country3);
                } else if (randomImage4.equals("country4")) {
                    ex1Image2.setImageResource(R.drawable.country4);
                } else if (randomImage4.equals("country5")) {
                    ex1Image2.setImageResource(R.drawable.country5);
                } else if (randomImage4.equals("country6")) {
                    ex1Image2.setImageResource(R.drawable.country6);
                } else if (randomImage4.equals("country7")) {
                    ex1Image2.setImageResource(R.drawable.country7);
                } else if (randomImage4.equals("country8")) {
                    ex1Image2.setImageResource(R.drawable.country8);
                } else if (randomImage4.equals("country9")) {
                    ex1Image2.setImageResource(R.drawable.country9);
                } else if (randomImage4.equals("country10")) {
                    ex1Image2.setImageResource(R.drawable.country10);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        dialogShow();
    }

    public void dialogShow() {
        //게임 종료 알림
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("End game");
        builder.setMessage("Would you like to end the game?\n(* Game data will be removed)");
        builder.setPositiveButton("Next Game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton("Home",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        answerCount = 0;
                        problemNumber = 0;
                        Excercise1Activity.super.onBackPressed();
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
            star = sh.getInt("game1level1", 0);
            if(current > star)
                toEdit.putInt("game1level1",current);
        }
        else if(level == 2) {
            star = sh.getInt("game1level2", 0);
            if(current > star)
                toEdit.putInt("game1level2",current);
        }
        else if(level == 3) {
            star = sh.getInt("game1level3", 0);
            if(current > star)
                toEdit.putInt("game1level3",current);
        }
        toEdit.commit();

    }
}