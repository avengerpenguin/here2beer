package uk.co.rossfenning.android.here2beer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class RadiusSelectorView extends View {

    private static final float minRadius = 100;
    private static final float maxRadius = 10000;

    private final Paint circlePaint;
    private final ScaleGestureDetector pinchDetector;
    private PubRequest pubRequest = new PubRequest();

    public RadiusSelectorView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        circlePaint = new Paint();

        pinchDetector = new ScaleGestureDetector(context, new RadiusChangeListener());
        setOnDragListener(new OnDragListener() {

            public boolean onDrag(View v, DragEvent event) {
                final double distance
                    = Math.sqrt(event.getX() * event.getX() + event.getY() * event.getY());
                
                float newRadius = (float) (pubRequest.getRadius() + distance);

                newRadius = Math.max(minRadius, Math.min(newRadius, maxRadius));

                pubRequest.setRadius(newRadius);
            
                invalidate();
                return true;
            }
        });
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        this.circlePaint.setColor(
            Color.HSVToColor(new float[]{0.4f * (pubRequest.getRadius() / maxRadius), 0.9f, 0.9f}));
        
        float width = this.getWidth();
        float height = this.getHeight();

        final float maxRoom = Math.min(width / 2, height / 2);

        canvas.drawCircle(width / 2, height / 2, maxRoom * (pubRequest.getRadius() / maxRadius), circlePaint);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        pinchDetector.onTouchEvent(ev);
        return true;
    }

    /**
     * @param pubRequest the pubRequest to set
     */
    public void setPubRequest(final PubRequest pubRequest) {
        this.pubRequest = pubRequest;
    }

    private class RadiusChangeListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(final ScaleGestureDetector detector) {
            float newRadius = pubRequest.getRadius() * detector.getScaleFactor();

            // Don't let the object get too small or too large.
            newRadius = Math.max(minRadius, Math.min(newRadius, maxRadius));

            pubRequest.setRadius(newRadius);
            
            invalidate();

            return true;
        }
    }
}
