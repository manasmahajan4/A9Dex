package adnyey.notitia.a9;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Handler;

import androidx.core.widget.CompoundButtonCompat;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;

import java.util.ArrayList;

public class Adapter_Selection extends StickyHeaderGridAdapter {
    private ArrayList<ArrayList<ObjListing>> originalList, garageList;
    Context mContext;

    Adapter_Selection(ArrayList<ArrayList<ObjListing>> originalList, ArrayList<ArrayList<ObjListing>> garageList, Context mContext) {
        this.originalList = originalList;
        this.garageList = garageList;
        this.mContext = mContext;

        for (ArrayList<ObjListing> temp1 : originalList) {
            for (ObjListing temp2 : temp1) {
                for (ObjListing temp3 : garageList.get(0)) {
                    if (temp3.getId() == temp2.getId()) {
                        temp2.setIschecked(true);
                    }
                }
            }
        }

    }

    @Override
    public int getSectionCount() {
        return originalList.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return originalList.get(section).size();
    }

    @Override
    public StickyHeaderGridAdapter.HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_header, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public StickyHeaderGridAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selection, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(StickyHeaderGridAdapter.HeaderViewHolder viewHolder, int section) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder) viewHolder;
        String cls = "";
        switch (section) {
            case 0:
                cls = "d";
                break;
            case 1:
                cls = "c";
                break;
            case 2:
                cls = "b";
                break;
            case 3:
                cls = "a";
                break;
            case 4:
                cls = "s";
                break;
        }
        holder.labelView.setText(cls);
    }

    @Override
    public void onBindItemViewHolder(StickyHeaderGridAdapter.ItemViewHolder viewHolder, final int section1, final int position1) {
        final MyItemViewHolder holder = (MyItemViewHolder) viewHolder;


        if (originalList.get(getAdapterPositionSection(holder.getAdapterPosition())).get(getItemSectionOffset(getAdapterPositionSection(holder.getAdapterPosition()), holder.getAdapterPosition())).getIschecked()) {
            holder.checkItem.setOnCheckedChangeListener(null);
            holder.checkItem.setChecked(true);
            selectedColoring(holder, true);
        } else {
            holder.checkItem.setOnCheckedChangeListener(null);
            holder.checkItem.setChecked(false);
            selectedColoring(holder, false);
        }

        holder.checkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                GarageAccess garageAccess = GarageAccess.getInstance(mContext);
                synchronized (garageAccess) {
                    garageAccess.open();
                    try {
                        if (isChecked) {

                            selectedColoring(holder, true);
                            originalList.get(section).get(offset).setIschecked(true);
                            garageAccess.addCar(originalList.get(section).get(offset).getId());
                            Log.d("TTAG", "Adding car " + originalList.get(section).get(offset).getId() + " " + originalList.get(section).get(offset).getName());
                        } else {

                            selectedColoring(holder, false);
                            originalList.get(section).get(offset).setIschecked(false);
                            garageAccess.removeCar(originalList.get(section).get(offset).getId());
                            Log.d("TTAG", "Removing car " + originalList.get(section).get(offset).getId() + " " + originalList.get(section).get(offset).getName());
                        }
                    }finally{
                        garageAccess.close();
                    }
                }
            }
        });

        holder.txtItem.setText("" + originalList.get(getAdapterPositionSection(holder.getAdapterPosition())).get(getItemSectionOffset(getAdapterPositionSection(holder.getAdapterPosition()), holder.getAdapterPosition())).getName());

    }

    public void selectedColoring(final MyItemViewHolder holder, final Boolean state) {
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (state) {
                    holder.sel_back.setBackgroundColor(mContext.getResources().getColor(R.color.check_selected));
                    holder.sel_border.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                    holder.txtItem.setTextColor(mContext.getResources().getColor(R.color.black));
                    holder.checkItem.setHighlightColor(mContext.getResources().getColor(R.color.black));
                    setCheckBoxColor(holder.checkItem, mContext.getResources().getColor(R.color.black), mContext.getResources().getColor(R.color.check_selected));
                } else {
                    holder.sel_back.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                    holder.sel_border.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                    holder.txtItem.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.checkItem.setHighlightColor(mContext.getResources().getColor(R.color.check_selected));
                    setCheckBoxColor(holder.checkItem, mContext.getResources().getColor(R.color.black), mContext.getResources().getColor(R.color.check_selected));
                }
            }
        }, 0);
    }

    public void setCheckBoxColor(CheckBox checkBox, int checkedColor, int uncheckedColor) {
        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {checkedColor, uncheckedColor};
        CompoundButtonCompat.setButtonTintList(checkBox, new
                ColorStateList(states, colors));
    }

    public static class MyHeaderViewHolder extends StickyHeaderGridAdapter.HeaderViewHolder {
        TextView labelView;

        MyHeaderViewHolder(View itemView) {
            super(itemView);
            labelView = (TextView) itemView.findViewById(R.id.label);

        }
    }

    public class MyItemViewHolder extends StickyHeaderGridAdapter.ItemViewHolder {
        AppCompatCheckBox checkItem;
        TextView txtItem;
        LinearLayout sel_back;
        RelativeLayout sel_border;

        MyItemViewHolder(View itemView) {
            super(itemView);
            checkItem = (AppCompatCheckBox) itemView.findViewById(R.id.check_selected);
            txtItem = (TextView) itemView.findViewById(R.id.check_name);
            sel_border = (RelativeLayout) itemView.findViewById(R.id.sel_border);
            sel_back = (LinearLayout) itemView.findViewById(R.id.sel_back);
        }
    }
}