package adnyey.notitia.a9;

public class ObjPerf {
    int iconRes;
    String name, pattern;
    double value_stock, value_max;

    public ObjPerf(int iconRes, String name, double value_stock, double value_max, String pattern) {
        this.iconRes = iconRes;
        this.name = name;
        this.value_stock = value_stock;
        this.value_max = value_max;
        this.pattern = pattern;
    }

    public int getIconRes() {
        return iconRes;
    }

    public String getName() {
        return name;
    }

    public double getValue_stock() {
        return value_stock;
    }

    public double getValue_max() {
        return value_max;
    }

    public String getPattern() {
        return pattern;
    }
}
