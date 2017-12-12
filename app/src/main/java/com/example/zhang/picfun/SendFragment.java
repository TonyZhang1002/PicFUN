package com.example.zhang.picfun;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.R.attr.path;
import static android.R.attr.start;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/*
   Here is the send fragment class
   @author zhang
*/
public class SendFragment extends Fragment {

    private View view;
    private Button choose;
    private Button shoot;

    public static FTPutils ftpUtils = null;
    static boolean iniFTPflag = false;

    private static final int GALLERY_CODE = 1;

    Context applicationContext = MainActivity.getContextOfApplication();
    SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("myImgNameList", MODE_PRIVATE);
    private int fileSize = 0;
    String imgMessageFileName = "mesg.txt";
    public static String fileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_send, null);
        choose = (Button) view.findViewById(R.id.choosePicButton);

        if(!iniFTPflag) {
            InitFTPServerSetting();
        }

        return view;
    }

    public static void InitFTPServerSetting() {
        ftpUtils = FTPutils.getInstance();
        iniFTPflag = ftpUtils.initFTPSetting("165.227.1.206", 21, "AdminTony", "Www13826568574co");
        if(iniFTPflag)
            Log.i("Tony","Connect FTP server success");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //When user choose to select photo from their repo
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromGallery();
            }
        });
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GALLERY_CODE:
                if(data == null) {
                    return;
                }else{
                    Uri uri = data.getData();
                    saveFile(uri);
                    Intent intent = new Intent(getActivity(),AddActivity.class);
                    startActivity(intent);
                }
                break;

            default:
                break;
        }
    }


    /*
    save the file
    @param uri
    @return
     */
    private void saveFile(Uri uri) {
        InputStream is;
        try {
            //Uri ----> InputStream
            is = applicationContext.getContentResolver().openInputStream(uri);
            //InputStream ----> Bitmap
            Bitmap bm = BitmapFactory.decodeStream(is);
            is.close();
            saveBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write bitmap into a file in SD card, and return the Uri
     * @param bm
     * @return
     */
    private void saveBitmap(Bitmap bm) {

        //create a new directory ot store the picture
        String tmpPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "picFun";
        File tmpDir = new File(tmpPath);
        if (!tmpDir.exists()){
            tmpDir.mkdirs();
        }

        //create a new file ot store the picture
        fileName = UUID.randomUUID().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        fileSize = sharedPreferences.getInt("FileSize",0);
        editor.putString("p"+Integer.toString(fileSize),fileName);
        fileSize++;
        editor.putInt("FileSize",fileSize);
        editor.commit();

        File img = new File(tmpDir, fileName + ".jpg");
        try {
            //open the file output stream
            FileOutputStream fos = new FileOutputStream(img);
            //compress the file to the bitmap
            bm.compress(Bitmap.CompressFormat.JPEG, 70, fos);
            //refresh
            fos.flush();
            //close
            fos.close();
            //get the file's uri
            Uri uri = Uri.fromFile(img);
            //send broadcast
            applicationContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            //upload to the FTP server
            boolean uploadFlag = ftpUtils.uploadFile(tmpPath +"/" + fileName + ".jpg",fileName + ".jpg");
            HomeFragment.refreshFlag = true;
            if(uploadFlag) {
                Log.i("Tony","Upload FTP server success");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
