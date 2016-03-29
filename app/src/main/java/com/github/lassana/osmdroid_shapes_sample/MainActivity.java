package com.github.lassana.osmdroid_shapes_sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.github.lassana.osmdroid_shape_extention.ShapeAsPointsBuilder;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 0x124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        }

        final MapView map = (MapView) findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        final IMapController mapController = map.getController();
        mapController.setZoom(9);
        final GeoPoint startPoint = new GeoPoint(-36.66372, 174.69231);
        mapController.setCenter(startPoint);

        {
            final List<GeoPoint> list = new ShapeAsPointsBuilder()
                    .GRC(new GeoPoint(-37.68375, 175.31544))
                    .GRC(new GeoPoint(-37.78622, 175.30456))
                    .GRC(new GeoPoint(-37.85433, 175.30497))
                    .GRC(new GeoPoint(-37.83211, 175.23553))
                    .CWA(new GeoPoint(-37.68842, 175.28242),
                         new GeoPoint(-37.84936, 175.33861),
                         new GeoPoint(-37.68375, 175.31544),
                         0d).toList();

            final Polygon polygon = new Polygon(this);
            polygon.setPoints(list);
            polygon.setFillColor(0xA0FF00FF);
            polygon.setStrokeColor(Color.MAGENTA);
            polygon.setStrokeWidth(1f);
            map.getOverlays().add(polygon);
            map.invalidate();
        }

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

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            String message = "OSMDroid permissions:";
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                message += "\nStorage access to store map tiles.";
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                message += "\nLocation to show user location.";
            }
            if (!permissions.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                String[] params = permissions.toArray(new String[permissions.size()]);
                requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:	{
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }
                Boolean location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED;
                Boolean storage  = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED;
                if (location && storage) {
                    Toast.makeText(MainActivity.this, "All permissions granted", Toast.LENGTH_SHORT).show();
                } else if (location) {
                    Toast.makeText(this, "Storage permission is required to store map tiles to reduce data usage " +
                            "and for offline usage.", Toast.LENGTH_LONG).show();
                } else if (storage) {
                    Toast.makeText(this,"Location permission is required to show the user's location on map.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Storage permission is required to store map tiles to reduce " +
                            "data usage and for offline usage." +
                            "\nLocation permission is required to show the user's location on map.",
                            Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
