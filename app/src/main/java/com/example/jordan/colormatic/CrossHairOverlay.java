package com.example.jordan.colormatic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Jordan on 11/6/17.
 */

/*
public class CrosshairOverlay extends Overlay {
    public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
        Projection projection = mapView.getProjection();
        Point center = projection.toPixels(mapView.getMapCenter(), null);

        // Customize appearance, should be a fields.
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFF000000);
        p.setStyle(Style.STROKE);
        p.setStrokeWidth(2.0f);
        int innerRadius = 10;
        int outerRadius = 20;

        canvas.drawCircle(center.x, center.y, innerRadius, p);
        canvas.drawCircle(center.x, center.y, outerRadius, p);
        return true;
    }
}
*/
/*
public class CrossHairsOverlay extends Overlay {
    public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
        super.draw(canvas, mapView, shadow);
        GeoPoint centerGp = mapView.getMapCenter();
        Projection projection = mapView.getProjection();
        Point centerPoint = projection.toPixels(centerGp, null);
        Paint p = new Paint();
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.crosshairs_dial);
        canvas.drawBitmap(bmp, centerPoint.x, centerPoint.y, p);
        return true;
    }
}
*/