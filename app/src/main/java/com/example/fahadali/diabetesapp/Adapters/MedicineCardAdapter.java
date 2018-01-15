package com.example.fahadali.diabetesapp.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.MedicineCard;
import com.example.fahadali.diabetesapp.R;
import java.util.List;

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
    public List<MedicineCard> medicinecards;
    public MedicineCardAdapter(List<MedicineCard> medicinecards) {
        this.medicinecards = medicinecards;
    }
       

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medicin_card_frag, viewGroup, false);
        CardHolder cardholder = new CardHolder(v);
        return cardholder;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, final int i) {
        holder.medicineName_ET.setText(medicinecards.get(i).MedicineName);
        holder.medicineEffect_ET.setText("Virkning: " + medicinecards.get(i).MedicineEffect);
        holder.medicineSideEffect_ET.setText("Bivirkninger: " + medicinecards.get(i).MedicineSideEffect);
        holder.other_ET.setText("Andet: " + medicinecards.get(i).other);
    }

    @Override
    public int getItemCount() {
        return medicinecards.size();
    }

}
