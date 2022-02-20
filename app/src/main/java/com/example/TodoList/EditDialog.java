package com.example.TodoList;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swipe4.R;

import java.util.Calendar;

class EditDialog extends ParentDialog{
   public EditDialog(Dialog dialog, SwipeAdapter swipeAdapter) {
      super(dialog,swipeAdapter);
   }
   public void startEditDialod(Task task){
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      dialog.setContentView(R.layout.editdialog);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialog.setCancelable(false);

      TextView btnclose = (TextView)dialog.findViewById(R.id.btnclose);
      EditText textName = (EditText)dialog.findViewById(R.id.name);
      EditText textDescription = (EditText)dialog.findViewById(R.id.textdescription);
      TextView textDateStart = (TextView)dialog.findViewById(R.id.dateStart);
      TextView textDateFinish = (TextView)dialog.findViewById(R.id.dateFinish);
      TextView textDateEnd = (TextView)dialog.findViewById(R.id.dateEnd);

      Button btnEdit = (Button) dialog.findViewById(R.id.btnedit);

      textName.setText(task.getName());
      textDescription.setText(task.getDescription());
      textDateStart.setText(task.getDateStart());
      textDateFinish.setText(task.getDateFinish());
      textDateEnd.setText(task.getDateEnd());
      dialog.show();
      btnclose.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            dialog.dismiss();
         }
      });
      textDateFinish.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
               @Override
               public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                  month = month + 1;
                  String date = makeDataString(day, month, year);
                  textDateFinish.setText(date);
               }


            };
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            int style = AlertDialog.THEME_HOLO_LIGHT;
            DatePickerDialog datePickerDialog = new DatePickerDialog(dialog.getContext(), style, dateSetListener,y,m,d);
            datePickerDialog.show();
         }

         private String makeDataString(int day, int month, int year) {

            return day + " " + month + " " + year;
         }
      });
      btnEdit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            dialog.dismiss();
            Toast.makeText(dialog.getContext(), textDescription.getText(), Toast.LENGTH_SHORT).show();
            task.setName(textName.getText().toString());
            task.setDescription(textDescription.getText().toString());
            task.setDateFinish(textDateFinish.getText().toString());
            DBHelper.getInstance().updateTask(dialog.getContext(),task);
            swipeAdapter.update();
         }
      });
   }
}
