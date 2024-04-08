package adnyey.notitia.a9;

public class ObjTrack {

    int id, time;
    String parent, track, link;

    public ObjTrack(int id,String parent,String track, int time, String link) {
        this.id = id;
        this.time = time;
        this.parent = parent;
        this.track = track;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

