package adnyey.notitia.a9;

import android.content.Context;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Nav extends
        RecyclerView.Adapter<Adapter_Nav.NavHolder> {

    private ArrayList<ObjNav> mCursor = new ArrayList<>();
    private Context mContext;
    private int selectedThing = 0;

    public Adapter_Nav(Context conte, ArrayList<ObjNav> mInsects) {
        this.mCursor = mInsects;
        mContext = conte;
    }


    @Override
    public NavHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nav_h, parent, false);

        return new NavHolder(v);
    }

    @Override
    public void onBindViewHolder(final NavHolder holder, int position) {

        try {

            holder.navTitle.setText(mCursor.get(holder.getLayoutPosition()).getTitle());
            holder.navImg.setImageResource(mCursor.get(holder.getLayoutPosition()).getResID());

            holder.navHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedThing = holder.getLayoutPosition();
                    notifyDataSetChanged();

                }
            });

            holder.navHolder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        holder.navTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                        holder.navBack.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        holder.navBorder.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        holder.navImg.setColorFilter(ContextCompat.getColor(mContext, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                        holder.navTriangle.setColorFilter(ContextCompat.getColor(mContext, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
                        holder.accentSelector.setVisibility(View.VISIBLE);
                        final Handler handler12 = new Handler();
                        handler12.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms
                                holder.navImg.setColorFilter(ContextCompat.getColor(mContext, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                                holder.navTriangle.setColorFilter(ContextCompat.getColor(mContext, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
                            }
                        }, 0);
                    } else {
                        if (selectedThing == holder.getLayoutPosition()) {

                            holder.navTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.navBack.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.navBorder.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.navImg.setColorFilter(ContextCompat.getColor(mContext, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            holder.navTriangle.setColorFilter(ContextCompat.getColor(mContext, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
                            holder.accentSelector.setVisibility(View.VISIBLE);
                            final Handler handler12 = new Handler();
                            handler12.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms
                                    holder.navImg.setColorFilter(ContextCompat.getColor(mContext, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                                    holder.navTriangle.setColorFilter(ContextCompat.getColor(mContext, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
                                }
                            }, 0);
                        } else {

                            holder.navTitle.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.navBack.setBackgroundColor(mContext.getResources().getColor(R.color.nav_normal_back));
                            holder.navBorder.setBackgroundColor(mContext.getResources().getColor(R.color.nav_normal_border));
                            holder.navImg.setColorFilter(ContextCompat.getColor(mContext, R.color.nav_normal_border), android.graphics.PorterDuff.Mode.MULTIPLY);
                            holder.navTriangle.setColorFilter(ContextCompat.getColor(mContext, R.color.nav_normal_border), android.graphics.PorterDuff.Mode.MULTIPLY);
                            holder.accentSelector.setVisibility(View.INVISIBLE);
                            final Handler handler12 = new Handler();
                            handler12.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms
                                    holder.navImg.setColorFilter(ContextCompat.getColor(mContext, R.color.nav_normal_border), android.graphics.PorterDuff.Mode.MULTIPLY);
                                    holder.navTriangle.setColorFilter(ContextCompat.getColor(mContext, R.color.nav_normal_border), android.graphics.PorterDuff.Mode.MULTIPLY);
                                }
                            }, 0);
                        }
                    }
                }
            });

            if (selectedThing == holder.getLayoutPosition()) {

                holder.navTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.navBack.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                holder.navBorder.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                holder.accentSelector.setVisibility(View.VISIBLE);
                final Handler handler12 = new Handler();
                handler12.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        holder.navImg.setColorFilter(ContextCompat.getColor(mContext, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                        holder.navTriangle.setColorFilter(ContextCompat.getColor(mContext, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }
                }, 0);
            } else {

                holder.navTitle.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.navBack.setBackgroundColor(mContext.getResources().getColor(R.color.nav_normal_back));
                holder.navBorder.setBackgroundColor(mContext.getResources().getColor(R.color.nav_normal_border));
                holder.accentSelector.setVisibility(View.INVISIBLE);
                final Handler handler12 = new Handler();
                handler12.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        holder.navImg.setColorFilter(ContextCompat.getColor(mContext, R.color.nav_normal_border), android.graphics.PorterDuff.Mode.MULTIPLY);
                        holder.navTriangle.setColorFilter(ContextCompat.getColor(mContext, R.color.nav_normal_border), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }
                }, 0);
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
    public class NavHolder extends RecyclerView.ViewHolder {
        public TextView navTitle;
        public ImageView navImg, navTriangle;
        public LinearLayout navHolder;
        public RelativeLayout navBorder, navBack;
        public View accentSelector;

        public NavHolder(View itemView) {
            super(itemView);
            navTitle = (TextView) itemView.findViewById(R.id.nav_title);
            navImg = (ImageView) itemView.findViewById(R.id.nav_img);
            navTriangle = (ImageView) itemView.findViewById(R.id.nav_triangle);
            navHolder = (LinearLayout) itemView.findViewById(R.id.nav_holder);
            navBorder = (RelativeLayout) itemView.findViewById(R.id.nav_back2);
            navBack = (RelativeLayout) itemView.findViewById(R.id.nav_back1);
            accentSelector = (View) itemView.findViewById(R.id.accent_selector);
        }
    }
}
