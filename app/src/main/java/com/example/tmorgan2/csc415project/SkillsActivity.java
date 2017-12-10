package com.example.tmorgan2.csc415project;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.tmorgan2.csc415project.models.Skill;

import java.util.List;

import layout.FragmentSkills;
import layout.FragmentSkillsDetail;


public class SkillsActivity extends AppCompatActivity implements FragmentSkills.SkillSelectedListener, FragmentSkillsDetail.SkillDetailListener {

    List<Skill> skills;
    SkillsDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("light_theme_switch", false)) {
            setTheme(R.style.AppTheme_Dark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new SkillsDB(getApplicationContext());


        skills = db.getSkills();

        if(savedInstanceState != null){
            return;
        }
        FragmentSkills fragSkills = new FragmentSkills();
        fragSkills.setArguments(getIntent().getExtras());

        getFragmentManager().beginTransaction().add(R.id.skillFrame, fragSkills).commit();

        FloatingActionButton fabNewSkill = (FloatingActionButton) findViewById(R.id.fabNewSkill);
        fabNewSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SkillsActivity.this, NewSkillActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onSkillSelected(int pos) {
        FragmentSkillsDetail detailFrag = (FragmentSkillsDetail) getFragmentManager().findFragmentById(R.id.fragment_skills_detail);

        if (detailFrag != null) {
            detailFrag.resetSkillDetailView(skills.get(pos).getSkillDetails());
        } else {
            FragmentSkillsDetail newFragment = new FragmentSkillsDetail();
            Bundle args = new Bundle();

            args.putString(FragmentSkillsDetail.SKILL_DETAILS, skills.get(pos).getSkillDetails());
            args.putInt(FragmentSkillsDetail.SKILL_ID, skills.get(pos).get_id());
            newFragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.skillFrame, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        int test = 0;

    }
}
