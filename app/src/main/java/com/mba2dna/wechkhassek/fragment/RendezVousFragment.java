package com.mba2dna.wechkhassek.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.adapter.RendezVousAdapter;
import com.mba2dna.wechkhassek.constants.Constants;
import com.mba2dna.wechkhassek.model.RenderVous;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RendezVousFragment extends Fragment {
    private ImageButton tvDisplayDate;
    private TextView textV;
    static RecyclerView recList;
    public RendezVousFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root= inflater.inflate(R.layout.fragment_rendez_vous, container, false);
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
                        RendezVousAdapter ca = new RendezVousAdapter(createList(12,date));
                        recList.setAdapter(ca);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                      //  Toast.makeText(fragment.getDialog().getContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.positiveAction("OK")
                        .negativeAction("ANNULER");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getFragmentManager(), null);
            }
        });

        recList = (RecyclerView) root.findViewById(R.id.volsList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        RendezVousAdapter ca = new RendezVousAdapter(createList(12,currentDateandTime));
        textV.setText(currentDateandTime);
        recList.setAdapter(ca);
        return root;
    }
    private List<RenderVous> createList(int size,String dateto) {
        String[] places = getResources().getStringArray(R.array.willaya_array);
        String[] etat = getResources().getStringArray(R.array.specialiter_array);
        String[] names = {"","BIDA Mohamed Amine" ,"Amriou Farid" , "Salhi Abdelkrim", "Moukhtari Amer" , "Rabhi Mohamed" , "Tahelkhir Said ", "Wadah Fakri" , "Mazari Rabah" , "Serdina Smina" , "Bouhan Sifdine" ,"Motar Tah" , "Daraji Samir", "Mouhoubi Adel"};
        List<RenderVous> result = new ArrayList<RenderVous>();
        for (int i=1; i <= size; i++) {
            RenderVous ci = new RenderVous();
            ci.name = dateto;
            ci.nom_prenom = names[i];
            ci.times = Constants.RandomDateTime();
            ci.distination = places[i];
            ci.Etat = etat[i];
            result.add(ci);
        }
        return result;
    }

}
