package org.androidtown.memoryexcercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class long_term_main extends AppCompatActivity {

    ImageButton zoo_long_term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_term_main);

        zoo_long_term = (ImageButton) findViewById(R.id.zoo_long_term);

        zoo_long_term.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), LevelSelect.class);
                intent.putExtra("gameType",6);
                startActivity(intent);
            }
        });
    }
}