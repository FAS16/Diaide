//package com.example.fahadali.diabetesapp.Adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.afollestad.sectionedrecyclerview.ItemCoord;
//import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
//import com.afollestad.sectionedrecyclerview.SectionedViewHolder;
//import com.example.fahadali.diabetesapp.Model.Reminder;
//import com.example.fahadali.diabetesapp.R;
//
//import java.util.List;
//
///**
// * Created by fahadali on 16/01/2018.
// */
//
//public class ReminderAdapterV2 extends SectionedRecyclerViewAdapter<ReminderAdapterV2.MainVH> {
//
//    private List<Reminder> reminderList;
//
//    public ReminderAdapterV2(List<Reminder> reminderList) {
//        this.reminderList = reminderList;
//    }
//
//
//    @Override
//    public int getSectionCount() {
//        return 1;
//    }
//
//    @Override
//    public int getItemCount(int sectionIndex) {
//        return reminderList.size(); // number of items in section, you could also pull this from a map of lists
//    }
//
//    @Override
//    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
//        return super.getItemViewType(section, relativePosition, absolutePosition);
//
//
//    }
//
//    @Override
//    public void onBindHeaderViewHolder(MainVH holder, int section, boolean expanded) {
//
//
//        // Setup header view
//    }
//
//    @Override
//    public void onBindViewHolder(MainVH holder, int section, int relativePosition, int absolutePosition) {
//
//        Reminder reminder = reminderList.get(relativePosition);
//        holder.note_TV.setText(reminder.getNote());
//        holder.date_TV.setText(reminder.getDate());
//        holder.time_TV.setText(reminder.getTime());
//        holder.repeat_TV.setText(reminder.getRepeat());
//        holder.priority_TV.setText(reminder.getPriority());
//
//
//
//
//
//        // Setup non-header view.
//        // 'section' is section index.
//        // 'relativePosition' is index in this section.
//        // 'absolutePosition' is index out of all items, including headers and footers.
//        // See sample project for a visual of how these indices work.
//    }
//
//    @Override
//    public void onBindFooterViewHolder(MainVH holder, int section) {
//        // Setup footer view, if footers are enabled (see the next section)
//    }
//
//    @Override
//    public MainVH onCreateViewHolder(ViewGroup parent, int viewType) {
//        // Change inflated layout based on type
//        int layoutRes;
//        switch(viewType) {
//            case VIEW_TYPE_HEADER:
//                layoutRes = R.layout.header_list_item;
//                break;
//            default:
//                layoutRes = R.layout.reminder_list_element;
//                break;
//        }
//        View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
//
//        return new MainVH(v);
//    }
//
//    public static class MainVH extends SectionedViewHolder
//            implements View.OnClickListener {
//
//        ImageView reminderIcon_IV;
//        TextView note_TV, date_TV, time_TV, repeat_TV, priority_TV;
//        ImageButton options_IB;
//
//        public MainVH(View itemView) {
//            super(itemView);
//
//            reminderIcon_IV = itemView.findViewById(R.id.reminder_IV);
//            note_TV = itemView.findViewById(R.id.note);
//            date_TV = itemView.findViewById(R.id.date_rem);
//            time_TV = itemView.findViewById(R.id.time_rem);
//            repeat_TV = itemView.findViewById(R.id.repeat_rem);
//            priority_TV = itemView.findViewById(R.id.priority_rem);
//            options_IB = itemView.findViewById(R.id.menu_card_view_rem);
//
//
//            // Setup view holder. You'd want some views to be optional, e.g. the
//            // header/footer will have views that normal item views do or do not have.
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//
//            // SectionedViewHolder exposes methods such as:
//            boolean isHeader = isHeader();
//            boolean isFooter = isFooter();
//            ItemCoord position = getRelativePosition();
//            int section = position.section();
//            int relativePos = position.relativePos();
//        }
//    }
//}