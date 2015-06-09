package c.mars.spacededit;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
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
    @InjectView(R.id.e5)
    EditText e5;

    EditText[] e;

    public void setFilledListener(OnFilledListener filledListener) {
        this.filledListener = filledListener;
    }

    OnFilledListener filledListener;

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

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        e1.requestFocus();
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    private void init(){
        inflate(getContext(), R.layout.spaced_edit, this);
        ButterKnife.inject(this);

        e=new EditText[] {e1, e2, e3, e4, e5};
//        e1.setOnFocusChangeListener(new OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(e1, 0);
//                }
//            }
//        });
        for (int i=0; i<e.length; i++){
            final int finalI = i;
            e[i].setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            e[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                private boolean updating;
                @Override
                public void afterTextChanged(Editable editable) {
                    if(updating){
                        updating=false;
                        return;
                    }

                    Log.d("c", editable.toString());
                    String s= editable.toString().toUpperCase();
                    updating=true;
                    if(s.length()>0) {
                        editable.replace(0, 1, s);
                    }

                    if (editable.length() == 1) {
                        if (finalI + 1 < e.length) {
                            e[finalI + 1].requestFocus();
                        } else {
                            fillOut();
                        }
                    } else if (editable.length() == 0){
//                        move back
                        if(finalI > 0) {
                            EditText pe =e[finalI - 1];
                            pe.requestFocus();
                            pe.setSelection(pe.getText().length());
                        } else {
                            fillOut();
                        }
                    }
                }
            });
        }
    }

    private void fillOut(){
        String o="";
        for(EditText et:e){
            o+=et.getText();
        }
        filledListener.onFilled(o);
    }

    public interface OnFilledListener {
        void onFilled(String s);
    }

    public interface Validator{
        boolean isValid(String k);
    }

    private class DefaultValidator implements Validator{
        private static final String DEFAULT_PATTERN = "^[A-Fa-f0-9]+$";

        @Override
        public boolean isValid(String k) {
            return k.matches(DEFAULT_PATTERN);
        }
    }

}
