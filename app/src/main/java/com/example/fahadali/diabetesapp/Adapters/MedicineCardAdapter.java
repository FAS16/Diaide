package com.example.fahadali.diabetesapp.Adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Model.MedicineCard;
import com.example.fahadali.diabetesapp.R;

import java.util.ArrayList;


/**
 * Created by aleks on 11-01-2018.
 */

public class MedicineCardAdapter extends RecyclerView.Adapter<MedicineCardAdapter.CardHolder> {
   public static class CardHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView medicineName_ET;
        TextView medicineEffect_ET;
        TextView medicineSideEffect_ET;
        TextView other_ET;

        CardHolder(View itemView) {
           super(itemView);
           cv = itemView.findViewById(R.id.medicinecard);
           medicineName_ET = itemView.findViewById(R.id.medicineName_ET);
           medicineEffect_ET = itemView.findViewById(R.id.medicineEffect_ET);
           medicineSideEffect_ET = itemView.findViewById(R.id.medicineSideEffect_ET);
           other_ET = itemView.findViewById(R.id.other_ET);

        }
   }
    public ArrayList<MedicineCard> medicinecards;
    public MedicineCardAdapter(ArrayList<MedicineCard> medicinecards) {
        this.medicinecards  = medicinecards;
    }


    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medicin_card_frag, viewGroup, false);
        CardHolder cardholder = new CardHolder(v);
        return cardholder;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, final int i) {
        User.getUserInstance();
        holder.medicineName_ET.setText(User.getUserInstance().getMedicinecardList().get(i).getMedicineName());
        holder.medicineEffect_ET.setText("Virkning: " + User.getUserInstance().getMedicinecardList().get(i).getMedicineEffect());
        holder.medicineSideEffect_ET.setText("Bivirkninger: " + User.getUserInstance().getMedicinecardList().get(i).getMedicineSideEffect());
        holder.other_ET.setText("Andet: " + User.getUserInstance().getMedicinecardList().get(i).getOther());
    }

    @Override
    public int getItemCount() {
        return medicinecards.size();
    }

}
