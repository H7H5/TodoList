package com.example.TodoList;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.swipe4.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder>{
    Dialog dialog;
    DBConector dbConector;
    public final int page;
    private final Context context;
    public ArrayList<Task> tasks;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public SwipeAdapter(Context context, ArrayList<Task> tasks, int page) {
        this.context = context;
        this.tasks = tasks;
        this.page = page;
        dbConector = new DBConector(context);
        update();
    }
    public void setTasks(ArrayList<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }
    public void update(){
        createListTasks();
        notifyDataSetChanged();
    }
    public void createListTasks() {
        tasks = loadTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getComplete()!=page){
                tasks.remove(i);
                i--;
            }
        }
        setTasks(tasks);
    }
    private ArrayList<Task> loadTasks(){
        return DBHelper.getInstance().loadTasks(context);
    }
    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(page == 0){
            view = LayoutInflater.from(context).inflate(R.layout.curent,parent,false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.complete,parent,false);
        }
        return new SwipeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SwipeViewHolder holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(tasks.get(position).getName()));
        viewBinderHelper.closeLayout(String.valueOf(tasks.get(position).getName()));
        holder.bindData(tasks.get(position));
    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private void startDialog(Task task){
        dialog = new Dialog(context);
        ReadDialog readDialog = new ReadDialog(dialog,this);
        readDialog.startDialod(task);
    }
    public void startEditDialog(Task task){
        dialog = new Dialog(context);
        EditDialog editDialog = new EditDialog(dialog,this);
        editDialog.startEditDialod(task);
    }
    public void startCreateDialog(Task task){
        dialog = new Dialog(context);
        CreateDialog createDialog = new CreateDialog(dialog,this);
        createDialog.startCreateDialod(task);
    }
    class SwipeViewHolder extends RecyclerView.ViewHolder{
        public Task task;
        private TextView textView;
        private TextView textEdit;
        private TextView textDelete;
        private TextView textComplete;
        private SwipeRevealLayout swipeRevealLayout;

        public SwipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            swipeRevealLayout = itemView.findViewById(R.id.swipelayout);
            textDelete = itemView.findViewById(R.id.txtDelete);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startDialog(task);

                }
            });
            textDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tasks.remove(task);
                    DBHelper.getInstance().deleteTask(context,task);
                    update();
                }
            });
            if (page==0){
                textEdit = itemView.findViewById(R.id.txtEdit);
                textComplete = itemView.findViewById(R.id.txtComplete);
                textComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        task.setComplete(1);
                        Date currentDate = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("d.M.yyyy", Locale.getDefault());
                        String dateText = dateFormat.format(currentDate);
                        task.setDateEnd(dateText);
                        DBHelper.getInstance().updateTask(context,task);
                        MainActivity.getInstance().pageFragments.get(1).adapter.update();
                        tasks.remove(task);
                        update();
                    }
                });
                textEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startEditDialog(task);
                    }
                });
            }
        }
        void  bindData(Task task){
            this.task = task;
            textView.setText(task.getName());
        }
    }
}
