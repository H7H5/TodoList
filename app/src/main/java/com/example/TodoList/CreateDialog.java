package com.example.TodoList;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swipe4.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

class CreateDialog extends ParentDialog{
   public CreateDialog(Dialog dialog, SwipeAdapter swipeAdapter) {
      super(dialog,swipeAdapter);
   }
   public void startCreateDialod(Task task1){
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      dialog.setContentView(R.layout.createdialog);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialog.setCancelable(false);
      TextView btnclose = (TextView)dialog.findViewById(R.id.btnclose);
      EditText textName = (EditText)dialog.findViewById(R.id.name);
      EditText textDescription = (EditText)dialog.findViewById(R.id.textdescription);
      TextView textDateFinish = (TextView)dialog.findViewById(R.id.dateFinish);
      Button btnCreate = (Button) dialog.findViewById(R.id.btncreate);
      textName.setText(task1.getName());
      textDescription.setText(task1.getDescription());
      textDateFinish.setText("select date");
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
                  month++;
                  String date = makeDataString(day, month, year);
                  textDateFinish.setText(date);
               }
            };
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH) ;
            int d = calendar.get(Calendar.DAY_OF_MONTH);


            int style = AlertDialog.THEME_HOLO_LIGHT;
            DatePickerDialog datePickerDialog = new DatePickerDialog(dialog.getContext(), style, dateSetListener,y,m,d);
            datePickerDialog.show();
         }

         private String makeDataString(int day, int month, int year) {
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            Date date = calendar.getTime();
            long finishData = date.getTime();
            Log.d("yyyy", String.valueOf(finishData));
            task1.setDateFinishLong(finishData);
            return day + " " + month + " " + year;
         }
      });

      btnCreate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("d.M.yyyy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);
            task1.setDateStart(dateText);
            dialog.dismiss();
            Toast.makeText(dialog.getContext(), textDescription.getText(), Toast.LENGTH_SHORT).show();
            task1.setName(textName.getText().toString());
            task1.setDescription(textDescription.getText().toString());
            task1.setDateFinish(textDateFinish.getText().toString());
            swipeAdapter.tasks.add(task1);
            DBHelper.getInstance().addTask(dialog.getContext(),task1);
            swipeAdapter.update();
         }
      });
   }
}
