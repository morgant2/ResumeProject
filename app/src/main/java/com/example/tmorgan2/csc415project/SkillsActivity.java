package com.example.tmorgan2.csc415project;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.tmorgan2.csc415project.models.Skill;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import layout.FragmentSkills;
import layout.FragmentSkillsDetail;


public class SkillsActivity extends AppCompatActivity implements FragmentSkills.SkillSelectedListener, FragmentSkillsDetail.SkillDetailListener {

    List<Skill> skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("light_theme_switch", false)) {
            setTheme(R.style.AppTheme_Dark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView rv = (ListView) findViewById(R.id.skillsListView);
        Gson gson = new Gson();
        String json = loadGSON(getApplicationContext());
        Type type = new TypeToken<List<Skill>>(){}.getType();
        skills = (List<Skill>) gson.fromJson(json, type);

        if(savedInstanceState != null){
            return;
        }
        FragmentSkills fragSkills = new FragmentSkills();
        fragSkills.setArguments(getIntent().getExtras());

        getFragmentManager().beginTransaction().add(R.id.skillFrame, fragSkills).commit();
    }

    private String loadGSON(Context context){
        String json = null;

        try
        {
            InputStream is = context.getAssets().open("skills.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e){
            e.printStackTrace();
        }

        return json;
    }

    @Override
    public void onSkillSelected(int pos) {
        FragmentSkillsDetail detailFrag = (FragmentSkillsDetail) getFragmentManager().findFragmentById(R.id.fragment_skills_detail);

        if (detailFrag != null) {
            detailFrag.resetSkillDetailView(skills.get(pos).getSkillDetails());
        } else {
            FragmentSkillsDetail newFragment = new FragmentSkillsDetail();

            Bundle args = new Bundle();

            args.putString(FragmentSkillsDetail.ARG_PARAM1, skills.get(pos).getSkillDetails());

            newFragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.skillFrame, newFragment);
            transaction.addToBackStack(null);


            transaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
