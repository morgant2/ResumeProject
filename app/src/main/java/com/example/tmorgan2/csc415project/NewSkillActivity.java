package com.example.tmorgan2.csc415project;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tmorgan2.csc415project.models.Skill;

public class NewSkillActivity extends AppCompatActivity {

    private int skillID;
    private EditText etSkillDetails;
    private EditText etSkill;
    private SkillsDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_skill);

        Button btnCreateSkill = (Button) findViewById(R.id.btnCreateSkill);
        etSkill = (EditText) findViewById(R.id.etSkillName);
        etSkillDetails = (EditText) findViewById(R.id.etSkillDetails);
        db = new SkillsDB(getApplicationContext());

        Intent intent = getIntent();

        skillID = intent.getIntExtra(getString(R.string.id_field_name), -1);
        if(skillID >= 0)
        {
            skillID = intent.getIntExtra(getString(R.string.id_field_name), -1);
            Skill skill = db.getSkill(skillID);

            etSkill.setText(skill.getSkill());
            etSkillDetails.setText(skill.getSkillDetails());
            btnCreateSkill.setText("Save Skill");
        }

        btnCreateSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues cv = new ContentValues();
                cv.put(getString(R.string.skill_field_name), etSkill.getText().toString());
                cv.put(getString(R.string.skill_detail_field_name), etSkillDetails.getText().toString());

                if(skillID >= 0)
                {
                    db.update(cv, getString(R.string.id_field_name) + "=" + skillID, null);
                }
                else
                {
                    db.insert(cv);
                }
                Intent intent = new Intent(NewSkillActivity.this, SkillsActivity.class);
                startActivity(intent);
            }
        });
    }
}
