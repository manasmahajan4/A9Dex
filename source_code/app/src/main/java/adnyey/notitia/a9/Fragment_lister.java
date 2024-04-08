package adnyey.notitia.a9;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;

import java.util.ArrayList;

public class Fragment_lister extends Fragment {

    ArrayList<ArrayList<ObjListing>> receivedData = new ArrayList<>(), tempList;
    private RecyclerView mRecycler;
    private StickyHeaderGridLayoutManager mLayoutManager;
    private Context mContext;
    private Activity mActivity;
    DatabaseAccess databaseAccess;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_carlist, container, false);
        mActivity = getActivity();
        mContext = getContext();
        Log.d("ACCT", mActivity.toString());

        databaseAccess = DatabaseAccess.getInstance(mActivity);

        mRecycler = (RecyclerView) view.findViewById(R.id.carlistRecycler);
        mRecycler.setLayoutManager(new StickyHeaderGridLayoutManager(2));
        tempList = new ArrayList<ArrayList<ObjListing>>();
        mRecycler.setAdapter(new Adapter_CarList(tempList, mContext));
        new Thread() {
            @Override
            public void run() {
                Bundle b = getArguments();
                int s = b.getInt("class");
                receivedData = new ArrayList<ArrayList<ObjListing>>();

                synchronized (databaseAccess) {
                    try {
                        databaseAccess.open();
                        receivedData.add(CursorConverter.extractObjListing(databaseAccess.getCarList(s)));
                    }finally {
                        databaseAccess.close();
                    }

                }

                try {

                    // code runs in a thread
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!isAdded()) {
                                return;
                            } else {

                                // Workaround RecyclerView limitation when playing remove animations. RecyclerView always
                                // puts the removed item on the top of other views and it will be drawn above sticky header.
                                // The only way to fix this, abandon remove animations :(
                                final Handler handler12 = new Handler();
                                handler12.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Do something after 100ms
                                        mLayoutManager = new StickyHeaderGridLayoutManager(2);

                                        mLayoutManager.setSpanSizeLookup(new StickyHeaderGridLayoutManager.SpanSizeLookup() {
                                            @Override
                                            public int getSpanSize(int section, int position) {
                                                switch (section) {
                                                    default:
                                                        return 1;
                                                }
                                            }
                                        });
                                        mRecycler.setItemAnimator(new DefaultItemAnimator() {
                                            @Override
                                            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                                                dispatchRemoveFinished(holder);
                                                return false;
                                            }
                                        });
                                        mRecycler.setNestedScrollingEnabled(false);
                                        mRecycler.setLayoutManager(mLayoutManager);
                                        mRecycler.setAdapter(new Adapter_CarList(receivedData, mContext));
                                    }
                                }, 0);

                            }
                        }
                    });
                } catch (final NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isAdded()) {
            return;
        }
    }
}
