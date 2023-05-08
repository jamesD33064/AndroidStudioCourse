package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
    SimpleCursorAdapter adapter = new SimpleCursorAdapter(
      MainActivity.this,
      R.layout.meal_list_item,
      cursor,
      new String[] { "meal_name", "description", "price" },
      new int[] { R.id.tv_mealname, R.id.tv_mealdescription, R.id.tv_mealprice},
      0
    );
    lv_main_meals.setAdapter(adapter);
  }



}