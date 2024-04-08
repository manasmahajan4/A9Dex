package adnyey.notitia.a9;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Adapter_UpEnclosing extends
        RecyclerView.Adapter<Adapter_UpEnclosing.upEncloseHolder> {

    RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    private ArrayList<int[]> mCursor;
    private Context mContext;

    public Adapter_UpEnclosing(Context conte, ArrayList<int[]> mInsects) {
        this.mCursor = mInsects;
        mContext = conte;
    }


    @Override
    public upEncloseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upgrade_encloser, parent, false);
        return new upEncloseHolder(v);
    }

    @Override
    public void onBindViewHolder(final upEncloseHolder holder, int position1) {

        try {
            Boolean isIncomplete = false;
            int total = 0;
            holder.up_stars.setNumStars(holder.getLayoutPosition() + 1);
            holder.up_stars.setRating(holder.getLayoutPosition() + 1);
            holder.up_stars.getProgressDrawable().setColorFilter(Color.parseColor("#FFC107"), PorterDuff.Mode.SRC_ATOP);

            LinearLayoutManager LayOut = new LinearLayoutManager(mContext);
            LayOut.setOrientation(LinearLayoutManager.VERTICAL);
            holder.upList.setLayoutManager(LayOut);

            for (int temp : mCursor.get(holder.getLayoutPosition()))
                if (temp == 0)
                    isIncomplete = true;

            if (isIncomplete) {
                holder.star_total.setText("INCOMPLETE");
                holder.star_total.setTextColor(Color.parseColor("#FF1744"));
            } else {
                for (int temp : mCursor.get(holder.getLayoutPosition()))
                    total += temp;
                holder.star_total.setText(NumberFormat.getIntegerInstance().format(total * 4));
            }
            holder.upList.setNestedScrollingEnabled(false);
            switch (getItemCount()) {

                case 3:
                    switch (holder.getLayoutPosition()) {
                        case 0:

                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 1));
                            break;
                        case 1:

                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 6));
                            break;
                        case 2:

                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 9));
                            break;
                    }
                    break;
                case 4:
                    switch (holder.getLayoutPosition()) {
                        case 0:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 1));
                            break;
                        case 1:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 5));
                            break;
                        case 2:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 8));
                            break;
                        case 3:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 10));
                            break;
                    }
                    break;
                case 5:
                    switch (holder.getLayoutPosition()) {
                        case 0:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 1));
                            break;
                        case 1:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 4));
                            break;
                        case 2:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 7));
                            break;
                        case 3:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 9));
                            break;
                        case 4:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 11));
                            break;
                    }
                    break;
                case 6:
                    switch (holder.getLayoutPosition()) {
                        case 0:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 1));
                            break;
                        case 1:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 4));
                            break;
                        case 2:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 7));
                            break;
                        case 3:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 9));
                            break;
                        case 4:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 11));
                            break;
                        case 5:
                            holder.upList.setAdapter(new Adapter_Up(mContext, mCursor.get(holder.getLayoutPosition()), 13));
                            break;
                    }
                    break;
            }


        }
        //Needed if the position is out of bounds
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mCursor.size();
    }

    /* ViewHolder for each insect item */
    public class upEncloseHolder extends RecyclerView.ViewHolder {
        public RatingBar up_stars;
        public RecyclerView upList;
        public TextView star_total;

        public upEncloseHolder(View itemView) {
            super(itemView);
            up_stars = (RatingBar) itemView.findViewById(R.id.upStars);
            upList = (RecyclerView) itemView.findViewById(R.id.nestedupRecycler);
            star_total = (TextView) itemView.findViewById(R.id.enclode_total);
        }
    }
}
