package com.example.ioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private TextView tv_filestring;
    private ImageView iv_pokemon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    tv_filestring = findViewById(R.id.tv_filestring);
    iv_pokemon = findViewById(R.id.iv_pokemon);

//    readimg2ImageView();

//    readtxt2TextView();
//
//    writetxt();

  }

  private void readimg2ImageView() {
    try {
      FileInputStream imageInputStream = openFileInput("pokemon.png");
      Bitmap bitmap = BitmapFactory.decodeStream(imageInputStream);
      imageInputStream.close();
      iv_pokemon.setImageBitmap(bitmap);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void readtxt2TextView() {
    File prjDir = this.getFilesDir();
    File inputFile = new File(prjDir, "Hello.txt");

    try {
      FileInputStream fis = new FileInputStream(inputFile);
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);

      StringBuilder sb = new StringBuilder();
      String line = "";
      while((line = br.readLine()) != null ) {
        sb.append(line + "\n");
      }
      String output = sb.toString();
      tv_filestring.setText(output);

      fis.close();
      isr.close();
      br.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void writetxt() {
    File prjDir = this.getFilesDir();
    File outputDir = new File(prjDir, "Hello.txt");

    try {
      FileOutputStream fos = new FileOutputStream(outputDir);
      String outString = "Hello World\nHello FCU";
      byte[] bytes = outString.getBytes();
      fos.write(bytes);
      fos.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}












































