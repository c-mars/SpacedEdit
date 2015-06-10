package c.mars.spacededit;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Constantine Mars on 6/10/15.
 */
public class CustomEditText extends EditText {
    private String joiner="   ";
    private Paint p;
    private Paint curP;
    private Thread t;

    // Cursor blink animation
    private Runnable cursorAnimation = new Runnable() {
        public void run() {
            int newAlpha = (curP.getAlpha() == 0) ? 255 : 0;
            curP.setAlpha(newAlpha);
            invalidate();
            postDelayed(cursorAnimation, 500);
        }
    };

    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setTextSize(getTextSize());

        curP = new Paint(Paint.ANTI_ALIAS_FLAG);
        curP.setStyle(Paint.Style.FILL_AND_STROKE);
        curP.setStrokeWidth(getResources().getDimension(R.dimen.cw));

        Handler h=new Handler();
        cursorAnimation=new Runnable() {
            public void run() {
                int newAlpha = (curP.getAlpha() == 0) ? 255 : 0;
                curP.setAlpha(newAlpha);
                postInvalidate();
                postDelayed(this, 500);
            }
        };

        h.postDelayed(cursorAnimation, 500);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        String s=drawText(canvas);
        drawCursor(canvas, s);
    }

    private String drawText(Canvas canvas){
        String os=getText().toString();
        String s="";
        String[] a=os.split("");
        for(int i=0;i<a.length;i++){
            if(i>0){
                s+=joiner;
            }
            s+=a[i];
        }
        float y=canvas.getHeight()*0.7f;
        canvas.drawText(s, 0, y, p);
        return s;
    }

    private void drawCursor(Canvas canvas, String s){
        float cs=canvas.getHeight()*0.2f;
        float ct=canvas.getHeight()*0.8f;
        float x = p.measureText(s);
        Rect b = new Rect();
        p.getTextBounds(s, 0, s.length(), b);

        canvas.drawLine(x, cs, x, ct, curP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



}
