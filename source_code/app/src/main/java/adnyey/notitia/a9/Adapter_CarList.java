package adnyey.notitia.a9;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;

import java.util.ArrayList;

import static android.view.View.GONE;

public class Adapter_CarList extends StickyHeaderGridAdapter {
    RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    private ArrayList<ArrayList<ObjListing>> labels;
    Context mContext;

    Adapter_CarList(ArrayList<ArrayList<ObjListing>> labels, Context mContext) {
        this.labels = labels;
        this.mContext = mContext;
    }

    @Override
    public int getSectionCount() {
        return labels.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return labels.get(section).size();
    }

    @Override
    public StickyHeaderGridAdapter.HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_header, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public StickyHeaderGridAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(StickyHeaderGridAdapter.HeaderViewHolder viewHolder, int section) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder)viewHolder;
        holder.labelView.setVisibility(GONE);
    }



    @Override
    public void onBindItemViewHolder(StickyHeaderGridAdapter.ItemViewHolder viewHolder, final int section1, final int position1) {
        final MyItemViewHolder holder = (MyItemViewHolder)viewHolder;

        try {
            int id = mContext.getResources().getIdentifier(labels.get(getAdapterPositionSection(holder.getAdapterPosition())).get(getItemSectionOffset(getAdapterPositionSection(holder.getAdapterPosition()), holder.getAdapterPosition())).getImg(), "drawable", mContext.getPackageName());
            Glide.with(mContext)
                    .load(id)
                    .apply(options)
                    .thumbnail(0.1f)
                    .into(holder.imgItem);
        }catch(NullPointerException e){}

        holder.txtItem.setText(""+labels.get(getAdapterPositionSection(holder.getAdapterPosition())).get(getItemSectionOffset(getAdapterPositionSection(holder.getAdapterPosition()), holder.getAdapterPosition())).getName());

        holder.txtItem.setHorizontallyScrolling(true);
        holder.txtItem.setSelected(true);

        holder.starItem.setNumStars(labels.get(getAdapterPositionSection(holder.getAdapterPosition())).get(getItemSectionOffset(getAdapterPositionSection(holder.getAdapterPosition()), holder.getAdapterPosition())).getStars());
        holder.starItem.setRating(labels.get(getAdapterPositionSection(holder.getAdapterPosition())).get(getItemSectionOffset(getAdapterPositionSection(holder.getAdapterPosition()), holder.getAdapterPosition())).getStars());
        holder.starItem.getProgressDrawable().setColorFilter(Color.parseColor("#FFC107"), PorterDuff.Mode.SRC_ATOP);

        holder.carSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                FragmentManager fm = ((AppCompatActivity)mContext).getSupportFragmentManager();
                DFrag_Details DialogFragment = DFrag_Details.newInstance(labels.get(section).get(offset).getId());
                DialogFragment.show(fm, "fragment_details");

            }
        });

    }

    public static class MyHeaderViewHolder extends StickyHeaderGridAdapter.HeaderViewHolder {
        TextView labelView;

        MyHeaderViewHolder(View itemView) {
            super(itemView);
            labelView = (TextView) itemView.findViewById(R.id.label);

        }
    }

    public static class MyItemViewHolder extends StickyHeaderGridAdapter.ItemViewHolder {
        ImageView imgItem;
        TextView txtItem;
        RatingBar starItem;
        LinearLayout carSelect;
        MyItemViewHolder(View itemView) {
            super(itemView);
            imgItem = (ImageView) itemView.findViewById(R.id.carPic);
            txtItem = (TextView) itemView.findViewById(R.id.carName);
            starItem = (RatingBar) itemView.findViewById(R.id.carStars);
            carSelect = (LinearLayout) itemView.findViewById(R.id.car_selector);
        }
    }
}