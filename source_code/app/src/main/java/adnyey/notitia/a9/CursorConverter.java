package adnyey.notitia.a9;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CursorConverter {

    public static ArrayList<ObjListing> extractObjListing(Cursor cursor) {
        ArrayList<ObjListing> dataExtracted = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                dataExtracted.add(new ObjListing(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5)));

            } while (cursor.moveToNext());
        }

        return dataExtracted;
    }

    public static ObjTrackHold extractObjTrack(Cursor cursor) {
        ArrayList<ArrayList<ObjTrack>> dataExtracted = new ArrayList<>();
        ArrayList<ObjTrack> singleData = new ArrayList<>();
        ArrayList<String> route_parents = new ArrayList<>();
        ObjTrackHold alltheData;

        if (cursor.moveToFirst()) {
            do {

                if(!route_parents.contains(cursor.getString(1)))
                {
                    route_parents.add(cursor.getString(1));
                }

            } while (cursor.moveToNext());

            dataExtracted = new ArrayList<ArrayList<ObjTrack>>();
            for(int i=0;i<route_parents.size();i++)
            {
                cursor.moveToFirst();
                do {

                    if(route_parents.get(i).equals(cursor.getString(1)))
                    {
                        singleData.add(new ObjTrack(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getInt(3), cursor.getString(4)));
                    }

                } while (cursor.moveToNext());
                Collections.sort(singleData, new CustomComparator());
                dataExtracted.add(singleData);
                singleData = new ArrayList<>();

            }
        }

        alltheData = new ObjTrackHold(dataExtracted, route_parents);

        return alltheData;
    }

    public static ArrayList<ObjListing> extractObjGarageListing(ArrayList<Cursor> input)
    {
        ArrayList<ObjListing> extractedData = new ArrayList<>();

        for(Cursor temp : input)
        {
            extractedData.add(extractObjListing(temp).get(0));
        }

        return extractedData;
    }

    public static ObjListing extractObj(Cursor input)
    {
        ObjListing extractedData = new ObjListing(input.getInt(0), input.getString(1), input.getString(2), input.getInt(3), input.getInt(4), input.getString(5));

        return extractedData;
    }
}
