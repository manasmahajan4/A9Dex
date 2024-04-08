package adnyey.notitia.a9;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Adapter_Import extends
        RecyclerView.Adapter<Adapter_Import.upHolder> {

    private ArrayList<ObjImport> mCursor;
    private int start;
    private Context mContext;

    public Adapter_Import(Context conte, ArrayList<ObjImport> mInsects) {
        this.mCursor = mInsects;
        this.start=start;
        mContext = conte;
    }


    @Override
    public Adapter_Import.upHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_import, parent, false);
        return new Adapter_Import.upHolder(v);
    }

    @Override
    public void onBindViewHolder(final Adapter_Import.upHolder holder, int position1) {

        try {

            switch (holder.getLayoutPosition())
            {
                case 0:
                    holder.import_num.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getQuantitiy()));
                    holder.import_cost.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getCost()));
                    holder.import_numx4.setText("X"+NumberFormat.getIntegerInstance().format(4)+" = "+NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getQuantitiy()*4));
                    holder.import_costx4.setText("X"+NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getQuantitiy()*4)+" = "+NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getCost()*4*mCursor.get(holder.getLayoutPosition()).getQuantitiy()));
                    holder.import_border.setBackgroundColor(mContext.getResources().getColor(R.color.iu_b));
                    holder.import_back.setBackgroundColor(mContext.getResources().getColor(R.color.iu_b));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            holder.import_img.setColorFilter(mContext.getResources().getColor(R.color.iu_n), PorterDuff.Mode.SRC_ATOP);
                        }
                    }, 1);
                    break;
                case 1:
                    holder.import_num.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getQuantitiy()));
                    holder.import_num.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.import_numx4.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.import_numx4.setText("X"+NumberFormat.getIntegerInstance().format(4)+" = "+NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getQuantitiy()*4));
                    holder.import_costx4.setText("X"+NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getQuantitiy()*4)+" = "+NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getCost()*4*mCursor.get(holder.getLayoutPosition()).getQuantitiy()));
                    holder.import_cost.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getCost()));
                    holder.import_border.setBackgroundColor(mContext.getResources().getColor(R.color.ir_b));
                    holder.import_back.setBackgroundColor(mContext.getResources().getColor(R.color.ir_b));
                    holder.import_img.setColorFilter(mContext.getResources().getColor(R.color.ir_n), PorterDuff.Mode.SRC_ATOP);
                    final Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            holder.import_img.setColorFilter(mContext.getResources().getColor(R.color.ir_n), PorterDuff.Mode.SRC_ATOP);
                        }
                    }, 1);
                    break;
                case 2:
                    holder.import_num.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getQuantitiy()));
                    holder.import_cost.setText(NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getCost()));
                    holder.import_numx4.setText("X"+NumberFormat.getIntegerInstance().format(4)+" = "+NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getQuantitiy()*4));
                    holder.import_costx4.setText("X"+NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getQuantitiy()*4)+" = "+NumberFormat.getIntegerInstance().format(mCursor.get(holder.getLayoutPosition()).getCost()*4*mCursor.get(holder.getLayoutPosition()).getQuantitiy()));
                    holder.import_border.setBackgroundColor(mContext.getResources().getColor(R.color.ie_b));
                    holder.import_back.setBackgroundColor(mContext.getResources().getColor(R.color.ie_b));
                    holder.import_img.setColorFilter(mContext.getResources().getColor(R.color.ie_n), PorterDuff.Mode.SRC_ATOP);
                    final Handler handler12 = new Handler();
                    handler12.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            holder.import_img.setColorFilter(mContext.getResources().getColor(R.color.ie_n), PorterDuff.Mode.SRC_ATOP);
                        }
                    }, 1);
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
    public class upHolder extends RecyclerView.ViewHolder {
        public TextView import_num, import_cost, import_numx4, import_costx4;
        public LinearLayout import_border;
        public LinearLayout import_back;
        public ImageView import_img;

        public upHolder(View itemView) {
            super(itemView);
            import_num = (TextView) itemView.findViewById(R.id.import_num);
            import_cost = (TextView) itemView.findViewById(R.id.import_cost);
            import_border = (LinearLayout) itemView.findViewById(R.id.import_border);
            import_back = (LinearLayout) itemView.findViewById(R.id.import_back);
            import_img = (ImageView) itemView.findViewById(R.id.import_img);
            import_numx4 = (TextView) itemView.findViewById(R.id.import_numx4);
            import_costx4 = (TextView) itemView.findViewById(R.id.import_costx4);
        }
    }
}
