package com.github.lassana.osmdroid_shapes_sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_GEO_DATA_INDEX = "MainActivity.EXTRA_GEO_DATA_INDEX";
    private final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 0x124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        }

        final MapView map = (MapView) findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        if (getIntent().hasExtra(EXTRA_GEO_DATA_INDEX)) {
            final int index = getIntent().getIntExtra(EXTRA_GEO_DATA_INDEX, 0);
            final SampleGeoData geoData = SampleGeoData.values()[index];

            final IMapController mapController = map.getController();
            mapController.setZoom(geoData.getZoomLevel());
            mapController.setCenter(geoData.getCenter());

            final Polygon polygon = new Polygon(this);
            polygon.setPoints(geoData.getData());
            polygon.setFillColor(geoData.getFillColor());
            polygon.setStrokeColor(geoData.getStrokeColor());
            polygon.setStrokeWidth(geoData.getStrokeWidth());
            //polygon.setPatternBMP(BitmapFactory.decodeResource(getResources(), com.github.lassana.osmbonuspack_gridpolygon_extention_lib.R.drawable.pattern));
            map.getOverlays().add(polygon);
            map.invalidate();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
