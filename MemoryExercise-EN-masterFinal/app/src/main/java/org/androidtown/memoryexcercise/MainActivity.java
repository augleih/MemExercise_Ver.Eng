package org.androidtown.memoryexcercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton longTermBtn, shortTermBtn;
    long bpTime = 0;
    Toast bpToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bpToast = Toast.makeText(this, "뒤로가기를 한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT);

        longTermBtn = (ImageButton)findViewById(R.id.longTerm);
        shortTermBtn = (ImageButton)findViewById(R.id.shortTerm);

        longTermBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), long_term_main.class);
                //intent.putExtra("gameType",6);
                startActivity(intent);
            }
        });

        shortTermBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),short_term_main.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        //참고 : http://best421.tistory.com/71
        //이렇게 해도 cancel이 안되는 이유는 onBackPressed()에 들어올 때마다 toast가 새로 생성되기 때문에
        //결국 cancel하는 toast는 다른 값이 된다. -> 전역변수로 설정하자.
//        Toast toast = Toast.makeText(this, "뒤로가기를 한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT);
        if(bpTime == 0) {
            System.out.println("토스트1 : "+ bpToast);
            ViewGroup group = (ViewGroup) bpToast.getView();
            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            bpToast.show();
            bpTime = System.currentTimeMillis();
        }
        else {
            long sec = System.currentTimeMillis() - bpTime;

            if(sec > 2000) {
                System.out.println("토스트2 : " + bpToast);
                bpToast.show();
                bpTime = System.currentTimeMillis();
            }
            else {
                bpToast.cancel();
                super.onBackPressed();
                finish();
            }
        }
    }
}
