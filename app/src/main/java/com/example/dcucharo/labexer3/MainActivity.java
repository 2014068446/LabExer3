package com.example.dcucharo.labexer3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText etData, etFilename;
    SharedPreferences preferences;
    FileOutputStream fos = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etData = findViewById(R.id.et_data);
        etFilename = findViewById(R.id.et_filename);
        preferences = getSharedPreferences(etFilename.getText().toString(), Context.MODE_PRIVATE);
    }
    public void savePreference(View view) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Filename",etFilename.getText().toString());
        editor.putString("Data",etData.getText().toString());
        editor.commit();

        Toast.makeText(this,"Saved in Preferences",Toast.LENGTH_SHORT).show();
    }
    public void saveIntStorage(View view) {
        String message =  etData.getText().toString();
        try {
            fos = openFileOutput(etFilename.getText().toString(), Context.MODE_PRIVATE);
            fos.write(message.getBytes());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this,"Saved in Internal Storage.", Toast.LENGTH_SHORT).show();
    }
    public void saveInternalCache(View view) {
        File folder = getCacheDir();
        File file = new File(folder,etFilename.getText().toString());
        writeData(etData.getText().toString(), file);
        Toast.makeText(this, "Successfully written to Internal Cache", Toast.LENGTH_SHORT).show();
    }
    public void saveExternalCache(View view) {
        File folder = getExternalCacheDir();
        File file = new File(folder,etFilename.getText().toString());
        writeData(etData.getText().toString(), file);
        Toast.makeText(this, "Successfully written to External Cache", Toast.LENGTH_SHORT).show();
    }
    public void saveExternalStorage(View view) {
        File folder = getExternalFilesDir("Temp");
        File file = new File(folder,etFilename.getText().toString());
        writeData(etData.getText().toString(), file);
        Toast.makeText(this, "Successfully written to External Storage", Toast.LENGTH_SHORT).show();
    }
    public void saveExternalPublic (View view) {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder,etFilename.getText().toString());
        writeData(etData.getText().toString(), file);
        Toast.makeText(this, "Successfully written to External Public", Toast.LENGTH_SHORT).show();
    }
    public void writeData(String message,File filename){
        try{
            fos = new FileOutputStream(filename);
            fos.write(message.getBytes());
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void callSecondActivity(View view) {
        Intent intent = new Intent(this,Secondary.class);
        startActivity(intent);
    }
}
