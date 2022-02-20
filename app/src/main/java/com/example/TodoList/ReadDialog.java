package com.example.TodoList;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.swipe4.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class ReadDialog extends ParentDialog{
   public ReadDialog(Dialog dialog, SwipeAdapter swipeAdapter) {
      super(dialog,swipeAdapter);
   }

   public void startDialod(Task task){
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      if (swipeAdapter.page==0){
         dialog.setContentView(R.layout.readdialog);
      }else {
         dialog.setContentView(R.layout.deletedialog);
      }
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialog.setCancelable(false);

      TextView btnclose = (TextView)dialog.findViewById(R.id.btnclose);
      TextView textName = (TextView)dialog.findViewById(R.id.name);
      TextView textDescription = (TextView)dialog.findViewById(R.id.textdescription);
      TextView textDateStart = (TextView)dialog.findViewById(R.id.dateStart);
      TextView textDateFinish = (TextView)dialog.findViewById(R.id.dateFinish);
      TextView textDateEnd = (TextView)dialog.findViewById(R.id.dateEnd);
      if(swipeAdapter.page==0){
         Button btnComplete = (Button) dialog.findViewById(R.id.btncomplete);
         Button btnEdit = (Button) dialog.findViewById(R.id.btnedit);
         btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
               task.setComplete(1);
               Date currentDate = new Date();
               DateFormat dateFormat = new SimpleDateFormat("d.M.yyyy", Locale.getDefault());
               String dateText = dateFormat.format(currentDate);
               task.setDateEnd(dateText);
               DBHelper.getInstance().updateTask(dialog.getContext(),task);
               MainActivity.getInstance().pageFragments.get(1).adapter.update();
               swipeAdapter.update();
            }
         });
         btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
               swipeAdapter.startEditDialog(task);

            }
         });
      }
      Button btnDelete = (Button) dialog.findViewById(R.id.btndelete);
      textName.setText(task.getName());
      textDescription.setText(task.getDescription());
      textDateStart.setText((CharSequence) task.getDateStart());
      textDateFinish.setText(task.getDateFinish());
      textDateEnd.setText(task.getDateFinishLong().toString());
      btnclose.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            dialog.dismiss();
         }
      });
      btnDelete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            dialog.dismiss();
            swipeAdapter.tasks.remove(task);
            DBHelper.getInstance().deleteTask(dialog.getContext(),task);
            swipeAdapter.update();
         }
      });
      dialog.show();
   }
}
