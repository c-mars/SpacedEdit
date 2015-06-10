package c.mars.spacededit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.o)
    TextView o;
    @InjectView(R.id.e)
    SpacedEdit e;
    @InjectView(R.id.t)
    EditText t;
    @InjectView(R.id.ce)
    CustomEditText ce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        e.setFilledListener(new SpacedEdit.OnFilledListener() {
            @Override
            public void onFilled(String s) {
                o.setText(s);
            }
        });
        e.requestFocus();

        String os="original";
        final CustomSpannableStringBuilder sb=new CustomSpannableStringBuilder(os, "   ");
        t.setText(sb);
        t.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private int lastLen=t.length();
            @Override
            public void afterTextChanged(Editable s) {
//                if add
                t.removeTextChangedListener(this);
                if(s.length()>lastLen) {
                    if (s.length() > 0) {
                        Character c=s.charAt(s.length() - 1);
                        t.setText(sb.append(c));
                    }
                } else if(s.length()<lastLen && s.length()>=0){
                    t.setText(sb.replace(sb.length() - 1, sb.length() - 1, null));
                }
                lastLen=s.length();
                t.setSelection(lastLen);
                t.addTextChangedListener(this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
