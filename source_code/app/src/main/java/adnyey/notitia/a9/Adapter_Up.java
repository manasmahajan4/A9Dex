package adnyey.notitia.a9;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Adapter_Up extends
        RecyclerView.Adapter<Adapter_Up.upHolder> {

    private int[] mCursor;
    private int start;
    private Context mContext;

    public Adapter_Up(Context conte, int[] mInsects, int start) {
        this.mCursor = mInsects;
        this.start = start;
        mContext = conte;
    }


    @Override
    public upHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upgrades, parent, false);
        return new upHolder(v);
    }

    @Override
    public void onBindViewHolder(final upHolder holder, int position1) {

        try {
            DecimalFormat Format_number = new DecimalFormat("#00");
            holder.up_stage.setText(Format_number.format(start + holder.getLayoutPosition()));
            if (mCursor[holder.getLayoutPosition()] != 0) {
                holder.up_cost.setText(NumberFormat.getIntegerInstance().format(mCursor[holder.getLayoutPosition()]));
                holder.up_costx4.setText("X" + NumberFormat.getIntegerInstance().format(4) + " = " + NumberFormat.getIntegerInstance().format(mCursor[holder.getLayoutPosition()] * 4));
            } else {
                holder.up_cost.setText("?");
                holder.up_costx4.setVisibility(View.GONE);
            }

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
    public class upHolder extends RecyclerView.ViewHolder {
        public TextView up_stage, up_cost, up_costx4;

        public upHolder(View itemView) {
            super(itemView);
            up_stage = (TextView) itemView.findViewById(R.id.up_stage);
            up_cost = (TextView) itemView.findViewById(R.id.up_cost);
            up_costx4 = (TextView) itemView.findViewById(R.id.up_costx4);
        }
    }
}
