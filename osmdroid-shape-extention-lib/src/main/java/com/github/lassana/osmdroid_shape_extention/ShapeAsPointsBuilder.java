package com.github.lassana.osmdroid_shape_extention;

import android.support.annotation.NonNull;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import org.osmdroid.util.GeoPoint;

import java.util.LinkedList;
import java.util.List;

/**
 * Class which creates geo-points by geo-curves.
 *
 * @author Nikolai Doronin {@literal <lassana.nd@gmail.com>}
 * @since 3/26/16.
 */
public class ShapeAsPointsBuilder {

    private final List<GeoPoint> points;
    private final Geodesic wgs84;

    public ShapeAsPointsBuilder() {
        points = new LinkedList<>();
        wgs84 = Geodesic.WGS84;
    }

    public ShapeAsPointsBuilder(@NonNull  List<GeoPoint> startList) {
        points = startList;
        wgs84 = Geodesic.WGS84;
    }

    /**
     * Generates points for a great circle (just one next point).
     * <p>
     * Sample geo-data:
     * <pre>
     * NZB172 1   364602.5S 1745003.6E GRC
     * </pre>
     */
    public ShapeAsPointsBuilder GRC(@NonNull GeoPoint geoPoint) {
        points.add(geoPoint);
        return this;
    }

    /**
     * Generates points for a clockwise arc.
     * <p>
     * Sample geo-data:
     * <pre>
     * NZB172 2   365618.3S 1743309.7E CWA 365219.2S 1742923.5E 5 NM
     * NZB172 3   364819.8S 1742537.7E GRC
     * </pre>
     */
    public ShapeAsPointsBuilder CWA(@NonNull GeoPoint startPoint, @NonNull GeoPoint centerPoint,
                                    @NonNull GeoPoint endPoint, double radius) {
        points.add(startPoint);

        GeodesicData f = wgs84.Inverse(centerPoint.getLatitude(), centerPoint.getLongitude(), startPoint.getLatitude(), startPoint.getLongitude());
        GeodesicData t = wgs84.Inverse(centerPoint.getLatitude(), centerPoint.getLongitude(), endPoint.getLatitude(), endPoint.getLongitude());

        double ffaz = f.azi1;
        double tfaz = (((ffaz < 0) && (t.azi1 < 0)) || (t.azi1 > 0)) ? t.azi1 : 360 + t.azi1;

        while ((int)ffaz != (int)tfaz) {
            GeodesicData llb = wgs84.Direct(centerPoint.getLatitude(), centerPoint.getLongitude(), ffaz, f.s12);
            points.add(new GeoPoint(llb.lat2, llb.lon2));
            ffaz += 1;
            if (ffaz>360) {
                ffaz -= 360;
            }
        }

        points.add(endPoint);

        return this;
    }

    /**
     * Generates points for a counterclockwise arc.
     * <p>
     * Sample geo-data:
     * <pre>
     * NZB172 4   364106.1S 1743732.9E CCA 363954.5S 1743840.7E 1.5 NM
     * NZB172 5   364100.3S 1743957.1E GRC
     * </pre>
     */
    public ShapeAsPointsBuilder CCA(@NonNull GeoPoint startPoint, @NonNull GeoPoint centerPoint,
                                    @NonNull GeoPoint endPoint, double radius) {
        points.add(startPoint);

        GeodesicData f = wgs84.Inverse(centerPoint.getLatitude(), centerPoint.getLongitude(), startPoint.getLatitude(), startPoint.getLongitude());
        GeodesicData t = wgs84.Inverse(centerPoint.getLatitude(), centerPoint.getLongitude(), endPoint.getLatitude(), endPoint.getLongitude());

        double ffaz = (f.azi1 < 0) ? 360 + f.azi1: f.azi1;
        double tfaz = (t.azi1 < 0) ? 360 + t.azi1: t.azi1;

        while (Math.abs((int)ffaz) != Math.abs((int)tfaz)) {
            GeodesicData llb = wgs84.Direct(centerPoint.getLatitude(), centerPoint.getLongitude(), ffaz,  f.s12);
            points.add(new GeoPoint(llb.lat2, llb.lon2));
            ffaz -= 1;
            if (ffaz < 0) {
                ffaz += 360;
            }
        }

        points.add(endPoint);

        return this;
    }

    /**
     * Generates points for a circle.
     * <p>
     * Sample geo-data:
     * <pre>
     * NZA347_S 1    CIR 391722.4S 1753345.6E 3 NM
     * </pre>
     */
    public ShapeAsPointsBuilder CIR(@NonNull GeoPoint centerPoint, double dist) {
        for ( double ffaz = 1; ffaz < 360; ffaz += 1 ){
            GeodesicData llb = wgs84.Direct(centerPoint.getLatitude(), centerPoint.getLongitude(), ffaz, dist);
            points.add(new GeoPoint(llb.lat2, llb.lon2));
        }
        return this;
    }

    /**
     * Generates points for a rhumbline.
     * <p>
     * Not implemented.
     */
    protected ShapeAsPointsBuilder RHL() {
        return this;
    }

    /**
     * Generates points for a geoborder i.e. a line following the road, etc.
     * <p>
     * Not implemented.
     */
    protected ShapeAsPointsBuilder FNT() {
        return this;
    }

    /**
     * Returns previously generated points set.
     */
    public List<GeoPoint> toList() {
        return points;
    }

}
