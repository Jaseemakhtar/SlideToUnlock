package jsync.com.slidetounlock;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by jass on 5/7/18.
 */

public class SwipeButton extends RelativeLayout {

    private ImageView slidingButton;
    private float initialX;
    private boolean active;
    private int intialButtonWidth;
    private TextView centerText;
    private Drawable disabledDrawable;
    private Drawable enableDrawable;


    public SwipeButton(Context context) {
        super(context);
        init(context, null, -1, -1);
    }

    public SwipeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1, -1);
    }

    public SwipeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, -1);
    }

    @TargetApi(21)
    public SwipeButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        initialX = 0;
        RelativeLayout background = new RelativeLayout(context);
        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        background.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_rounded));
        addView(background,layoutParams);

        final TextView centerText = new TextView(context);
        this.centerText = centerText;

        LayoutParams layoutParamsCenterText = new LayoutParams(
               ViewGroup.LayoutParams.WRAP_CONTENT,
               ViewGroup.LayoutParams.WRAP_CONTENT
       );
       layoutParamsCenterText.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
       centerText.setText("SWIPE");
       centerText.setTextColor(Color.WHITE);
       centerText.setPadding(35,35,35,35);

       background.addView(centerText,layoutParamsCenterText);

       final ImageView slidingButton =  new ImageView(context);
       this.slidingButton = slidingButton;

       disabledDrawable = ContextCompat.getDrawable(context,R.drawable.ic_lock_open_black_24dp);
       enableDrawable = ContextCompat.getDrawable(context,R.drawable.ic_lock_outline_black_24dp);

        slidingButton.setImageDrawable(disabledDrawable);
        slidingButton.setPadding(40,40,40,40);

        LayoutParams layoutParamsButton = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParamsButton.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);

        slidingButton.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_button));

        addView(slidingButton,layoutParamsButton);

        setOnTouchListener(getButtonTouchListener());
    }

    private OnTouchListener getButtonTouchListener() {
        return new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_UP:

                        return true;

                    case MotionEvent.ACTION_DOWN:
                        //Log.i("MotionEvent","Action Down");
                        return true;

                    case MotionEvent.ACTION_MOVE:

                        if (initialX == 0){
                            initialX = slidingButton.getX();
                        }

                        if (event.getX() > initialX + slidingButton.getWidth() / 2 && event.getX() + slidingButton.getWidth() / 2 < getWidth()){
                            slidingButton.setX(event.getX() - slidingButton.getWidth() /2);
                            centerText.setAlpha( 1 - 1.3f * (slidingButton.getX() + slidingButton.getWidth()) / getWidth());
                        }

                        if (event.getX() + slidingButton.getWidth() / 2 > getWidth() && slidingButton.getX() + slidingButton.getWidth() /2 < getWidth()){
                            slidingButton.setX(getWidth() - slidingButton.getWidth());
                        }

                        if (event.getX() < slidingButton.getWidth() / 2 && slidingButton.getX() > 0){
                            slidingButton.setX(0);
                        }
                        return true;
                }
                return false;
            }
        };
    }
}
