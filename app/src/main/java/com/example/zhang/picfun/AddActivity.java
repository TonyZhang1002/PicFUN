package com.example.zhang.picfun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    private int FileSize = 0;
    String tmpPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "picFun";
    String imgMessageFileName = "mesg.txt";

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

                boolean downLoadTxtFlag = SendFragment.ftpUtils.downLoadFile(tmpPath +"/" + imgMessageFileName,imgMessageFileName);
                if(downLoadTxtFlag) {
                    Log.i("Tony", "Get txt file success");
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                FileSize = sharedPreferences.getInt("FileSize",0);
                MainActivity.writeTxtToFile(SendFragment.fileName,tmpPath,imgMessageFileName);
                editor.putString("t"+Integer.toString(FileSize-1),title.getText().toString());
                MainActivity.writeTxtToFile(title.getText().toString(),tmpPath,imgMessageFileName);
                editor.putString("d"+Integer.toString(FileSize-1),description.getText().toString());
                MainActivity.writeTxtToFile(description.getText().toString(),tmpPath,imgMessageFileName);
                editor.commit();

                boolean uploadTxtFlag = SendFragment.ftpUtils.uploadFile(tmpPath +"/" + imgMessageFileName,imgMessageFileName);
                if(uploadTxtFlag) {
                    Log.i("Tony","Upload txt file success");
                }

                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
