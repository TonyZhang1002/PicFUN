package com.example.zhang.picfun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    private int FileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText title = (EditText) findViewById(R.id.addEditText);
        final EditText description = (EditText) findViewById(R.id.desEditText);
        Button confirm = (Button) findViewById(R.id.confirm_button);

        final SharedPreferences sharedPreferences = getSharedPreferences("myImgNameList", MODE_PRIVATE);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                FileSize = sharedPreferences.getInt("FileSize",0);
                editor.putString("t"+Integer.toString(FileSize-1),title.getText().toString());
                editor.putString("d"+Integer.toString(FileSize-1),description.getText().toString());
                editor.commit();

                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
