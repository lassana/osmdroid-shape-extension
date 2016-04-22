package com.github.lassana.osmdroid_shape_extension;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

/**
 * @author Nikolai Doronin {@literal <lassana.nd@gmail.com>}
 * @since 4/4/16.
 */
public class BitmapPolygon extends Polygon {

    private BitmapShader bitmapShader;
    private IGeoPoint lastCenterGeoPoint;
    private int xOffset = 0;
    private int yOffset = 0;
    private int height;
    private int width;

    public BitmapPolygon(Context ctx) {
        super(ctx);
    }

    public void setPatternBitmap(@NonNull final Bitmap patternBitmap) {
        height = patternBitmap.getHeight();
        width = patternBitmap.getWidth();
        bitmapShader = new BitmapShader(patternBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mFillPaint.setShader(bitmapShader);
    }

    protected void recalculateMatrix(@NonNull final MapView mapView) {
        //final int mapSize = TileSystem.MapSize(mapView.getZoomLevel());

        final Projection projection = mapView.getProjection();
        final IGeoPoint geoPoint = mapView.getMapCenter();
        if (lastCenterGeoPoint == null) lastCenterGeoPoint = geoPoint;

        /*
        Log.d("BitmapPolygon", "geoPoint.getLatitude() =" + geoPoint.getLatitude());
        Log.d("BitmapPolygon", "geoPoint.getLongitude() =" + geoPoint.getLongitude());
        Log.d("BitmapPolygon", "lastCenterGeoPoint.getLatitude() =" + lastCenterGeoPoint.getLatitude());
        Log.d("BitmapPolygon", "lastCenterGeoPoint.getLongitude() = " + lastCenterGeoPoint.getLongitude());
        */

        final Point point = projection.toPixels(geoPoint, null);
        final Point lastCenterPoint = projection.toPixels(lastCenterGeoPoint, null);

        /*
        Log.d("BitmapPolygon", "point.x = " + point.x);
        Log.d("BitmapPolygon", "point.y = " + point.y);
        Log.d("BitmapPolygon", "lastCenterPoint.x = " + lastCenterPoint.x);
        Log.d("BitmapPolygon", "lastCenterPoint.y = " + lastCenterPoint.y);
        */

        xOffset += lastCenterPoint.x - point.x;
        yOffset += lastCenterPoint.y - point.y;

        xOffset %= width;
        yOffset %= height;

        final Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setScale(1,1);
        matrix.preTranslate(xOffset, yOffset);
        //matrix.setTranslate(xOffset, yOffset);
        bitmapShader.setLocalMatrix(matrix);

        /*
        Log.d("BitmapPolygon", "xOffset = " + xOffset);
        Log.d("BitmapPolygon", "yOffset = " + yOffset);
        */

        mFillPaint.setShader(bitmapShader);

        lastCenterGeoPoint = geoPoint;
    }

    @Override
    protected void draw(Canvas canvas, MapView mapView, boolean shadow) {
        recalculateMatrix(mapView);
        super.draw(canvas, mapView, shadow);
    }
}
