package c.mars.spacededit;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by mars on 6/9/15.
 */
public class SpacedEdit extends LinearLayout {

    @InjectView(R.id.e1)
    EditText e1;
    @InjectView(R.id.e2)
    EditText e2;
    @InjectView(R.id.e3)
    EditText e3;
    @InjectView(R.id.e4)
    EditText e4;

    public SpacedEdit(Context context) {
        super(context);
        init();
    }

    public SpacedEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpacedEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpacedEdit(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.spaced_edit, this);
        ButterKnife.inject(this);
        e3.setText("Injected!");
    }

}
