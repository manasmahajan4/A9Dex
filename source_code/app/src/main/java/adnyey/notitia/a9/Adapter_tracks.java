package adnyey.notitia.a9;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class Adapter_tracks extends StickyHeaderGridAdapter {
    private ObjTrackHold labels;
    Context mContext;
    ArrayList<ObjLinks> alltheLinks = new ArrayList<>();

    Adapter_tracks(ObjTrackHold labels, ArrayList<ObjLinks> alltheLinks, Context mContext) {
        this.labels = labels;
        this.mContext = mContext;
        this.alltheLinks = alltheLinks;
    }

    @Override
    public int getSectionCount() {
        try {
            return labels.getMainData().size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public int getSectionItemCount(int section) {
        return labels.getMainData().get(section).size();
    }

    @Override
    public StickyHeaderGridAdapter.HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_header_track, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public StickyHeaderGridAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_track_item, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(StickyHeaderGridAdapter.HeaderViewHolder viewHolder, int section1) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder) viewHolder;
        String imageHeader = "d_lancer";
        switch (getAdapterPositionSection(holder.getAdapterPosition())) {
            case 0:
                imageHeader = "scotland";
                break;
            case 1:
                imageHeader = "himalayas";
                break;
            case 2:
                imageHeader = "us_mid";
                break;
            case 3:
                imageHeader = "shanghai";
                break;
            case 4:
                imageHeader = "rome";
                break;
            case 5:
                imageHeader = "san_francis";
                break;
            case 6:
                imageHeader = "cairo";
                break;
            case 7:
                imageHeader = "caribbean";
                break;
            case 8:
                imageHeader = "osaka";
                break;
        }

        holder.labelDeatil.setText(labels.getParents().get(getAdapterPositionSection(holder.getAdapterPosition())));

        try {
            int id = mContext.getResources().getIdentifier(imageHeader, "drawable", mContext.getPackageName());
            holder.labelView.setImageResource(id);
        } catch (NullPointerException e) {
        }
    }

    @Override
    public void onBindItemViewHolder(StickyHeaderGridAdapter.ItemViewHolder viewHolder, final int section1, final int position1) {
        final MyItemViewHolder holder = (MyItemViewHolder) viewHolder;

        holder.trackName.setText(labels.getMainData().get(getAdapterPositionSection(holder.getAdapterPosition())).get(getItemSectionOffset(getAdapterPositionSection(holder.getAdapterPosition()), holder.getAdapterPosition())).getTrack());

        holder.trackTime.setText(NumberFormat.getIntegerInstance().format(labels.getMainData().get(getAdapterPositionSection(holder.getAdapterPosition())).get(getItemSectionOffset(getAdapterPositionSection(holder.getAdapterPosition()), holder.getAdapterPosition())).getTime()) + Html.fromHtml("&rdquo;"));

        holder.trackLinkAvail.setTextColor(mContext.getResources().getColor(R.color.very_red));

        if (alltheLinks.size() > 0)
            for (ObjLinks temp : alltheLinks) {
                if (temp.getTrack_id() == labels.getMainData().get(getAdapterPositionSection(holder.getAdapterPosition())).get(getItemSectionOffset(getAdapterPositionSection(holder.getAdapterPosition()), holder.getAdapterPosition())).getId()) {
                    if(!temp.getLink().isEmpty()) {
                        holder.trackLinkAvail.setTextColor(mContext.getResources().getColor(R.color.check_selected));
                    }
                }
            }

        holder.trackClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                //Toast.makeText(mContext,"Under Construction", Toast.LENGTH_LONG).show();
                if (alltheLinks.size() > 0)
                    for (ObjLinks temp : alltheLinks) {
                        if (temp.getTrack_id() == labels.getMainData().get(section).get(offset).getId()) {
                            if(!temp.getLink().isEmpty()) {
                                Uri uriUrl = Uri.parse(temp.getLink());
                                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                mContext.startActivity(launchBrowser);
                            }
                        }
                    }
//                if (labels.getMainData().get(section).get(offset).getLink()!=null) {
//                    Uri uriUrl = Uri.parse(labels.getMainData().get(section).get(offset).getLink());
//                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//                    mContext.startActivity(launchBrowser);
//                }

            }
        });

    }

    public static class MyHeaderViewHolder extends StickyHeaderGridAdapter.HeaderViewHolder {
        ImageView labelView;
        TextView labelDeatil;


        MyHeaderViewHolder(View itemView) {
            super(itemView);
            labelView = (ImageView) itemView.findViewById(R.id.label);
            labelDeatil = (TextView) itemView.findViewById(R.id.parentName);

        }
    }

    public static class MyItemViewHolder extends StickyHeaderGridAdapter.ItemViewHolder {
        TextView trackTime, trackName, trackLinkAvail;
        LinearLayout trackClick;

        MyItemViewHolder(View itemView) {
            super(itemView);
            trackTime = (TextView) itemView.findViewById(R.id.trackLength);
            trackName = (TextView) itemView.findViewById(R.id.trackName);
            trackClick = (LinearLayout) itemView.findViewById(R.id.track_clicker);
            trackLinkAvail = (TextView) itemView.findViewById(R.id.hasLink);
        }
    }
}
