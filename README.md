#osmdroid-extentions

Custom polygons on osmdroid map control.

##[1. Complex shapes on osmdroid map control](osmdroid-shape-extention-lib).

Complex shapes on osmdroid map.

<pre><code><img src="media/device-2016-04-01-195435.png" height="512px" width="384px"> <img src="media/device-2016-04-01-200104.png" height="512px" width="384px"> <img src="media/device-2016-04-01-200128.png" height="512px" width="384px"> <img src="media/device-2016-04-01-200138.png" height="512px" width="384px"> <img src="media/device-2016-04-01-200147.png" height="512px" width="384px"></code></pre>

####Under the hood:

[ShapeAsPointsBuilder.java](osmdroid-shape-extention-lib/src/main/java/com/github/lassana/osmdroid_shape_extention/ShapeAsPointsBuilder.java).

####Usage:

    // I filled the data manually, but it can be done more clever, of course.
    final ShapeAsPointsBuilder shapeBuilder = new ShapeAsPointsBuilder()
            .GRC(new GeoPoint(-36.76736, 174.83433))
            .CWA(new GeoPoint(-36.93842, 174.55269), new GeoPoint(-36.87200, 174.48986), new GeoPoint(-36.80550, 174.42714))
            .CCA(new GeoPoint(-36.68503, 174.62581), new GeoPoint(-36.66514, 174.64464), new GeoPoint(-36.68342, 174.66586))
            .CCA(new GeoPoint(-36.66372, 174.69231), new GeoPoint(-36.64542, 174.67103), new GeoPoint(-36.65486, 174.69992))
            .GRC(new GeoPoint(-36.66072, 174.74381))
            //close shape going to the start point
            .CWA(new GeoPoint(-36.61911, 174.79094), new GeoPoint(-36.70106, 174.77139), new GeoPoint(-36.76736, 174.83433), 0.08333d);

    final Polygon polygon = new Polygon(context);
    polygon.setPoints(shapeBuilder.toList());
    polygon.setFillColor(0x60FF00FF);
    polygon.setStrokeColor(Color.MAGENTA);
    polygon.setStrokeWidth(1f);

    map.getOverlays().add(polygon);
    map.invalidate();

##[2. Drawing a grid inside OSMBonusPack Polygon](osmbonuspack-gridpolygon-extention-lib).

![Screencast](https://i.stack.imgur.com/qtfEG.gif)

####Implementation:

[GridPolygon.java](osmbonuspack-gridpolygon-extention-lib/src/main/java/com/github/lassana/osmbonuspack_gridpolygon_extention_lib/GridPolygon.java).

####Usage:

    final GridPolygon polygon = new GridPolygon(context);
    polygon.setPoints(geoData);
    polygon.setFillColor(fillColor);
    polygon.setStrokeColor(strokeColor);
    polygon.setStrokeWidth(strokeWidth);
    polygon.setPatternBMP(BitmapFactory.decodeResource(getResources(), R.drawable.pattern));
    map.getOverlays().add(polygon);
    map.invalidate();

##License

[LGPLv3](LICENSE).
