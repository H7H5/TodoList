package com.example.TodoList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

class DBHelper {
    private static DBHelper instance;

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    public void addTask(Context context, Task task){
        SQLiteDatabase database = new DBConector(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConector.KEY_ID, (byte[]) null);
        contentValues.put(DBConector.KEY_NAME, task.getName());
        contentValues.put(DBConector.KEY_DESCRIPTION, task.getDescription());
        contentValues.put(DBConector.KEY_START_DATA, task.getDateStart());
        contentValues.put(DBConector.KEY_FINISH_DATA, task.getDateFinish());
        contentValues.put(DBConector.KEY_END_DATA, task.getDateEnd());
        contentValues.put(DBConector.KEY_COMPLETE, task.getComplete());
        contentValues.put(DBConector.KEY_FINISH_DATA_LONG, task.getDateFinishLong());
        database.insert(DBConector.TABLE_SAVE,null,contentValues);
    }
    public void  updateTask(Context context, Task task){
        String id = String.valueOf(task.getId());
        SQLiteDatabase database = new DBConector(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConector.KEY_NAME, task.getName());
        contentValues.put(DBConector.KEY_DESCRIPTION, task.getDescription());
        contentValues.put(DBConector.KEY_START_DATA, task.getDateStart());
        contentValues.put(DBConector.KEY_FINISH_DATA, task.getDateFinish());
        contentValues.put(DBConector.KEY_END_DATA, task.getDateEnd());
        contentValues.put(DBConector.KEY_COMPLETE, task.getComplete());
        contentValues.put(DBConector.KEY_FINISH_DATA_LONG, task.getDateFinishLong());
        database.update(DBConector.TABLE_SAVE,contentValues,
                DBConector.KEY_ID+"=?",new String[]{id});
    }
    public void deleteTask(Context context, Task task){
        SQLiteDatabase database = new DBConector(context).getWritableDatabase();
        database.delete(DBConector.TABLE_SAVE, DBConector.KEY_ID+"= " + task.getId(),
                null);
    }
    public ArrayList<Task> loadTasks(Context context){
        ArrayList<Task> tasks_load = new ArrayList<>();
        SQLiteDatabase database = new DBConector(context).getWritableDatabase();
        //new DBConector(context).drop(database);
        Cursor cursor = database.query(DBConector.TABLE_SAVE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBConector.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBConector.KEY_NAME);
            int descriptionIndex = cursor.getColumnIndex(DBConector.KEY_DESCRIPTION);
            int startIndex = cursor.getColumnIndex(DBConector.KEY_START_DATA);
            int finishIndex = cursor.getColumnIndex(DBConector.KEY_FINISH_DATA);
            int endIndex = cursor.getColumnIndex(DBConector.KEY_END_DATA);
            int finish_longIndex = cursor.getColumnIndex(DBConector.KEY_FINISH_DATA_LONG);
            int completeIndex = cursor.getColumnIndex(DBConector.KEY_COMPLETE);
            do {
                String name = cursor.getString(nameIndex);
                String description =cursor.getString(descriptionIndex);
                String dateStart = cursor.getString(startIndex);
                String dateFinish = cursor.getString(finishIndex);
                String dateEnd = cursor.getString(endIndex);
                String id = cursor.getString(idIndex);
                String dateFinishLond = cursor.getString(finish_longIndex);
                String complete = cursor.getString(completeIndex);
                Task task = new Task(name,description,dateFinish,dateStart);
                task.setDateEnd(dateEnd);
                task.setDateFinishLong(Long.valueOf(dateFinishLond));
                task.setComplete(Integer.valueOf(complete));
                task.setId(Integer.valueOf(id));
                tasks_load.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks_load;
    }

}
