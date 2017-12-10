package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tmorgan2.csc415project.NewSkillActivity;
import com.example.tmorgan2.csc415project.R;
import com.example.tmorgan2.csc415project.SkillsActivity;
import com.example.tmorgan2.csc415project.SkillsDB;

public class FragmentSkillsDetail extends Fragment {
    public static final String SKILL_DETAILS = "param1";
    public static final String SKILL_ID = "skill_id";
    private TextView tvSkillDetail;
    private Button btnEditSkill;
    private Button btnDeleteSkill;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String details;
    private int skillID;

    private SkillDetailListener mListener;

    public FragmentSkillsDetail() {
    }

    public static FragmentSkillsDetail newInstance(String param1, String param2) {
        FragmentSkillsDetail fragment = new FragmentSkillsDetail();
        Bundle args = new Bundle();
        args.putString(SKILL_DETAILS, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            details = getArguments().getString(SKILL_DETAILS);
            skillID = getArguments().getInt(SKILL_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skills_detail, container, false);

        tvSkillDetail = (TextView) view.findViewById(R.id.skillDetailText);
        tvSkillDetail.setText(details);

        btnEditSkill = (Button) view.findViewById(R.id.btnEditSkill);
        btnDeleteSkill = (Button) view.findViewById(R.id.btnDeleteSkill);

        btnEditSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewSkillActivity.class);
                intent.putExtra(getString(R.string.id_field_name), skillID);
                startActivity(intent);
            }
        });

        btnDeleteSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkillsDB db = new SkillsDB(getContext());
                db.delete(getString(R.string.id_field_name) + "=" + skillID, null);
                Intent intent = new Intent(getActivity(), SkillsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SkillDetailListener) {
            mListener = (SkillDetailListener) context;
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

    public void resetSkillDetailView(String details) {
        tvSkillDetail.setText(details);
    }

    public interface SkillDetailListener {
        void onFragmentInteraction(Uri uri);
    }
}
