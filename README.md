# A few extensions for [omsdroid](https://github.com/osmdroid/osmdroid)

### 1. Drawing complex shapes on the osmdroid map control

<pre><img src="media/device-2016-04-01-195435.png" height="512px" width="384px"> <img src="media/device-2016-04-01-200104.png" height="512px" width="384px"> <img src="media/device-2016-04-01-200128.png" height="512px" width="384px"> <img src="media/device-2016-04-01-200138.png" height="512px" width="384px"> <img src="media/device-2016-04-01-200147.png" height="512px" width="384px"></pre>

    final ShapeAsPointsBuilder shapeBuilder = new ShapeAsPointsBuilder()
            .GRC(new GeoPoint(-36.76736, 174.83433))
            .CWA(new GeoPoint(-36.93842, 174.55269), new GeoPoint(-36.87200, 174.48986), new GeoPoint(-36.80550, 174.42714))
            .CCA(new GeoPoint(-36.68503, 174.62581), new GeoPoint(-36.66514, 174.64464), new GeoPoint(-36.68342, 174.66586))
            .CCA(new GeoPoint(-36.66372, 174.69231), new GeoPoint(-36.64542, 174.67103), new GeoPoint(-36.65486, 174.69992))
            .GRC(new GeoPoint(-36.66072, 174.74381))
            .CWA(new GeoPoint(-36.61911, 174.79094), new GeoPoint(-36.70106, 174.77139), new GeoPoint(-36.76736, 174.83433));

    final Polygon polygon = new Polygon(context);
    polygon.setPoints(shapeBuilder.toList());
    polygon.setFillColor(0x60FF00FF);
    polygon.setStrokeColor(Color.MAGENTA);
    polygon.setStrokeWidth(1f);

    map.getOverlays().add(polygon);
    map.invalidate();

The [implementation](src/main/java/com/github/lassana/osmdroid_shape_extension/ShapeAsPointsBuilder.java) depends only on [GeographicLib](https://geographiclib.sourceforge.io) and can be easily ported to Google Maps, Mapbox, etc.

### 2. Drawing a bitmap (e.g. a grid) inside of an OSMBonusPack Polygon

[Screencast](https://i.imgur.com/WGVDY8m.gif)

    final BitmapPolygon polygon = new BitmapPolygon(context);
    polygon.setPoints(geoData);
    polygon.setFillColor(fillColor);
    polygon.setStrokeColor(strokeColor);
    polygon.setStrokeWidth(strokeWidth);
    polygon.setPatternBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pattern));
    
    map.getOverlays().add(polygon);
    map.invalidate();

### License

[The FreeBSD license](LICENSE).
