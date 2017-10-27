package com.example.tmorgan2.csc415project.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tmorgan2.csc415project.R;
import com.example.tmorgan2.csc415project.models.Skill;

import java.util.List;

/**
 * Created by tmorgan2 on 10/24/2017.
 */

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.MyViewHolder> {

    private Context context;
    private List<Skill> skills;
    private LayoutInflater inflater;
    public SkillAdapter(List<Skill> skills ) { this.skills = skills;}

    public SkillAdapter(Context context, List<Skill> skills)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.skills = skills;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View skillView = inflater.inflate(R.layout.fragment_skills, parent, false);

        return new MyViewHolder(skillView);
    };

    @Override
    public void onBindViewHolder(SkillAdapter.MyViewHolder holder, int position) {
//        holder.skill.setText(skills);
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView skill;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            skill = (TextView) itemView.findViewById(R.id.skillsListView);
        }
    }
}
