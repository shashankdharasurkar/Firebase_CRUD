package com.example.firebasecrud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {

    private ArrayList<CourseRVModel> courseRVModelArrayList;
    private Context context;
    int lastpos = -1;
    private CourseClickInterface courseClickInterface;

    public CourseRVAdapter(ArrayList<CourseRVModel> courseRVModelArrayList, Context context, CourseClickInterface courseClickInterface) {
        this.courseRVModelArrayList = courseRVModelArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CourseRVModel courseRVModel = courseRVModelArrayList.get(position);
        holder.courseNameTv.setText(courseRVModel.getCourseName());
        holder.coursePrictTv.setText("Rs. " + courseRVModel.getCoursePrice());
        Picasso.get().load(courseRVModel.getCourseImg()).into(holder.courseIV);
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseClickInterface.onCourseClick(position);
            }
        });

    }

    private void setAnimation(View view, int position) {
        if (position > lastpos) {

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            view.setAnimation(animation);
            lastpos = position;
        }
    }

    @Override
    public int getItemCount() {
        return courseRVModelArrayList.size();
    }

    public interface CourseClickInterface {
        void onCourseClick(int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView courseNameTv, coursePrictTv;
        ImageView courseIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseNameTv = itemView.findViewById(R.id.idTVCourseName);
            coursePrictTv = itemView.findViewById(R.id.idTVPrice);
            courseIV = itemView.findViewById(R.id.idIVcourseImg);
        }
    }

}
