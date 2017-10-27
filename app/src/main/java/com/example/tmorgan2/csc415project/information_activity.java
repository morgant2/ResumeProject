package com.example.tmorgan2.csc415project;

import android.content.Intent;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;


public class information_activity extends AppCompatActivity {

    private int[] resArray;
    private int currentImage = 0;
    private Button nextButton;
    private Button previousButton;
    private TextView bio;
    private final int NUM_OF_IMAGES = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("light_theme_switch", false)) {
            setTheme(R.style.AppTheme_Dark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_activity);
        setResourceArray();

        bio = (TextView) findViewById(R.id.textView);
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);

        setButtonClickEvents();
        setInitialButtonState();

        bio.setText(R.string.bio);
    }

    private void setInitialButtonState() {
        previousButton.setEnabled(false);
    }

    private void setButtonClickEvents() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextButtonClick();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPreviousButtonClick();
            }
        });
    }

    public void setResourceArray()
    {
        resArray = new int[NUM_OF_IMAGES];
        resArray[0] = R.drawable.resume_icon0_200;
        resArray[1] = R.drawable.resume_icon1_200;
        resArray[2] = R.drawable.resume_icon2_200;

    }
    public void onNextButtonClick()
    {
        if(!isResourceArrayAtEnd())
        {
            currentImage++;
            setImage();
        }

        setButtonEnabledState();
    }

    public void onPreviousButtonClick()
    {
        if(!isResourceArrayAtStart())
        {
            currentImage--;
            setImage();
        }
        setButtonEnabledState();
    }

    private void setButtonEnabledState()
    {
        if(isResourceArrayAtEnd())
        {
            nextButton.setEnabled(false);
        }
        else if(isResourceArrayAtStart())
        {
            previousButton.setEnabled(false);
        }
        else
        {
            nextButton.setEnabled(true);
            previousButton.setEnabled(true);
        }
    }
    private boolean isResourceArrayAtEnd() { return currentImage == resArray.length - 1; }
    private boolean isResourceArrayAtStart() { return currentImage == 0; }
    private void setImage()
    {
        new Thread(new Runnable() {
            public void run() {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                image.setImageResource(resArray[currentImage]);
            }
        }).start();

//        ImageView image = (ImageView) findViewById(R.id.imageView);
//        image.setImageResource(resArray[currentImage]);
    }

    public void btnSettings_onClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void btnSkills_onClick(View view) {
        Intent intent = new Intent(this, SkillsActivity.class);
        startActivity(intent);
    }
}
