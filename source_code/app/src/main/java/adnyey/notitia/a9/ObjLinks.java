package adnyey.notitia.a9;

public class ObjLinks {
    private int track_id;
    private String link;

    public ObjLinks(int track_id, String link) {
        this.track_id = track_id;
        this.link = link;
    }

    public int getTrack_id() {
        return track_id;
    }

    public String getLink() {
        return link;
    }
}
