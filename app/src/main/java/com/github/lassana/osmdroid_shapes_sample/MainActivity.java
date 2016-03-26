package com.github.lassana.osmdroid_shapes_sample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.lassana.osmdroid_shape_extention.ShapeAsPointsBuilder;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MapView map = (MapView) findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        final IMapController mapController = map.getController();
        mapController.setZoom(9);
        final GeoPoint startPoint = new GeoPoint(-36.66372, 174.69231);
        mapController.setCenter(startPoint);

        {
            final Polygon polygon = new Polygon(this);
            final ShapeAsPointsBuilder shapeBuilder = new ShapeAsPointsBuilder();

            // I filled the data manually, but it can be done more clever, of course.
            shapeBuilder.GRC(new GeoPoint(-36.76736, 174.83433));
            shapeBuilder.CWA(new GeoPoint(-36.93842, 174.55269),
                             new GeoPoint(-36.87200, 174.48986),
                             new GeoPoint(-36.80550, 174.42714),
                             0.08333d);
            shapeBuilder.CCA(new GeoPoint(-36.68503, 174.62581),
                             new GeoPoint(-36.66514, 174.64464),
                             new GeoPoint(-36.68342, 174.66586),
                             0.02500d);
            shapeBuilder.CCA(new GeoPoint(-36.66372, 174.69231),
                             new GeoPoint(-36.64542, 174.67103),
                             new GeoPoint(-36.65486, 174.69992),
                             0.02500d);
            shapeBuilder.GRC(new GeoPoint(-36.66072, 174.74381));
            shapeBuilder.CWA(new GeoPoint(-36.61911, 174.79094),
                             new GeoPoint(-36.70106, 174.77139),
                             new GeoPoint(-36.76736, 174.83433), //close shape going to the start point
                             0.08333d);

            polygon.setPoints(shapeBuilder.toList());
            polygon.setFillColor(0xA0FF00FF);
            polygon.setStrokeColor(Color.MAGENTA);
            polygon.setStrokeWidth(1f);
            map.getOverlays().add(polygon);
            map.invalidate();
        }

        {
            final Polygon polygon = new Polygon(this);
            final ShapeAsPointsBuilder shapeBuilder = new ShapeAsPointsBuilder();

            shapeBuilder.CIR(new GeoPoint(-37.63036, 176.17192), 0.41 * 1852);

            polygon.setPoints(shapeBuilder.toList());
            polygon.setFillColor(0xA00000FF);
            polygon.setStrokeColor(Color.BLACK);
            polygon.setStrokeWidth(1f);
            map.getOverlays().add(polygon);
            map.invalidate();
        }
    }
}
