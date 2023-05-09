package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.ETC1;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class MealManagementActivity extends AppCompatActivity {

  private EditText etMealName;
  private EditText etMealDescription;
  private EditText etMealPrice;
  private EditText etChooseImg;
  private Button btnAddMeal;
  private Button btnDelMeal;
  private ListView lvMeals;
  private DBHandler databaseHandler;

  private long id_meal_waittodel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_meal_management);

    etMealName = findViewById(R.id.et_meal_name);
    etMealDescription = findViewById(R.id.et_meal_description);
    etMealPrice = findViewById(R.id.et_meal_price);
    etChooseImg = findViewById(R.id.et_ChooseImg);
    btnAddMeal = findViewById(R.id.btn_addmeal);
    btnDelMeal = findViewById(R.id.btn_delmeal);
    lvMeals = findViewById(R.id.lv_alllmeals);

    databaseHandler = new DBHandler(this);
    databaseHandler.open();


    // -------------- btnAddMeal --------------
    View.OnClickListener addMealonClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String mealName = etMealName.getText().toString();
        String mealDescription = etMealDescription.getText().toString();
        String mealImg = etChooseImg.getText().toString();

        int mealPrice = Integer.parseInt(etMealPrice.getText().toString());
        databaseHandler.addMeal(mealName, mealDescription, mealImg, mealPrice);
        showAllMeal();
      }
    };
    btnAddMeal.setOnClickListener(addMealonClickListener);

    // -------------- btnDelMeal --------------
    View.OnClickListener delMealonClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        delMeal(id_meal_waittodel);
      }
    };
    btnDelMeal.setOnClickListener(delMealonClickListener);

    // -------------- lvMeals --------------
    AdapterView.OnItemClickListener listviewItemClickListener = new AdapterView.OnItemClickListener(){
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        id_meal_waittodel = id;

        Cursor cursor = databaseHandler.getMeal(id);
        cursor.moveToFirst();
        etMealName.setText(cursor.getString(1));
        etMealDescription.setText(cursor.getString(2));;
        etChooseImg.setText(cursor.getString(3));
        etMealPrice.setText(Integer.toString(cursor.getInt(4)));
      }
    };
    lvMeals.setOnItemClickListener(listviewItemClickListener);

    showAllMeal();
  }

  private void delMeal(long id){
    databaseHandler.delMeal(id);
    showAllMeal();
  }

  private void showAllMeal() {
    Cursor cursor = databaseHandler.getAllMeals();
    SimpleCursorAdapter adapter = new SimpleCursorAdapter(
      MealManagementActivity.this,
      android.R.layout.simple_list_item_2,
      cursor,
      new String[] { "meal_name", "price" },
//      new String[] { "meal_name", "img_name" },
      new int[] { android.R.id.text1, android.R.id.text2},
      0
    );
    lvMeals.setAdapter(adapter);
  }



}