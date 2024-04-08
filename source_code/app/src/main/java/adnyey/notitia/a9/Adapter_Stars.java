package adnyey.notitia.a9;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;

public class Adapter_Stars extends
        RecyclerView.Adapter<Adapter_Stars.NavHolder> {

    private int mCursor[], mRanks[];
    private Context mContext;

    public Adapter_Stars(Context conte, int mInsects[], int mBois[]) {
        this.mCursor = mInsects;
        this.mRanks = mBois;
        mContext = conte;
    }


    @Override
    public NavHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_star, parent, false);
        return new NavHolder(v);
    }

    @Override
    public void onBindViewHolder(final NavHolder holder, int position1) {

        try {
                if (mCursor[holder.getLayoutPosition()] != 0)
                    holder.starCount.setText(NumberFormat.getIntegerInstance().format(mCursor[holder.getLayoutPosition()]));
                else
                    holder.starCount.setText("?");

                if (mRanks[holder.getLayoutPosition()+1] != 0)
                    holder.rankCount.setText(NumberFormat.getIntegerInstance().format(mRanks[holder.getLayoutPosition()+1]));
                else
                    holder.rankCount.setText("?");
        }
        //Needed if the position is out of bounds
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mCursor.length;
    }

    /* ViewHolder for each insect item */
    public static class NavHolder extends RecyclerView.ViewHolder {
        public TextView starCount, rankCount;
        public NavHolder(View itemView) {
            super(itemView);
            starCount = (TextView) itemView.findViewById(R.id.star_value);
            rankCount = (TextView) itemView.findViewById(R.id.star_rank);
        }
    }
}
