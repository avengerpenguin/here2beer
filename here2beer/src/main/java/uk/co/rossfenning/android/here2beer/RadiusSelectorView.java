package uk.co.rossfenning.android.here2beer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class RadiusSelectorView extends View {

    private static final float minRadius = 160.9344f;
    private static final float maxRadius = 8046.72f;

    private final Paint circlePaint;
    private final ScaleGestureDetector pinchDetector;
    private PubRequest pubRequest = new PubRequest();

    private OnRadiusChangeListener onRadiusChangeListener;
    
    public RadiusSelectorView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);

        pinchDetector = new ScaleGestureDetector(context, new CircleScaleListener());
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        float width = this.getWidth();
        float height = this.getHeight();

        final float maxRoom = Math.min(width / 2, height / 2);

        final float hue = 360f * 0.3f * (1 -(pubRequest.getRadius() / maxRadius));
        circlePaint.setColor(
            Color.HSVToColor(new float[]{hue, 0.9f, 0.9f}));

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
    
    public void setOnRadiusChangedListener(final OnRadiusChangeListener onRadiusChangeListener) {
        this.onRadiusChangeListener = onRadiusChangeListener;
    }

    public interface OnRadiusChangeListener {
        
        void onRadiusChanged(float newRadius);
        
    }
    
    private class CircleScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(final ScaleGestureDetector detector) {
            float newRadius = pubRequest.getRadius() * detector.getScaleFactor();

            // Don't let the object get too small or too large.
            newRadius = Math.max(minRadius, Math.min(newRadius, maxRadius));

            pubRequest.setRadius(newRadius);
           
            if (onRadiusChangeListener != null) {
                onRadiusChangeListener.onRadiusChanged(newRadius);
            }
            
            invalidate();

            return true;
        }
    }
}
