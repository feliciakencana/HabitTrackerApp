package com.example.newhabittracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newhabittracker.Database.HabitItemViewModel;
import com.example.newhabittracker.Model.HabitList;
import com.example.newhabittracker.R;

import java.util.ArrayList;
import java.util.List;

public class HabitCompletedAdapter extends RecyclerView.Adapter<HabitCompletedAdapter.HabitCompletedHolder> {
    private List<HabitList> habitLists=new ArrayList<>();
    HabitItemViewModel habitItemViewModel;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public HabitCompletedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_stats, parent, false);
        return new HabitCompletedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitCompletedHolder holder, int position) {
        HabitList currentItem= habitLists.get(position);
        holder.RtxtHabitName.setText(currentItem.getHabitName_column());
        holder.RtxtHabitCompletedValue.setText(String.valueOf(currentItem.getTotalCompleted_column()));
    }

    @Override
    public int getItemCount() {
        return habitLists.size();
    }

    public void setHabitCompleted(List<HabitList> habitLists){
        this.habitLists =habitLists;
        notifyDataSetChanged();
    }

    public HabitList getHabitItemPosition(int position) {
        return habitLists.get(position);
    }

    class HabitCompletedHolder extends RecyclerView.ViewHolder {
        private TextView RtxtHabitName, RtxtHabitCompletedValue;

        public HabitCompletedHolder(@NonNull View itemView) {
            super(itemView);
            RtxtHabitName = itemView.findViewById(R.id.habitCompleted_name);
            RtxtHabitCompletedValue = itemView.findViewById(R.id.habitCompleted_value);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= getAdapterPosition();
                    if(listener!=null&&position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(habitLists.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(HabitList habitLists);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

}
