package com.soft.zkrn.weilin_application.Activities.Home;
//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.soft.zkrn.weilin_application.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomepageMainpage extends Fragment {

    private Button bt_help;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homepage_frag_mainpage,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bt_help = (Button) getActivity().findViewById(R.id.bt_HomepageMain_help);

        bt_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Homepage_Help.class));
            }
        });

    }
}
