package com.github.lassana.osmbonuspack_gridpolygon_extention_lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.util.Log;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

/**
 * @author Nikolai Doronin {@literal <lassana.nd@gmail.com>}
 * @since 4/4/16.
 */
public class GridPolygon extends Polygon {

    private BitmapShader bitmapShader;
    private IGeoPoint lastCenterGeoPoint;
    private int xOffset = 0;
    private int yOffset = 0;

    public GridPolygon(Context ctx) {
        super(ctx);
    }

    public void setPatternBMP(@NonNull final Bitmap patternBMP) {
        bitmapShader = new BitmapShader(patternBMP, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mFillPaint.setShader(bitmapShader);
    }

    protected void recalculateMatrix(@NonNull final MapView mapView) {
        //final int mapSize = TileSystem.MapSize(mapView.getZoomLevel());

        final Projection projection = mapView.getProjection();
        final IGeoPoint geoPoint = mapView.getMapCenter();
        if (lastCenterGeoPoint == null) lastCenterGeoPoint = geoPoint;

        Log.d("GridPolygon", "geoPoint.getLatitude() =" + geoPoint.getLatitude());
        Log.d("GridPolygon", "geoPoint.getLongitude() =" + geoPoint.getLongitude());
        Log.d("GridPolygon", "lastCenterGeoPoint.getLatitude() =" + lastCenterGeoPoint.getLatitude());
        Log.d("GridPolygon", "lastCenterGeoPoint.getLongitude() = " + lastCenterGeoPoint.getLongitude());

        final Point point = projection.toPixels(geoPoint, null);
        final Point lastCenterPoint = projection.toPixels(lastCenterGeoPoint, null);

        Log.d("GridPolygon", "point.x = " + point.x);
        Log.d("GridPolygon", "point.y = " + point.y);
        Log.d("GridPolygon", "lastCenterPoint.x = " + lastCenterPoint.x);
        Log.d("GridPolygon", "lastCenterPoint.y = " + lastCenterPoint.y);

        xOffset += lastCenterPoint.x - point.x;
        yOffset += lastCenterPoint.y - point.y;

        xOffset %= 100; // 100 is pixel size of shader image
        yOffset %= 100;

        final Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setScale(1,1);
        matrix.preTranslate(xOffset, yOffset);
        //matrix.setTranslate(xOffset, yOffset);
        bitmapShader.setLocalMatrix(matrix);

        Log.d("GridPolygon", "xOffset = " + xOffset);
        Log.d("GridPolygon", "yOffset = " + yOffset);

        mFillPaint.setShader(bitmapShader);

        lastCenterGeoPoint = geoPoint;
    }

    @Override
    protected void draw(Canvas canvas, MapView mapView, boolean shadow) {
        recalculateMatrix(mapView);
        super.draw(canvas, mapView, shadow);
    }
}
