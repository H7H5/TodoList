package com.example.TodoList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.swipe4.R;

import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PageFragment extends Fragment implements View.OnClickListener{

   RecyclerView recyclerView;
   SwipeAdapter adapter;
   static ArrayList<Task> tasks = new ArrayList<>();
   static final String ARGUMENT_PAGE_NUMBER2 = "arg_page_number";
   Button butn1;
   View view;
   int page;
   static PageFragment newInstance(int page) {
      PageFragment pageFragment = new PageFragment();
      Bundle arguments = new Bundle();
      arguments.putInt(ARGUMENT_PAGE_NUMBER2, page);
      pageFragment.setArguments(arguments);
      return pageFragment;

   }

   private void oldCreate(){
      butn1 = (Button)view.findViewById(R.id.btncreate);
      butn1.setOnClickListener(this);
      recyclerView =(RecyclerView) view.findViewById(R.id.swipe_rv);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
              LinearLayoutManager.VERTICAL));
      page = getArguments().getInt(ARGUMENT_PAGE_NUMBER2);
      adapter = new SwipeAdapter(getContext(), tasks , page);
      recyclerView.setAdapter(adapter);
   }

   @Override
   public void onClick(View view) {
      if (view.getId()==R.id.btncreate){
         Task task = new Task("","","","");
         adapter.startCreateDialog(task);
      }
   }
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

   }
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState ) {
      view = inflater.inflate(R.layout.recyclerview, null);
      oldCreate();
      return view;
   }
}
