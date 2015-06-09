package c.mars.spacededit;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
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

    EditText[] e;

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

        e=new EditText[] {e1, e2, e3, e4};
        for (int i=0; i<e.length; i++){
            final int finalI = i;
            e[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() == 1 && finalI + 1 < e.length) {
                        e[finalI+1].requestFocus();
                    }
                }
            });
        }
    }

}
