package com.example.tmorgan2.csc415project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tmorgan2.csc415project.models.Skill;

import java.util.ArrayList;

/**
 * Created by tmorgan2 on 11/21/2017.
 */

public class SkillsDB extends SQLiteOpenHelper {

    private static String CREATE_DATABASE;
    private static final String DATABASE_NAME = "SKILLSDB";
    private static final String DATABASE_TABLE = "Skills";

    public static final int ID_COL = 0;
    public static final int SKILL_COL = 1;
    public static final int SKILL_DETAILS_COL = 2;

    private static final int DATABASE_VERSION = 4;
    private Context context = null;

    public SkillsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        CREATE_DATABASE = "CREATE TABLE " + DATABASE_TABLE + "(" +
                context.getString(R.string.id_field_name) + " integer primary key autoincrement, " +
                context.getString(R.string.skill_field_name) + " text not null, " +
                context.getString(R.string.skill_detail_field_name) + " text not null" +
                ");";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long insert(ContentValues cv)
    {
            SQLiteDatabase db = this.getWritableDatabase();

            return db.insert(DATABASE_TABLE, null, cv);
    }

    public int update(ContentValues cv, String where, String[] whereArgs)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(DATABASE_TABLE, cv, where, whereArgs);
    }

    public int delete(String where, String[] whereArgs)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(DATABASE_TABLE, where, whereArgs);
    }


    public Cursor query(String[] columns, String where, String[] whereArgs, String orderBy)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(DATABASE_TABLE, columns, where, whereArgs, null, null, orderBy);

    }

    public ArrayList<Skill> getSkills()
    {
        Cursor cursor = this.query(null, null, null, null);
        ArrayList<Skill> skills = new ArrayList<Skill>();

        while(cursor.moveToNext())
        {
            skills.add(getSkillFromCursor(cursor));
        }

        if(cursor != null) cursor.close();

        return skills;
    }

    public Skill getSkill(int id)
    {
        Cursor cursor = this.query(null, context.getString(R.string.id_field_name) + "=" + id, null, null);
        Skill skill = null;
        if(cursor.moveToNext())
            skill = getSkillFromCursor(cursor);
        cursor.close();
        return skill;
    }

    private Skill getSkillFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0)
            return null;
        try
        {
            return new Skill(cursor.getInt(ID_COL), cursor.getString(SKILL_COL), cursor.getString(SKILL_DETAILS_COL));

        }
        catch (Exception e)
        {
            return null;
        }
    }

    private Skill createSkill(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.id_field_name)));
        String skill = cursor.getString(cursor.getColumnIndex(context.getString(R.string.skill_field_name)));
        String skillDetail = cursor.getString(cursor.getColumnIndex(context.getString(R.string.skill_detail_field_name)));

        return new Skill(id, skill, skillDetail);
    }

}
