package adnyey.notitia.a9;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Routes extends Fragment {

    ObjTrackHold alltheData;
    ArrayList<ObjLinks> alltheLinks = new ArrayList<>();
    ObjTrackHold tempp = new ObjTrackHold();

    private RecyclerView mRecycler;
    private StickyHeaderGridLayoutManager mLayoutManager;
    private Context mContext;
    private Activity mActivity;
    DatabaseAccess databaseAccess;

    private String url = "https://raw.githubusercontent.com/adnyey/A9Dex/master/best_routes.json";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_route, container, false);
        mActivity = getActivity();
        mContext = getContext();
        databaseAccess = DatabaseAccess.getInstance(mActivity);
        mRecycler = (RecyclerView) view.findViewById(R.id.tracksRecycler);
        mRecycler.setLayoutManager(new StickyHeaderGridLayoutManager(2));
        alltheLinks = new ArrayList<>();
        mRecycler.setAdapter(new Adapter_tracks(tempp, alltheLinks, mContext));

        new Thread() {
            @Override
            public void run() {
                alltheData = new ObjTrackHold();
                alltheLinks = new ArrayList<>();
                synchronized (databaseAccess) {
                    try {
                        databaseAccess.open();
                        alltheData = CursorConverter.extractObjTrack(databaseAccess.getTracks());
                    } finally {
                        databaseAccess.close();
                    }

                }

                // code runs in a thread
                try {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            final Handler handler12 = new Handler();
                            handler12.postDelayed(new Runnable() {
                                @Override
                                public void run() {

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

                                    mRecycler.setLayoutManager(mLayoutManager);
                                    mRecycler.setNestedScrollingEnabled(false);
                                    mRecycler.setAdapter(new Adapter_tracks(alltheData, alltheLinks, mContext));

                                    final Toast toast = Toast.makeText(mContext, "Downloading links . . .", Toast.LENGTH_SHORT);
                                    toast.show();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            toast.cancel();
                                        }
                                    }, 250); // time in millisec

                                    new GetTrackLinks().execute();
                                }
                            }, 0);


                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return view;

    }

    private class GetTrackLinks extends AsyncTask<Void, Void, Void> {

        Boolean hasConnection = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            if (DetectConnection.hasInternetAccess(mContext)) {
                hasConnection = true;

                HttpHandler sh = new HttpHandler();

                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(url);

                Log.e("PP", "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        JSONArray contacts = jsonObj.getJSONArray("routes");

                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);

                            int track_id = c.getInt("track_id");
                            String link = c.getString("link");
                            alltheLinks.add(new ObjLinks(track_id, link));

                            // adding contact to contact list
                        }
                    } catch (final JSONException e) {
                        Log.e("PP", "Json parsing error: " + e.getMessage());
                        hasConnection = false;

                    }
                } else {
                    Log.e("PP", "Couldn't get json from server.");
                    hasConnection = false;

                }
            } else {
                hasConnection = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            /**
             * Updating parsed JSON data into ListView
             * */
            if (hasConnection) {
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

                mRecycler.setLayoutManager(mLayoutManager);
                mRecycler.setNestedScrollingEnabled(false);
                mRecycler.setAdapter(new Adapter_tracks(alltheData, alltheLinks, mContext));

                final Toast toast = Toast.makeText(mContext, "Links successfully fetched!", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 750); // time in millisec

            } else
                Toast.makeText(mContext, "No Internet! Unable to fetch Route Links!", Toast.LENGTH_SHORT).show();
        }

    }
}
