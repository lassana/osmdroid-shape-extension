package com.github.lassana.osmdroid_shapes_sample;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.github.lassana.osmdroid_shape_extention.ShapeAsPointsBuilder;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

import java.util.List;

/**
 * @author Nikolai Doronin {@literal <lassana.nd@gmail.com>}
 * @since 4/1/16.
 */
public enum SampleGeoData {

    FIRST, SECOND, THIRD, FOURTH, FIFTH;

    public List<GeoPoint> getData() {
        final ShapeAsPointsBuilder builder = new ShapeAsPointsBuilder();
        switch (this.ordinal()) {
            case 0:
                builder.GRC(new GeoPoint(-36.76736, 174.83433))
                        .CWA(new GeoPoint(-36.93842, 174.55269), new GeoPoint(-36.87200, 174.48986), new GeoPoint(-36.80550, 174.42714))
                        .CCA(new GeoPoint(-36.68503, 174.62581), new GeoPoint(-36.66514, 174.64464), new GeoPoint(-36.68342, 174.66586))
                        .CCA(new GeoPoint(-36.66372, 174.69231), new GeoPoint(-36.64542, 174.67103), new GeoPoint(-36.65486, 174.69992))
                        .GRC(new GeoPoint(-36.66072, 174.74381))
                        .CWA(new GeoPoint(-36.61911, 174.79094), new GeoPoint(-36.70106, 174.77139), new GeoPoint(-36.76736, 174.83433));
                break;
            case 1:
                builder.CIR(new GeoPoint(-37.63036, 176.17192), 0.41 * 1852);
                break;
            case 2:
                builder.GRC(new GeoPoint(-37.68375, 175.31544))
                        .GRC(new GeoPoint(-37.78622, 175.30456))
                        .GRC(new GeoPoint(-37.85433, 175.30497))
                        .GRC(new GeoPoint(-37.83211, 175.23553))
                        .CWA(new GeoPoint(-37.68842, 175.28242), new GeoPoint(-37.84936, 175.33861), new GeoPoint(-37.68375, 175.31544));
                break;
            case 3:
                builder.CWA(new GeoPoint(-36.47342, 175.11544), new GeoPoint(-37.00453, 174.81372), new GeoPoint(-37.30819, 175.43739))
                        .GRC(new GeoPoint(-37.30819, 175.43739))
                        .GRC(new GeoPoint(-37.48728, 175.36483))
                        .GRC(new GeoPoint(-37.51325, 175.32081))
                        .GRC(new GeoPoint(-37.53881, 175.23578))
                        .GRC(new GeoPoint(-37.56194, 175.15847))
                        .CWA(new GeoPoint(-37.57336, 174.9815), new GeoPoint(-37.00453, 174.81372), new GeoPoint(-37.30016, 174.18424))
                        .GRC(new GeoPoint(-37.30016, 174.18424))
                        .CCA(new GeoPoint(-37.23606, 174.38047), new GeoPoint(-37.00453, 174.81372), new GeoPoint(-36.61017, 174.98258))
                        .CCA(new GeoPoint(-36.61017, 174.98258), new GeoPoint(-36.78679, 174.63123), new GeoPoint(-36.95164, 174.26911))
                        .GRC(new GeoPoint(-36.95164, 174.26911))
                        .CWA(new GeoPoint(-36.96623, 174.04694), new GeoPoint(-36.78679, 174.63123), new GeoPoint(-36.47342, 175.11544))
                        .GRC(new GeoPoint(-36.47342, 175.11544));
                break;
            case 4:
                builder.CCA(new GeoPoint(-36.95164, 174.26911), new GeoPoint(-36.78679, 174.63123), new GeoPoint(-36.98448, 174.29329))
                        .CCA(new GeoPoint(-36.98448, 174.29329), new GeoPoint(-37.00453, 174.81372), new GeoPoint(-37.23606, 174.38047))
                        .GRC(new GeoPoint(-37.23606, 174.38047))
                        .CWA(new GeoPoint(-37.30016, 174.18424), new GeoPoint(-37.00453, 174.81372), new GeoPoint(-37.03146, 174.08486))
                        .CWA(new GeoPoint(-37.03146, 174.08486), new GeoPoint(-36.78679, 174.63123), new GeoPoint(-36.96623, 174.04694))
                        .GRC(new GeoPoint(-36.96623, 174.04694))
                        .GRC(new GeoPoint(-36.95164, 174.26911));
                break;
            default:
                break;
        }
        return builder.toList();
    }

    public int getZoomLevel() {
        switch (this.ordinal()) {
            case 0:
                return 10;
            case 1:
                return 15;
            case 2:
                return 11;
            case 3:
                return 9;
            case 4:
                return 10;
            default:
                return 0;
        }

    }

    public IGeoPoint getCenter() {
        switch (this.ordinal()) {
            case 0:
                return new GeoPoint(-36.814782d, 174.622192d);
            case 1:
                return new GeoPoint(-37.630360d, 176.171920d);
            case 2:
                return new GeoPoint(-37.788624d, 175.273132d);
            case 3:
                return new GeoPoint(-36.982809d, 174.726562d);
            case 4:
                return new GeoPoint(-37.155938d, 174.195098d);
            default:
                return null;
        }
    }

    public float getStrokeWidth() {
        return 2f;
    }

    @ColorInt
    public int getStrokeColor() {
        switch (this.ordinal()) {
            case 0:
                return Color.MAGENTA;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.DKGRAY;
            default:
                return Color.TRANSPARENT;
        }
    }

    @ColorInt
    public int getFillColor() {
        switch (this.ordinal()) {
            case 0:
                return 0x60FF00FF;
            case 1:
                return 0x600000FF;
            case 2:
                return 0x6000FFFF;
            case 3:
                return 0x6000FF00;
            case 4:
                return 0x60444444;
            default:
                return Color.TRANSPARENT;
        }
    }
}
