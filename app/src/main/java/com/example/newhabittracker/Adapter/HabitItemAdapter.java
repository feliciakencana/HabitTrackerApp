package com.example.newhabittracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newhabittracker.Model.HabitItem;
import com.example.newhabittracker.R;

import java.util.ArrayList;
import java.util.List;

public class HabitItemAdapter extends RecyclerView.Adapter<HabitItemAdapter.HabitItemHolder> {
    private List<HabitItem> habitItem = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public HabitItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_item, parent, false);
        return new HabitItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitItemHolder holder, int position) {
        HabitItem currentItem = habitItem.get(position);
        holder.RtxtHabitName.setText(currentItem.getHabitName_column());
        if(currentItem.getTargetedHour_column()!=0){
            holder.RtxtTargetedGoal.setText(String.valueOf(currentItem.getTargetedHour_column()));
            holder.RtxtReachedValue.setText(String.valueOf(currentItem.getIsTimeComplete_column()));
        }else if(currentItem.getValueUnit_column()!=null){
            holder.RtxtTargetedGoal.setText(String.valueOf(currentItem.getTargetedValue_column()));
            holder.RtxtReachedValue.setText(String.valueOf(currentItem.getGoalReached_column()));
        }
        if(currentItem.getValueUnit_column()!=null){
            holder.RtxtValueUnit.setText(currentItem.getValueUnit_column());
        }else{
            holder.RtxtValueUnit.setText("hours");
        }

        if(currentItem.getIsTimeComplete_column()==0&&currentItem.getIsDuration_column()==1){
            holder.RimgIsReminder.setVisibility(View.VISIBLE);
            holder.RimgDone.setVisibility(View.GONE);
        }else if((currentItem.getIsTimeComplete_column()>=currentItem.getTargetedHour_column()||currentItem.getTotalCompleted_column()>0)
        &&currentItem.getIsDuration_column()==1){
            holder.RimgIsReminder.setVisibility(View.GONE);
            holder.RimgDone.setVisibility(View.VISIBLE);
        }
        if(currentItem.getGoalReached_column()>=currentItem.getTargetedValue_column()&&currentItem.getIsDuration_column()==0){
            holder.RimgIsReminder.setVisibility(View.GONE);
            holder.RimgDone.setVisibility(View.VISIBLE);
        }else if(currentItem.getIsDuration_column()==0){
            holder.RimgIsReminder.setVisibility(View.VISIBLE);
            holder.RimgDone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return habitItem.size();
    }

    public void setHabitItem(List<HabitItem>habitItem){
        this.habitItem=habitItem;
        notifyDataSetChanged();
    }

    public HabitItem getHabitItemPosition(int position) {
        return habitItem.get(position);
    }

    class HabitItemHolder extends RecyclerView.ViewHolder {
        private TextView RtxtHabitName, RtxtTargetedGoal, RtxtValueUnit, RtxtDay, RtxtReachedValue;
        private ImageView RimgIsReminder,RimgDone;

        public HabitItemHolder(@NonNull View itemView) {
            super(itemView);
            RtxtHabitName = itemView.findViewById(R.id.textView_displayHabitName);
            RtxtTargetedGoal = itemView.findViewById(R.id.textView_disp_targetedGoal);
            RimgIsReminder = itemView.findViewById(R.id.imgview_reminder);
            RtxtValueUnit = itemView.findViewById(R.id.textView_displayValueUnit);
            RtxtReachedValue = itemView.findViewById(R.id.textView_display_reachedGoal);
            RimgDone=itemView.findViewById(R.id.imgview_done);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= getAdapterPosition();
                    if(listener!=null&&position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(habitItem.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(HabitItem habitItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
