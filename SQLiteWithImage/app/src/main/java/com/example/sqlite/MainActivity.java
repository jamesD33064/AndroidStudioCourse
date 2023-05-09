package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

  private Button btn_meal;
  private ListView lv_main_meals;

  private DBHandler databaseHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btn_meal = findViewById(R.id.btn_meal);
    lv_main_meals = findViewById(R.id.lv_main_meals);

    databaseHandler = new DBHandler(this);
    databaseHandler.open();

    View.OnClickListener listener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, MealManagementActivity.class);
        startActivity(intent);
      }
    };
    btn_meal.setOnClickListener(listener);
    showAllMeal();
  }

  @Override
  public void onResume() {
    super.onResume();
    showAllMeal();
  }

  private  void showAllMeal() {
    Cursor cursor = databaseHandler.getAllMeals();
    String[] columns = new String[] { "meal_name", "description", "price", "img_name" };
    int[] views = new int[] { R.id.tv_mealname, R.id.tv_mealdescription, R.id.tv_mealprice, R.id.iv_mealimg};

    SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            MainActivity.this,
            R.layout.meal_list_item,
            cursor,
            columns,
            views,
            0
    ) {
      @Override
      public void setViewImage(ImageView v, String value) {
          try {
            FileInputStream imageInputStream = openFileInput(value+".png");
            Bitmap bitmap = BitmapFactory.decodeStream(imageInputStream);
            imageInputStream.close();
            v.setImageBitmap(bitmap);
          } catch (IOException e) {
            v.setImageResource(R.drawable.ic_launcher_foreground);
//            throw new RuntimeException(e);
          }
      }
    };
    lv_main_meals.setAdapter(adapter);
  }

}