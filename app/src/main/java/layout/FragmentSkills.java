package layout;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tmorgan2.csc415project.R;
import com.example.tmorgan2.csc415project.models.Skill;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class FragmentSkills extends Fragment {

    private SkillSelectedListener mListener;

    public FragmentSkills() {
    }

    public static FragmentSkills newInstance(String param1, String param2) {
        FragmentSkills fragment = new FragmentSkills();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        Gson gson = new Gson();
        String jsonOutput = loadGSON(getContext());
        Type listType = new TypeToken<List<Skill>>(){}.getType();
        List<Skill> skills = (List<Skill>) gson.fromJson(jsonOutput, listType);

        ArrayAdapter adapter =new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, skills);

        ListView skillsListView = (ListView) view.findViewById(R.id.skillsListView);
        skillsListView.setAdapter(adapter);
        skillsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onSkillSelected(position);
            }
        });

        return view;
    }

    public void onButtonPressed(int pos) {
        if (mListener != null) {
            mListener.onSkillSelected(pos);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SkillSelectedListener) {
            mListener = (SkillSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SkillSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface SkillSelectedListener {
        void onSkillSelected(int pos);
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
}
