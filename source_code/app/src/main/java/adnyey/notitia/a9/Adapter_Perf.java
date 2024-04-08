package adnyey.notitia.a9;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Adapter_Perf extends
        RecyclerView.Adapter<Adapter_Perf.PerfHolder> {
    RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    private ArrayList<ObjPerf> mCursor = new ArrayList<>();
    private Context mContext;
    private boolean isCompare;

    public Adapter_Perf(Context conte, ArrayList<ObjPerf> mInsects, boolean isCompare) {
        this.mCursor = mInsects;
        mContext = conte;
        this.isCompare = isCompare;
    }


    @Override
    public PerfHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    int layoutID = R.layout.item_perf;
    if(isCompare){
        layoutID = R.layout.item_perf_cmp;
    }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layoutID, parent, false);
        return new PerfHolder(v);
    }

    @Override
    public void onBindViewHolder( final PerfHolder holder, int position1) {
        Log.d("AAGG","IN "+holder.getLayoutPosition());
        try {

            holder.perf_name.setText(mCursor.get(holder.getLayoutPosition()).getName());

            try {
                Glide.with(mContext)
                        .load(mCursor.get(holder.getLayoutPosition()).getIconRes())
                        .apply(options)
                        .thumbnail(0.1f)
                        .into(holder.perf_img);
            } catch (NullPointerException e) {
            }

            DecimalFormat Format_number = new DecimalFormat(mCursor.get(holder.getLayoutPosition()).getPattern());

            if (mCursor.get(holder.getLayoutPosition()).getValue_stock() != 0)
                holder.perf_stock.setText(Format_number.format(mCursor.get(holder.getLayoutPosition()).getValue_stock()));
            else
                holder.perf_stock.setText("?");

            if (mCursor.get(holder.getLayoutPosition()).getValue_max() != 0)
                holder.perf_max.setText(Format_number.format(mCursor.get(holder.getLayoutPosition()).getValue_max()));
            else
                holder.perf_max.setText("?");

            if(!isCompare) {
                if (holder.getLayoutPosition() > 4) {

                    holder.maxsholder.setVisibility(View.GONE);
                    holder.perf_fuel.setVisibility(View.VISIBLE);
                    holder.perf_fuel.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getValue_max()));


                    if (holder.getLayoutPosition() == 6) {
                        Log.d("AAGG", "IN HERE");
                        if (mCursor.get(holder.getLayoutPosition()).getValue_max() == 0)
                            holder.perf_fuel.setText("?");
                        else {

                            int hours = (int) mCursor.get(holder.getLayoutPosition()).getValue_max() / 60; //since both are ints, you get an int
                            int minutes = (int) mCursor.get(holder.getLayoutPosition()).getValue_max() % 60;

                            if (hours <= 0)
                                holder.perf_fuel.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getValue_max()) + " m");
                            else if (hours > 0 && minutes <= 0) {
                                holder.perf_fuel.setText(NumberFormat.getIntegerInstance().format(hours) + " H");
                            } else if (hours > 0 && minutes > 0) {
                                holder.perf_fuel.setText(NumberFormat.getIntegerInstance().format(hours) + " H " + NumberFormat.getIntegerInstance().format(minutes) + " m");
                            }
                        }

                    }
                }
            }else
            {


                if (holder.getLayoutPosition() == 6) {

                    if (mCursor.get(holder.getLayoutPosition()).getValue_stock() != 0)
                    {

                        int hours = (int) mCursor.get(holder.getLayoutPosition()).getValue_stock() / 60; //since both are ints, you get an int
                        int minutes = (int) mCursor.get(holder.getLayoutPosition()).getValue_stock() % 60;

                        if (hours <= 0)
                            holder.perf_stock.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getValue_stock()) + " m");
                        else if (hours > 0 && minutes <= 0) {
                            holder.perf_stock.setText(NumberFormat.getIntegerInstance().format(hours) + " H");
                        } else if (hours > 0 && minutes > 0) {
                            holder.perf_stock.setText(NumberFormat.getIntegerInstance().format(hours) + " H " + NumberFormat.getIntegerInstance().format(minutes) + " m");
                        }
                    }
                    else
                        holder.perf_stock.setText("?");

                    if (mCursor.get(holder.getLayoutPosition()).getValue_max() != 0)
                    {

                        int hours = (int) mCursor.get(holder.getLayoutPosition()).getValue_max() / 60; //since both are ints, you get an int
                        int minutes = (int) mCursor.get(holder.getLayoutPosition()).getValue_max() % 60;

                        if (hours <= 0)
                            holder.perf_max.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getValue_max()) + " m");
                        else if (hours > 0 && minutes <= 0) {
                            holder.perf_max.setText(NumberFormat.getIntegerInstance().format(hours) + " H");
                        } else if (hours > 0 && minutes > 0) {
                            holder.perf_max.setText(NumberFormat.getIntegerInstance().format(hours) + " H " + NumberFormat.getIntegerInstance().format(minutes) + " m");
                        }
                    }
                    else
                        holder.perf_max.setText("?");

                }
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
    public class PerfHolder extends RecyclerView.ViewHolder {
        public TextView perf_name, perf_max, perf_stock, perf_fuel;
        public ImageView perf_img;
        public LinearLayout maxsholder;

        public PerfHolder(View itemView) {
            super(itemView);
            perf_name = (TextView) itemView.findViewById(R.id.perf_name);
            perf_max = (TextView) itemView.findViewById(R.id.perf_max);
            perf_stock = (TextView) itemView.findViewById(R.id.perf_stock);
            perf_img = (ImageView) itemView.findViewById(R.id.perf_img);
            perf_fuel = (TextView) itemView.findViewById(R.id.perf_fuel);
            maxsholder = (LinearLayout) itemView.findViewById(R.id.max_s_holder);
        }
    }
}
