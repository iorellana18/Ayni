package cl.citiaps.coordinaciondevoluntarios.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MissionsRecruitedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissionsRecruitedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MISSION_LIST = "missionList";
    /*private static final String ARG_TITLE = "title";*/



    // TODO: Rename and change types of parameters
    private ArrayList<MissionData>  missionList = new ArrayList<MissionData>();
    /*private String title;*/


    public MissionsRecruitedFragment() {
        // Required empty public constructor
    }

    public static MissionsRecruitedFragment newInstance(ArrayList<MissionData> missionList) {
        MissionsRecruitedFragment fragment = new MissionsRecruitedFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MISSION_LIST, missionList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            missionList = (ArrayList<MissionData>) getArguments().getSerializable(ARG_MISSION_LIST);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_missions_recruited, container, false);

    }

}
