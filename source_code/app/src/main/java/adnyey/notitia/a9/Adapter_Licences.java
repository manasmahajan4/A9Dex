package adnyey.notitia.a9;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Licences extends
        RecyclerView.Adapter<Adapter_Licences.upHolder> {

    ArrayList<String> licence_details;
    private int start;
    private Context mContext;

    public Adapter_Licences(Context conte, ArrayList<String> licencee) {
        this.licence_details = licencee;
        this.start=start;
        mContext = conte;
    }


    @Override
    public Adapter_Licences.upHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_license, parent, false);
        return new Adapter_Licences.upHolder(v);
    }

    @Override
    public void onBindViewHolder(final Adapter_Licences.upHolder holder, int position) {

        try {

            holder.import_num.setText(licence_details.get(position));

        }
        //Needed if the position is out of bounds
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return licence_details.size();
    }

    /* ViewHolder for each insect item */
    public class upHolder extends RecyclerView.ViewHolder {
        public TextView import_num;

        public upHolder(View itemView) {
            super(itemView);
            import_num = (TextView) itemView.findViewById(R.id.licence_view);
        }
    }
}
