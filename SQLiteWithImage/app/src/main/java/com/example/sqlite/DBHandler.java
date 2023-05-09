package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DBHandler {
  private AppCompatActivity acticity;
  private SQLiteDatabase database;

  private static final String DB_Name = "fcu_breakfast.db";
  private static final String Create_Meal_Table = "CREATE TABLE IF NOT EXISTS Meals (" +
    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
    "meal_name TEXT NOT NULL, " +
    "description TEXT, " +
    "img_name TEXT, " +
    "price INTEGER NOT NULL" +
    ")";

  public DBHandler(AppCompatActivity acticity){
    this.acticity = acticity;
  }

  public void open(){
    database = acticity.openOrCreateDatabase(DB_Name, Context.MODE_PRIVATE, null);
    database.execSQL(Create_Meal_Table);
  }

  public void addMeal(String meal_name, String description, String img_name, int price){
    ContentValues values = new ContentValues();
    values.put("meal_name", meal_name);
    values.put("description", description);;
    values.put("img_name", img_name);
    values.put("price", price);
    database.insert("Meals", null, values);
  }

  public void delMeal(long id){
    database.delete("Meals", "_id=" + id, null);
  }

  public Cursor getMeal(long id) {
    Cursor cursor = database.rawQuery("SELECT * FROM Meals WHERE _id=?", new String[]{String.valueOf(id)});

    return cursor;
  }

  public Cursor getAllMeals(){
    Cursor cursor = database.rawQuery("SELECT * FROM Meals", null);
//    Toast.makeText(acticity, cursor.getCount()+"is added", Toast.LENGTH_SHORT).show();

    return cursor;
  }

}
