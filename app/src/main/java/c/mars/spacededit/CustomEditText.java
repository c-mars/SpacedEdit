package c.mars.spacededit;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Constantine Mars on 6/10/15.
 */
public class CustomEditText extends EditText {
    private static final String JOINER = "   ";
    private static final long DELAY=800;
    private Paint p;
    private Paint curP;
    private boolean hideCursor;
    private Runnable cursorAnimation;


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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setTextSize(getTextSize());

        curP = new Paint(Paint.ANTI_ALIAS_FLAG);
        curP.setStyle(Paint.Style.FILL_AND_STROKE);
        curP.setColor(getHighlightColor());
        curP.setStrokeWidth(getResources().getDimension(R.dimen.cw));

        final Handler h=new Handler();
        cursorAnimation=new Runnable() {
            public void run() {
                if (hideCursor){
                    curP.setAlpha(0);
                    return;
                }
                int newAlpha = (curP.getAlpha() == 0) ? 255 : 0;
                curP.setAlpha(newAlpha);
                postInvalidate();
                h.postDelayed(this, DELAY);
            }
        };

        h.postDelayed(cursorAnimation, DELAY);
        hideCursor=false;

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    hideCursor=false;
                    h.postDelayed(cursorAnimation, DELAY);
                }else {
                    hideCursor = true;
                }
                postInvalidate();
            }
        });

        setFilters(new InputFilter[]{new InputFilter.AllCaps()});
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
                s+= JOINER;
            }
            s+=a[i];
        }
        float y=canvas.getHeight()*0.7f;
        canvas.drawText(s, 0, y, p);
        return s;
    }

    private void drawCursor(Canvas canvas, String s){
        if (hideCursor){
             return;
        }

        float cs=canvas.getHeight()*0.25f;
        float ct=canvas.getHeight()*0.75f;
        float x = (s.length()==0 ? 10 : p.measureText(s));
        Rect b = new Rect();
        p.getTextBounds(s, 0, s.length(), b);

        canvas.drawLine(x, cs, x, ct, curP);
    }

}
