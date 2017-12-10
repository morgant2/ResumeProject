package com.example.tmorgan2.csc415project.models;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by tmorgan2 on 10/24/2017.
 */

public class Skill  {
    private String skill;
    private String skillDetails;

    public int get_id() {
        return _id;
    }

    private int _id;

    public Skill(int id, String skill, String skillDetails)
    {
        this._id = id;
        this.skill = skill;
        this.skillDetails = skillDetails;
    }

    public String getSkill() {
        return skill;
    }

    public String getSkillDetails() {
        return skillDetails;
    }

    @Override
    public String toString() {
        return skill;
    }


    public String loadGSON(Context context){
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
}
