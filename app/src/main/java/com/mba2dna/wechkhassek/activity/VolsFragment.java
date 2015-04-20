package com.mba2dna.wechkhassek.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.adapter.VolsAdapter;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.model.VolInfo;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class VolsFragment extends Fragment implements View.OnClickListener {
    private ImageButton tvDisplayDate;
    private TextView textV;
    static RecyclerView recList;
    public VolsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_vols, container, false);
      //  Dialog.Builder builder = null;
        recList = (RecyclerView) root.findViewById(R.id.volsList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        VolsAdapter ca = new VolsAdapter(createList(20));
        recList.setAdapter(ca);
        tvDisplayDate = (ImageButton) root.findViewById(R.id.btnChangeDate);
        textV = (TextView) root.findViewById(R.id.tvDate);
        tvDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.Builder builder = new DatePickerDialog.Builder(){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog)fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        textV.setText(date);
                        super.onPositiveActionClicked(fragment);
                        VolsAdapter ca = new VolsAdapter(createList(20));
                        recList.setAdapter(ca);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(fragment.getDialog().getContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.positiveAction("OK")
                        .negativeAction("ANNULER");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getFragmentManager(), null);
            }
        });

        // Inflate the layout for this fragment
        return root;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

        }
    }
    private List<VolInfo> createList(int size) {
        String[] places = { "Lyon","Paris","Oran", "New York", "Berlin", "Chicago","Lyon","Paris","Oran", "New York", "Berlin", "Chicago","Lyon","Paris","Oran", "New York", "Berlin", "Chicago","Lyon","Paris","Oran", "New York", "Berlin", "Chicago","Lyon","Paris","Oran", "New York", "Berlin", "Chicago"};
        String[] Companies = { "AirAlgerie","Asure","Aigle", "AirAlgerie","Asure","Aigle","AirAlgerie","Asure","Aigle", "AirAlgerie","Asure","Aigle","AirAlgerie","Asure","Aigle", "AirAlgerie","Asure","Aigle","AirAlgerie","Asure","Aigle", "AirAlgerie","Asure","Aigle","AirAlgerie","Asure","Aigle", "AirAlgerie","Asure","Aigle","AirAlgerie","Asure","Aigle", "AirAlgerie","Asure","Aigle"};
        String[] etat = { "Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure","Prévu","En route","Atterri à l'heure",};
        List<VolInfo> result = new ArrayList<VolInfo>();
        for (int i=1; i <= size; i++) {
            VolInfo ci = new VolInfo();
            ci.name = VolInfo.NAME_PREFIX + (i* 126);
            ci.company = Companies[i];
            ci.times = Constants.RandomDateTime();
            ci.distination = places[i];
            ci.Etat = etat[i];

            result.add(ci);

        }

        return result;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
