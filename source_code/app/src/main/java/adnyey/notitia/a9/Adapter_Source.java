package adnyey.notitia.a9;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Source extends
        RecyclerView.Adapter<Adapter_Source.upHolder> {

    ArrayList<ObjSource> licence_details;
    private int start;
    private Context mContext;

    public Adapter_Source(Context conte, ArrayList<ObjSource> licencee) {
        this.licence_details = licencee;
        this.start=start;
        mContext = conte;
    }


    @Override
    public Adapter_Source.upHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_source, parent, false);
        return new Adapter_Source.upHolder(v);
    }

    @Override
    public void onBindViewHolder(final Adapter_Source.upHolder holder, int position) {

        try {

            holder.title.setText(licence_details.get(holder.getAdapterPosition()).getTitle());

            if(licence_details.get(holder.getAdapterPosition()).getSource().equals(""))
            {
                //Nothing
            }
            else {
                holder.clicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uriUrl = Uri.parse(licence_details.get(holder.getAdapterPosition()).getSource());
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        mContext.startActivity(launchBrowser);
                    }
                });
            }

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
        public TextView title;
        public RelativeLayout clicker;

        public upHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txt_src);
            clicker = (RelativeLayout) itemView.findViewById(R.id.src_clicker);
        }
    }
}
