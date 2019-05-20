package org.androidtown.memoryexcercise;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by imseokbin on 2018. 8. 12..
 */

public class LevelSelect extends AppCompatActivity {
    SharedPreferences sh_pref;
    SharedPreferences.Editor toEdit;
    Button level1,level2,level3,level4;
    ImageButton homeButton;
    TextView level1Star,level2Star,level3Star,level4Star;
    ImageView imageView2, imageView3, imageView4, imageView5;
    int level = 1,gameType;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        level1 = (Button)findViewById(R.id.level1);
        level2 = (Button)findViewById(R.id.level2);
        level3 = (Button)findViewById(R.id.level3);
        level4 = (Button)findViewById(R.id.level4);
        homeButton = (ImageButton)findViewById(R.id.homeButton);

        level1Star = (TextView)findViewById(R.id.level1Star);
        level2Star = (TextView)findViewById(R.id.level2Star);
        level3Star = (TextView)findViewById(R.id.level3Star);
        level4Star = (TextView)findViewById(R.id.level4Star);

        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        imageView5 = (ImageView)findViewById(R.id.imageView5);

        Intent intent = getIntent();
        gameType = intent.getIntExtra("gameType",0);
        if(gameType == 1){
            sh_pref = getSharedPreferences("level credential",0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game1level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game1level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game1level3",0)));
            level4.setEnabled(false);

            imageView5.setVisibility(View.INVISIBLE);
            level4.setVisibility(View.INVISIBLE);
            level4Star.setVisibility(View.INVISIBLE);
            level4.setEnabled(false);
        }
        else if(gameType ==2) {
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game2level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game2level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game2level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game2level4",0)));
        }
        else if(gameType ==3) {
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game3level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game3level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game3level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game3level4",0)));
        }
        else if(gameType ==4) {
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game4level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game4level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game4level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game4level4",0)));

        }
        else if(gameType ==5) {
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game5level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game5level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game5level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game5level4",0)));
        }
        else if(gameType == 6){
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game6level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game6level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game6level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game6level4",0)));

            imageView4.setVisibility(View.INVISIBLE);
            imageView5.setVisibility(View.INVISIBLE);

            level3.setVisibility(View.INVISIBLE);
            level3Star.setVisibility(View.INVISIBLE);
            level4.setVisibility(View.INVISIBLE);
            level4Star.setVisibility(View.INVISIBLE);
        }
        else if(gameType == 7){
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game7level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game7level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game7level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game7level4",0)));

            level2.setVisibility(View.INVISIBLE);
            level2Star.setVisibility(View.INVISIBLE);
            level3.setVisibility(View.INVISIBLE);
            level3Star.setVisibility(View.INVISIBLE);
            level4.setVisibility(View.INVISIBLE);
            level4Star.setVisibility(View.INVISIBLE);

            imageView3.setVisibility(View.INVISIBLE);
            imageView4.setVisibility(View.INVISIBLE);
            imageView5.setVisibility(View.INVISIBLE);
        }


        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameType ==1){
                    Intent intent = new Intent(getApplicationContext(),Excercise1Activity.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==2){
                    Intent intent = new Intent(getApplicationContext(),Game2Activity.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==3){
                    Intent intent = new Intent(getApplicationContext(),NumRemember.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==4){
                    Intent intent = new Intent(getApplicationContext(),excercise4Activity_first.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==5){
                    Intent intent = new Intent(getApplicationContext(),Game5Activity.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==6){
                    Intent intent = new Intent(getApplicationContext(),zoo_long_term.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==7){
                    Intent intent = new Intent(getApplicationContext(),NameMatching.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameType ==1){
                    Intent intent = new Intent(getApplicationContext(),Excercise1Activity.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==2){
                    Intent intent = new Intent(getApplicationContext(),Game2Activity.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==3){
                    Intent intent = new Intent(getApplicationContext(),NumRemember.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==4){
                    Intent intent = new Intent(getApplicationContext(),excercise4Activity.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==5){
                    Intent intent = new Intent(getApplicationContext(),alphabet_lv2.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==6){
                    Intent intent = new Intent(getApplicationContext(),zoo_long_term_lv2.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameType ==1){
                    Intent intent = new Intent(getApplicationContext(),Excercise1Activity.class);
                    intent.putExtra("level",3);
                    startActivity(intent);
                }
                else if(gameType ==2){
                    Intent intent = new Intent(getApplicationContext(),Game2Activity.class);
                    intent.putExtra("level",3);
                    startActivity(intent);
                }
                else if(gameType ==3){
                    Intent intent = new Intent(getApplicationContext(),NumRemember.class);
                    intent.putExtra("level",3);
                    startActivity(intent);
                }
                else if(gameType ==4){
                    Intent intent = new Intent(getApplicationContext(),exercise4Activity_lv2.class);
                    intent.putExtra("level",1.5);
                    startActivity(intent);
                }
                else if(gameType ==5){
                    Intent intent = new Intent(getApplicationContext(),alphabet_lv3.class);
                    intent.putExtra("level",3);
                    startActivity(intent);
                }
                else if(gameType ==6){
                    Intent intent = new Intent(getApplicationContext(),zoo_long_term_lv3.class);
                    intent.putExtra("level",3);
                    startActivity(intent);
                }
            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameType ==1){

                }
                else if(gameType ==2){
                    Intent intent = new Intent(getApplicationContext(),Game2Activity.class);
                    intent.putExtra("level",4);
                    startActivity(intent);
                }
                else if(gameType ==3){
                    Intent intent = new Intent(getApplicationContext(),NumRemember.class);
                    intent.putExtra("level",4);
                    intent.putExtra("round",10000);
                    startActivity(intent);
                }
                else if(gameType ==4){
                    Intent intent = new Intent(getApplicationContext(),exercise4Activity_lv2.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==5){
                    Intent intent = new Intent(getApplicationContext(),alphabet_lv4.class);
                    intent.putExtra("level",4);
                    startActivity(intent);
                }
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        gameType = intent.getIntExtra("gameType",0);
        sh_pref = getSharedPreferences("level credential",0);
        toEdit = sh_pref.edit();

        if(gameType == 1){
            sh_pref = getSharedPreferences("level credential",0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game1level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game1level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game1level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game1level4",0)));

            imageView5.setVisibility(View.INVISIBLE);
            level4.setVisibility(View.INVISIBLE);
            level4Star.setVisibility(View.INVISIBLE);
            level4.setEnabled(false);
        }
        else if(gameType ==2) {
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game2level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game2level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game2level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game2level4",0)));
        }
        else if(gameType ==3) {
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game3level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game3level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game3level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game3level4",0)));
        }
        else if(gameType ==4) {
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game4level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game4level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game4level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game4level4",0)));

        }
        else if(gameType ==5) {
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game5level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game5level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game5level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game5level4",0)));
        }

        else if(gameType == 6){
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game6level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game6level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game6level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game6level4",0)));

            imageView4.setVisibility(View.INVISIBLE);
            imageView5.setVisibility(View.INVISIBLE);
            level3.setVisibility(View.INVISIBLE);
            level3Star.setVisibility(View.INVISIBLE);
            level4.setVisibility(View.INVISIBLE);
            level4Star.setVisibility(View.INVISIBLE);
        }
        else if(gameType == 7){
            sh_pref = getSharedPreferences("level credential", 0);
            toEdit = sh_pref.edit();
            level1Star.setText(Integer.toString(sh_pref.getInt("game7level1",0)));
            level2Star.setText(Integer.toString(sh_pref.getInt("game7level2",0)));
            level3Star.setText(Integer.toString(sh_pref.getInt("game7level3",0)));
            level4Star.setText(Integer.toString(sh_pref.getInt("game7level4",0)));

            level2.setVisibility(View.INVISIBLE);
            level2Star.setVisibility(View.INVISIBLE);
            level3.setVisibility(View.INVISIBLE);
            level3Star.setVisibility(View.INVISIBLE);
            level4.setVisibility(View.INVISIBLE);
            level4Star.setVisibility(View.INVISIBLE);

            imageView3.setVisibility(View.INVISIBLE);
            imageView4.setVisibility(View.INVISIBLE);
            imageView5.setVisibility(View.INVISIBLE);
        }


        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameType ==1){
                    Intent intent = new Intent(getApplicationContext(),Excercise1Activity.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==2){
                    Intent intent = new Intent(getApplicationContext(),Game2Activity.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==3){
                    Intent intent = new Intent(getApplicationContext(),NumRemember.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==4){
                    Intent intent = new Intent(getApplicationContext(),excercise4Activity_first.class);
                    startActivity(intent);
                }
                else if(gameType ==5){
                    Intent intent = new Intent(getApplicationContext(),Game5Activity.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==6){
                    Intent intent = new Intent(getApplicationContext(),zoo_long_term.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
                else if(gameType ==7){
                    Intent intent = new Intent(getApplicationContext(),NameMatching.class);
                    intent.putExtra("level",1);
                    startActivity(intent);
                }
            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameType ==1){
                    Intent intent = new Intent(getApplicationContext(),Excercise1Activity.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==2){
                    Intent intent = new Intent(getApplicationContext(),Game2Activity.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==3){
                    Intent intent = new Intent(getApplicationContext(),NumRemember.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==4){
                    Intent intent = new Intent(getApplicationContext(),excercise4Activity.class);
                    startActivity(intent);
                }
                else if(gameType ==5){
                    Intent intent = new Intent(getApplicationContext(),alphabet_lv2.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==6){
                    Intent intent = new Intent(getApplicationContext(),zoo_long_term_lv2.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameType ==1){
                    Intent intent = new Intent(getApplicationContext(),Excercise1Activity.class);
                    intent.putExtra("level",3);
                    startActivity(intent);
                }
                else if(gameType ==2){
                    Intent intent = new Intent(getApplicationContext(),Game2Activity.class);
                    intent.putExtra("level",3);
                    startActivity(intent);
                }
                else if(gameType ==3){
                    Intent intent = new Intent(getApplicationContext(),NumRemember.class);
                    intent.putExtra("level",3);

                    startActivity(intent);
                }
                else if(gameType ==4){
                    Intent intent = new Intent(getApplicationContext(),exercise4Activity_lv2.class);
                    startActivity(intent);
                }
                else if(gameType ==5){
                    Intent intent = new Intent(getApplicationContext(),alphabet_lv3.class);
                    intent.putExtra("level",3);
                    startActivity(intent);
                }
                else if(gameType ==6){
                    Intent intent = new Intent(getApplicationContext(),zoo_long_term_lv3.class);
                    intent.putExtra("level",3);
                    startActivity(intent);
                }
            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameType ==1){
                    Intent intent = new Intent(getApplicationContext(),Excercise1Activity.class);
                    intent.putExtra("level",4);
                    startActivity(intent);
                }
                else if(gameType ==2){
                    Intent intent = new Intent(getApplicationContext(),Game2Activity.class);
                    intent.putExtra("level",4);
                    startActivity(intent);
                }
                else if(gameType ==3){
                    Intent intent = new Intent(getApplicationContext(),NumRemember.class);
                    intent.putExtra("level",4);

                    startActivity(intent);
                }
                else if(gameType ==4){
                    Intent intent = new Intent(getApplicationContext(),exercise4Activity_lv2.class);
                    intent.putExtra("level",2);
                    startActivity(intent);
                }
                else if(gameType ==5){
                    Intent intent = new Intent(getApplicationContext(),alphabet_lv4.class);
                    intent.putExtra("level",4);
                    startActivity(intent);
                }
            }
        });

    }
}