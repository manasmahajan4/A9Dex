package adnyey.notitia.a9;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Handler;
import androidx.fragment.app.Fragment;
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

public class Adapter_Selection_VS extends StickyHeaderGridAdapter {
    private ArrayList<ArrayList<ObjListing>> originalList;
    private boolean isLeft;
    private int currentCarID, frozenCarID;
    private Adapter_Selection_VS.MyItemViewHolder previousHolder;
    Context mContext;
    private Fragment targetFragment;

    Adapter_Selection_VS(ArrayList<ArrayList<ObjListing>> originalList, int currentCarID, int frozenCarID, boolean isLeft, Context mContext, Fragment targetFragment) {
        this.originalList = originalList;
        this.isLeft = isLeft;
        this.mContext = mContext;
        this.targetFragment = targetFragment;
        ((MyContract) targetFragment).methodToPassData(currentCarID);
        for (ArrayList<ObjListing> temp1 : originalList) {
            for (ObjListing temp2 : temp1) {
                if (currentCarID == temp2.getId()) {
                    this.currentCarID = currentCarID;
                    Log.d("VS", "SET Current: " + this.currentCarID);
                    temp2.setIschecked(true);
                }
                if (frozenCarID == temp2.getId()) {
                    this.frozenCarID = frozenCarID;
                    Log.d("VS", "SET Frozen: " + this.frozenCarID);
                    temp2.setIschecked(true);
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
        return new Adapter_Selection_VS.MyHeaderViewHolder(view);
    }

    @Override
    public StickyHeaderGridAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selection, parent, false);
        return new Adapter_Selection_VS.MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(StickyHeaderGridAdapter.HeaderViewHolder viewHolder, int section) {
        final Adapter_Selection_VS.MyHeaderViewHolder holder = (Adapter_Selection_VS.MyHeaderViewHolder) viewHolder;
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
        final Adapter_Selection_VS.MyItemViewHolder holder = (Adapter_Selection_VS.MyItemViewHolder) viewHolder;

        final int section = getAdapterPositionSection(holder.getAdapterPosition());
        final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
        ObjListing currentElement = originalList.get(section).get(offset);
        boolean isChecked = currentElement.getIschecked();

        if (currentElement.getId() == currentCarID)
            Log.d("VS", "Reading: " + currentElement.getName() + ", " + currentElement.getId() + ", " + currentElement.getIschecked());

        holder.txtItem.setText("" + currentElement.getName());
        holder.checkItem.setOnCheckedChangeListener(null);
        holder.checkItem.setChecked(isChecked);

        if (currentElement.getId() == frozenCarID)
            disabledColoring(holder);
        else {
            selectedColoring(holder, isChecked);
            if (currentElement.getId() == currentCarID) {
                previousHolder = holder;
            }
        }

        holder.checkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.getAdapterPosition() > -1) {
                    final int section = getAdapterPositionSection(holder.getAdapterPosition());
                    final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                    ObjListing currentElement = originalList.get(section).get(offset);
                    if (currentElement.getId() != currentCarID) {
                        if (isChecked) {

                            for (ArrayList<ObjListing> temp1 : originalList) {
                                for (ObjListing temp2 : temp1) {
                                    if (currentCarID == temp2.getId()) {
                                        temp2.setIschecked(false);
                                    }
                                }
                            }
                            currentCarID = currentElement.getId();
                            currentElement.setIschecked(true);
                            ((MyContract) targetFragment).methodToPassData(currentCarID);

                            selectedColoring(holder, true);

                            try {
                                selectedColoring(previousHolder, false);
                                previousHolder.checkItem.setChecked(false);
                            } catch (NullPointerException e) {
                            }

                            previousHolder = holder;
                            Log.d("VS", "Selected: " + currentElement.getName() + ", " + currentElement.getId() + ", " + currentElement.getIschecked());

                        }
                    }
                }
            }
        });

    }

    public void disabledColoring(final Adapter_Selection_VS.MyItemViewHolder holder) {
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(holder!=null) {
                    holder.sel_back.setBackgroundColor(mContext.getResources().getColor(R.color.a9accent));
                    holder.sel_border.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                    holder.txtItem.setTextColor(mContext.getResources().getColor(R.color.black));
                    holder.checkItem.setHighlightColor(mContext.getResources().getColor(R.color.black));
                    holder.checkItem.setEnabled(false);
                    setCheckBoxColor(holder.checkItem, mContext.getResources().getColor(R.color.black), mContext.getResources().getColor(R.color.a9accent));
                }
            }
        }, 0);
    }

    public void selectedColoring(final Adapter_Selection_VS.MyItemViewHolder holder, final Boolean state) {
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(holder!=null) {
                    if (isLeft) {
                        if (state) {
                            holder.sel_back.setBackgroundColor(mContext.getResources().getColor(R.color.leftCar));
                            holder.sel_border.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                            holder.txtItem.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.checkItem.setHighlightColor(mContext.getResources().getColor(R.color.black));
                            setCheckBoxColor(holder.checkItem, mContext.getResources().getColor(R.color.black), mContext.getResources().getColor(R.color.leftCar));
                            holder.checkItem.setEnabled(false);
                        } else {
                            holder.sel_back.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                            holder.sel_border.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                            holder.txtItem.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.checkItem.setHighlightColor(mContext.getResources().getColor(R.color.white));
                            setCheckBoxColor(holder.checkItem, mContext.getResources().getColor(R.color.black), mContext.getResources().getColor(R.color.white));
                            holder.checkItem.setEnabled(true);
                        }
                    } else {
                        if (state) {
                            holder.sel_back.setBackgroundColor(mContext.getResources().getColor(R.color.rightCar));
                            holder.sel_border.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                            holder.txtItem.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.checkItem.setHighlightColor(mContext.getResources().getColor(R.color.black));
                            setCheckBoxColor(holder.checkItem, mContext.getResources().getColor(R.color.black), mContext.getResources().getColor(R.color.rightCar));
                            holder.checkItem.setEnabled(false);
                        } else {
                            holder.sel_back.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                            holder.sel_border.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                            holder.txtItem.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.checkItem.setHighlightColor(mContext.getResources().getColor(R.color.white));
                            setCheckBoxColor(holder.checkItem, mContext.getResources().getColor(R.color.black), mContext.getResources().getColor(R.color.white));
                            holder.checkItem.setEnabled(true);
                        }
                    }
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