package adnyey.notitia.a9;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;

import java.util.ArrayList;

public class Fragment_Garage extends Fragment {

    ArrayList<ArrayList<ObjListing>> receivedData = new ArrayList<>(), tempList;
    private RecyclerView mRecycler;
    private StickyHeaderGridLayoutManager mLayoutManager;
    RelativeLayout addCars;
    DatabaseAccess databaseAccess;
    private Context mContext;
    private Activity mActivity;
    GarageAccess garageAccess;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_garage, container, false);

        mContext = getContext();
        mActivity = getActivity();

        databaseAccess = DatabaseAccess.getInstance(mActivity);
        garageAccess = GarageAccess.getInstance(mActivity);

        mRecycler = (RecyclerView) view.findViewById(R.id.carlistGarage);
        mRecycler.setLayoutManager(new StickyHeaderGridLayoutManager(2));
        tempList = new ArrayList<ArrayList<ObjListing>>();
        mRecycler.setAdapter(new Adapter_CarList(tempList, mContext));
        addCars = (RelativeLayout) view.findViewById(R.id.add_cars);

        new Thread() {
            @Override
            public void run() {

                receivedData = new ArrayList<ArrayList<ObjListing>>();
                synchronized (databaseAccess) {
                    synchronized (garageAccess) {
                        try {

                            databaseAccess.open();
                            garageAccess.open();
                            receivedData.add(CursorConverter.extractObjGarageListing(databaseAccess.getCarGarageList(garageAccess.getSavedCars())));
                        } finally {
                            databaseAccess.close();
                            garageAccess.close();
                        }
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

                                        // Workaround RecyclerView limitation when playing remove animations. RecyclerView always
                                        // puts the removed item on the top of other views and it will be drawn above sticky header.
                                        // The only way to fix this, abandon remove animations :(
                                        mRecycler.setItemAnimator(new DefaultItemAnimator() {
                                            @Override
                                            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                                                dispatchRemoveFinished(holder);
                                                return false;
                                            }
                                        });

                                        mRecycler.setLayoutManager(mLayoutManager);
                                        mRecycler.setNestedScrollingEnabled(false);
                                        mRecycler.setAdapter(new Adapter_CarList(receivedData, mContext));

                                        if (receivedData.get(0).size() == 0) {
                                            mRecycler.setVisibility(View.GONE);
                                        }
                                    }
                                }, 0);

                                addCars.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        FragmentManager fm = getChildFragmentManager();
                                        DFrag_Selector dialogFragment = DFrag_Selector.newInstance();
                                        dialogFragment.show(fm, "fragment_selector");
                                        fm.executePendingTransactions();
                                        dialogFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialogInterface) {
                                                //do whatever you want when dialog is dismissed
                                                final Handler handler12 = new Handler();
                                                handler12.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        //Do something after 100ms
                                                        mRecycler.invalidate();
                                                        receivedData = new ArrayList<ArrayList<ObjListing>>();
                                                        synchronized (databaseAccess) {
                                                            synchronized (garageAccess) {
                                                                try {

                                                                    databaseAccess.open();
                                                                    garageAccess.open();
                                                                    receivedData.add(CursorConverter.extractObjGarageListing(databaseAccess.getCarGarageList(garageAccess.getSavedCars())));
                                                                } finally {
                                                                    databaseAccess.close();
                                                                    garageAccess.close();
                                                                }
                                                            }
                                                        }
                                                        if (receivedData.get(0).size() == 0)
                                                            mRecycler.setVisibility(View.GONE);
                                                        else
                                                            mRecycler.setVisibility(View.VISIBLE);

                                                        mRecycler.setItemAnimator(new DefaultItemAnimator() {
                                                            @Override
                                                            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                                                                dispatchRemoveFinished(holder);
                                                                return false;
                                                            }
                                                        });
                                                        mRecycler.setLayoutManager(mLayoutManager);
                                                        mRecycler.setNestedScrollingEnabled(false);
                                                        mRecycler.setAdapter(new Adapter_CarList(receivedData, mContext));
                                                    }
                                                }, 0);

                                            }
                                        });
                                    }
                                });


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
