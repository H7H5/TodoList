package com.example.TodoList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


class DBConector extends SQLiteOpenHelper {
   public static final int DATABASE_VERSION = 1;
   public static final String DATABASE_NAME = "saveLesson";
   public static final String TABLE_SAVE = "mySaveTask1";
   public static final String TABLE_SAVE_SELECT = "mySaveSelect";
   public static final String KEY_ID = "_id";
   public static final String KEY_NAME = "name";
   public static final String KEY_DESCRIPTION = "description";
   public static final String KEY_START_DATA = "start";
   public static final String KEY_FINISH_DATA = "finish";
   public static final String KEY_FINISH_DATA_LONG = "finish_long";
   public static final String KEY_END_DATA = "end_data";
   public static final String KEY_COMPLETE = "complete";

   public DBConector(@Nullable Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }

   @Override
   public void onCreate(SQLiteDatabase sqLiteDatabase) {
      sqLiteDatabase.execSQL("create table " + TABLE_SAVE +
              " (" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL, "
              + KEY_NAME + " text, "
              + KEY_DESCRIPTION + " text, "
              + KEY_START_DATA + " text,"
              + KEY_FINISH_DATA + " text, "
              + KEY_END_DATA + " text, "
              + KEY_FINISH_DATA_LONG + " text, "
              + KEY_COMPLETE + " text);");

   }
   public void drop(SQLiteDatabase sqLiteDatabase){
      sqLiteDatabase.execSQL("drop table if exists " + TABLE_SAVE);
      onCreate(sqLiteDatabase);
   }
   @Override
   public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
      sqLiteDatabase.execSQL("drop table if exists " + TABLE_SAVE);
      onCreate(sqLiteDatabase);
      Log.d("yyyy", "0000");
   }
}
