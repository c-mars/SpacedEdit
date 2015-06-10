package c.mars.spacededit;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;

import com.google.common.base.Joiner;

/**
 * Created by Constantine Mars on 6/10/15.
 */
public class CustomSpannableStringBuilder extends SpannableStringBuilder {
//    original is mutable
    private StringBuilder original;
    private String joinerString;

    public CustomSpannableStringBuilder(String original, String joinerString) {
        super(makeDisplay(original, joinerString));

        this.original = new StringBuilder(original);
        this.joinerString=joinerString;
    }

    public String getOriginal() {
        return original.toString();
    }

    public void setOriginal(String original) {
        this.original = new StringBuilder(original);
    }

    @NonNull
    @Override
    public SpannableStringBuilder replace(int start, int end, CharSequence tb) {
        original.replace(start, end, (tb==null?"":tb.toString()));

        int predictedStart=start-joinerString.length();
        String foundJoiner=subSequence(predictedStart, end-1).toString();
        if(foundJoiner.equals(joinerString)) {
            start=predictedStart;
        }
        return super.replace(start, end, tb);
    }

    @NonNull
    @Override
    public SpannableStringBuilder append(CharSequence text) {
        original.append(text);
        String s=joinerString+text.toString();
        return super.append(s);
    }

    private static String makeDisplay(String original, String joinerString){
        return Joiner.on(joinerString).join(original.split(""));
    }
}
