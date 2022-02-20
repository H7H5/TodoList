package com.example.TodoList;

import android.app.Dialog;

abstract class ParentDialog {
   protected Dialog dialog;
   protected SwipeAdapter swipeAdapter;
   public ParentDialog(Dialog dialog, SwipeAdapter swipeAdapter) {
      this.swipeAdapter = swipeAdapter;
      this.dialog = dialog;
   }
}
