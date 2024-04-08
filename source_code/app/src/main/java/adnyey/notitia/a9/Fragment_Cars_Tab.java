package adnyey.notitia.a9;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Cars_Tab extends Fragment{

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public Fragment_lister fragmentD;
    FragmentManager childFragMang;
    public static int int_items = 5 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        /**
         *Set an Apater for the View Pager
         */
        childFragMang = getChildFragmentManager();
        final Handler handler12 = new Handler();
        handler12.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms`
                viewPager.setAdapter(new MyAdapter(childFragMang));
                tabLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        tabLayout.setupWithViewPager(viewPager);
                        try {
                            tabLayout.getTabAt(0).setCustomView(R.layout.tab_square_d);
                            tabLayout.getTabAt(1).setCustomView(R.layout.tab_square_c);
                            tabLayout.getTabAt(2).setCustomView(R.layout.tab_square_b);
                            tabLayout.getTabAt(3).setCustomView(R.layout.tab_square_a);
                            tabLayout.getTabAt(4).setCustomView(R.layout.tab_square_s);
                            tabLayout.setMinimumHeight(10);
                        }catch (NullPointerException e){e.printStackTrace();}
                    }
                });
            }
        }, 0);


        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            Bundle args = new Bundle();
            switch (position){
                case 0 :
                    args.putInt("class",1);
                    fragmentD = new Fragment_lister ();
                    fragmentD.setArguments(args);
                    return fragmentD;
                case 1 : args.putInt("class",2);
                    fragmentD = new Fragment_lister ();
                    fragmentD.setArguments(args);
                    return fragmentD;
                case 2 : args.putInt("class",3);
                    fragmentD = new Fragment_lister ();
                    fragmentD.setArguments(args);
                    return fragmentD;
                case 3 : args.putInt("class",4);
                    fragmentD = new Fragment_lister ();
                    fragmentD.setArguments(args);
                    return fragmentD;
                case 4 : args.putInt("class",5);
                    fragmentD = new Fragment_lister ();
                    fragmentD.setArguments(args);
                    return fragmentD;
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }
    }
}
