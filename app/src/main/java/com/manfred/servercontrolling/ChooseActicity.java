package com.manfred.servercontrolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ChooseActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_acticity);
        final ImageButton procButton = findViewById(R.id.imageButtonProc);
        final ImageButton filesButton = findViewById(R.id.imageButtonFiles);
        procButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseActicity.this, ProcessesActivity.class);
                startActivity(intent);

            }
        });
        filesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseActicity.this, FilesActivity.class);
                startActivity(intent);

            }
        });
    }
}
