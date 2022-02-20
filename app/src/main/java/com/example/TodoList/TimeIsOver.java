package com.example.TodoList;

import android.content.Context;
import java.util.ArrayList;

class TimeIsOver {
   Context context;
   ArrayList<Task> tasks= new ArrayList<>();
   boolean flag = false;
   public TimeIsOver(Context context) {
      this.context = context;
   }

   public boolean timeCheck(){
      tasks =  DBHelper.getInstance().loadTasks(context);
      if (tasks.size()==0){
         return false;
      }
      for (int i = 0; i < tasks.size(); i++) {
         if (tasks.get(i).getComplete()==0){
            if (tasks.get(i).getDateFinishLong() < System.currentTimeMillis()){
               flag = true;
               return true;
            }
         }
      }
      return false;
   }
}
