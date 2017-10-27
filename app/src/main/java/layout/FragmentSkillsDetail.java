package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tmorgan2.csc415project.R;

public class FragmentSkillsDetail extends Fragment {
    public static final String ARG_PARAM1 = "param1";
    private TextView tvSkillDetail;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String details;

    private SkillDetailListener mListener;

    public FragmentSkillsDetail() {
    }

    public static FragmentSkillsDetail newInstance(String param1, String param2) {
        FragmentSkillsDetail fragment = new FragmentSkillsDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            details = getArguments().getString(ARG_PARAM1);;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skills_detail, container, false);

        tvSkillDetail = (TextView) view.findViewById(R.id.skillDetailText);
        tvSkillDetail.setText(details);
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
