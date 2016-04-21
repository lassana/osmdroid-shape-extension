package com.github.lassana.osmdroid_shape_extension;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Nikolai Doronin {@literal <lassana.nd@gmail.com>}
 * @since 4/22/16.
 */
@RunWith(AndroidJUnit4.class)
public class PolygonCentroidTest {

    @Test
    public void testCentroid() {
        final List<GeoPoint> points = new ArrayList<>();
        points.add(new GeoPoint(40d, 20d));
        points.add(new GeoPoint(40d, 40d));
        points.add(new GeoPoint(20d, 40d));
        points.add(new GeoPoint(20d, 20d));
        final GeoPoint centroid = new PolygonCentroid(points).centroid();
        assertEquals(centroid.getLatitude(), 30d, 0.0001d);
        assertEquals(centroid.getLongitude(), 30d, 0.0001d);
    }

    @Test
    public void testCentroid_negative() {
        final List<GeoPoint> points = new ArrayList<>();
        points.add(new GeoPoint(-40d, -20d));
        points.add(new GeoPoint(-40d, -40d));
        points.add(new GeoPoint(-20d, -40d));
        points.add(new GeoPoint(-20d, -20d));
        final GeoPoint centroid = new PolygonCentroid(points).centroid();
        assertEquals(centroid.getLatitude(), -30d, 0.0001d);
        assertEquals(centroid.getLongitude(), -30d, 0.0001d);
    }

    @Test
    public void testCentroid_empty() {
        final GeoPoint centroid = new PolygonCentroid(new ArrayList<GeoPoint>()).centroid();
        assertEquals(centroid.getLatitude(), 0d, 0.0001d);
        assertEquals(centroid.getLongitude(), 0d, 0.0001d);
    }
}