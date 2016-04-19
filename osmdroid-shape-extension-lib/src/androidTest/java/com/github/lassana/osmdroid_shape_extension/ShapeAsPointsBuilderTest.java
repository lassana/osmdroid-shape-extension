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
 * @since 4/19/16.
 */
@RunWith(AndroidJUnit4.class)
public class ShapeAsPointsBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void testWithStep() {
        final ShapeAsPointsBuilder builder = new ShapeAsPointsBuilder();
        builder.withStep(-1);
    }


    @Test
    public void testGRC() {
        final ShapeAsPointsBuilder builder = new ShapeAsPointsBuilder();
        builder.GRC(new GeoPoint(52, 23));
        final List<GeoPoint> geoPoints = builder.toList();
        assertEquals(geoPoints.size(), 1);
        assertEquals(geoPoints.get(0), new GeoPoint(52, 23));
    }

    @Test
    public void testCWA() {
        final ShapeAsPointsBuilder builder = new ShapeAsPointsBuilder();
        builder.CWA(new GeoPoint(-36.93842, 174.55269), new GeoPoint(-36.87200, 174.48986), new GeoPoint(-36.80550, 174.42714));
        final List<GeoPoint> geoPoints = builder.toList();
        assertTrue(geoPoints.size() >= 3);
        assertEquals(geoPoints.get(0), new GeoPoint(-36.93842, 174.55269));
        assertEquals(geoPoints.get(geoPoints.size()-1), new GeoPoint(-36.80550, 174.42714));
    }

    @Test
    public void testCCA() {
        final ShapeAsPointsBuilder builder = new ShapeAsPointsBuilder();
        builder.CWA(new GeoPoint(-37.23606, 174.38047), new GeoPoint(-37.00453, 174.81372), new GeoPoint(-36.61017, 174.98258));
        final List<GeoPoint> geoPoints = builder.toList();
        assertTrue(geoPoints.size() >= 3);
        assertEquals(geoPoints.get(0), new GeoPoint(-37.23606, 174.38047));
        assertEquals(geoPoints.get(geoPoints.size()-1), new GeoPoint(-36.61017, 174.98258));
    }

    @Test
    public void testCIR() {
        final ShapeAsPointsBuilder builder = new ShapeAsPointsBuilder();
        final GeoPoint centerPoint = new GeoPoint(-37.63036, 176.17192);
        final double radius = 0.41 * 1852;
        builder.CIR(centerPoint, radius);
        final List<GeoPoint> geoPoints = builder.toList();
        assertTrue(geoPoints.size() >= 4);
        for (GeoPoint geoPoint : geoPoints) {
            assertEquals(geoPoint.distanceTo(centerPoint), radius, 10);
        }
    }

    @Test
    public void testToList() {
        final List<GeoPoint> inputList = new ArrayList<>();
        inputList.add(new GeoPoint(52, 23));
        final ShapeAsPointsBuilder builder = new ShapeAsPointsBuilder(inputList);
        final List<GeoPoint> geoPoints = builder.toList();
        assertEquals(geoPoints.size(), 1);
        assertEquals(geoPoints.get(0), new GeoPoint(52, 23));
    }


    @Test
    public void testToList_emptySet() {
        final ShapeAsPointsBuilder builder = new ShapeAsPointsBuilder();
        final List<GeoPoint> geoPoints = builder.toList();
        assertEquals(geoPoints.size(), 0);
    }
}