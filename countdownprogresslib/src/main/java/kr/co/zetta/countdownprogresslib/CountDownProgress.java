package kr.co.zetta.countdownprogresslib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class CountDownProgress extends RelativeLayout {
    private final int STATE_IDLE = 0;
    private final int STATE_START = 1;
    private final int STATE_PAUSE = 2;
    private final int STATE_RESTART = 3;

    private int progressCustomDrawable;

    private int mCntState = 0; // current countdown state

    private ProgressBar mProgressBar;
    private TextView mTextView;

    private CountDownTimer mCountDownTimer;
    private CountDownFinishListener mListener;

    private long totalTime = 10000L;
    private long mRunningTime = 0L;
    private long mRemainTime = 0L;

    public CountDownProgress(Context context) {
        super(context);
        initView();
    }

    public CountDownProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CountDownProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        getAttrs(attrs, defStyleAttr);
    }

    /** init CountDownProgress Layout*/
    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View view = li.inflate(R.layout.layout_countdown_progress, this ,false);
        addView(view);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_countdown);
        mTextView = (TextView) findViewById(R.id.tv_progress_inside_message);
     }

    /** get attrs value */
    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownProgressLayout);
        setTypeArray(typedArray);
    }

    /** get attrs value */
    private void getAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownProgressLayout, defStyleAttr, 0);
        setTypeArray(typedArray);
    }

    /** set custom view value by attr value */
    private void setTypeArray(TypedArray typedArray) {
        // set progress custom drawable (If not set, set default drawable)
        progressCustomDrawable = typedArray.getResourceId(R.styleable.CountDownProgressLayout_progressDrawableCustom, R.drawable.progress_default);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            mProgressBar.setProgressDrawable(getResources().getDrawable(progressCustomDrawable, null));
        } else {
            mProgressBar.setProgressDrawable(getResources().getDrawable(progressCustomDrawable));
        }

        // set progress color tint, tintBackground (only default drawable)
        if (progressCustomDrawable == R.drawable.progress_default){
            int progressColorTint = typedArray.getColor(R.styleable.CountDownProgressLayout_progressColorTint, Color.parseColor("#8A3CC4"));
            int progressColorTintBg = typedArray.getColor(R.styleable.CountDownProgressLayout_progressColorTintBg, Color.parseColor("#FFFFFF"));

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                mProgressBar.setProgressTintList(ColorStateList.valueOf(progressColorTint));
                mProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(progressColorTintBg));
            } else {
                LayerDrawable progressBarDrawable = (LayerDrawable) mProgressBar.getProgressDrawable();
                Drawable bgDrawable = progressBarDrawable.getDrawable(0);
                Drawable tintDrawable = progressBarDrawable.getDrawable(1);

                bgDrawable.setColorFilter(progressColorTintBg, PorterDuff.Mode.SRC_IN);
                tintDrawable.setColorFilter(progressColorTint,  PorterDuff.Mode.SRC_IN);
            }
        }

        // set progress radius value (only default drawable)
        if (progressCustomDrawable == R.drawable.progress_default){
            float progressRadius = typedArray.getFloat(R.styleable.CountDownProgressLayout_progressRadius, 8.0f);

            LayerDrawable drawable = (LayerDrawable) mProgressBar.getProgressDrawable();
            GradientDrawable bgDrawable = (GradientDrawable) drawable.getDrawable(0);
            bgDrawable.setCornerRadius(progressRadius);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                GradientDrawable tintDrawable = (GradientDrawable) ((ClipDrawable) drawable.getDrawable(1)).getDrawable();
                tintDrawable.setCornerRadius(progressRadius);
            } else {
                GradientDrawable tintDrawableApi23 = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.progress_tint);
                tintDrawableApi23.setCornerRadius(progressRadius);
            }

            mProgressBar.setProgressDrawable(drawable);
        }

        // set progress inside text, textSize, textColor
        String progressInsideTextMsg = typedArray.getString(R.styleable.CountDownProgressLayout_progressText);
        int progressInsideTextSize = typedArray.getInt(R.styleable.CountDownProgressLayout_progressTextSize, 12);
        int progressInsideTextColor = typedArray.getColor(R.styleable.CountDownProgressLayout_progressTextColor, Color.parseColor("#000000"));
        mTextView.setText(progressInsideTextMsg);
        mTextView.setTextSize(progressInsideTextSize);
        mTextView.setTextColor(progressInsideTextColor);

        // typedArray recycle
        typedArray.recycle();
    }

    /** programmatically ProgressBar Tint Background (only default drawable) */
    public void setProgressColorTintBg(String colorValue){
        if (colorValue != null){
            if (progressCustomDrawable == R.drawable.progress_default){
                int colorParse = Color.parseColor(colorValue);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                    mProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(colorParse));
                } else {
                    LayerDrawable progressBarDrawable = (LayerDrawable) mProgressBar.getProgressDrawable();
                    Drawable bgDrawable = progressBarDrawable.getDrawable(0);
                    bgDrawable.setColorFilter(colorParse, PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }

    /** programmatically ProgressBar Tint (only default drawable) */
    public void setProgressColorTint(String colorValue){
        if (colorValue != null){
            if (progressCustomDrawable == R.drawable.progress_default){
                int colorParse = Color.parseColor(colorValue);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                    mProgressBar.setProgressTintList(ColorStateList.valueOf(colorParse));
                } else {
                    LayerDrawable progressBarDrawable = (LayerDrawable) mProgressBar.getProgressDrawable();
                    Drawable tintDrawable = progressBarDrawable.getDrawable(1);
                    tintDrawable.setColorFilter(colorParse, PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }

    /** programmatically ProgressBar Tint (only default drawable) */
    public void setProgressRadius(float radius){
        if (progressCustomDrawable == R.drawable.progress_default){
            LayerDrawable drawable = (LayerDrawable) mProgressBar.getProgressDrawable();
            GradientDrawable bgDrawable = (GradientDrawable) drawable.getDrawable(0);
            bgDrawable.setCornerRadius(radius);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                GradientDrawable tintDrawable = (GradientDrawable) ((ClipDrawable) drawable.getDrawable(1)).getDrawable();
                tintDrawable.setCornerRadius(radius);
            } else {
                GradientDrawable tintDrawableApi23 = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.progress_tint);
                tintDrawableApi23.setCornerRadius(radius);
            }

            mProgressBar.setProgressDrawable(drawable);
        }
    }

    /** programmatically TextView set text */
    public void setProgressText(String message){
        mTextView.setText(message);
    }

    /** programmatically TextView set text size */
    public void setProgressTextSize(int textSize){
        mTextView.setTextSize(textSize);
    }

    /** programmatically TextView set text color (int) */
    public void setProgressTextColor(int colorValue){
        mTextView.setTextColor(colorValue);
    }

    /** programmatically TextView set text color (String) */
    @SuppressLint("ResourceType")
    public void setProgressTextColor(String colorValue){
        if (colorValue != null){
            int colorParse = Color.parseColor(colorValue);
            mTextView.setText(colorParse);
        }
    }

    /** countdown finish interface */
    public interface CountDownFinishListener {
        void onFinished();
        void onPause(long runningTime, long remainTime, int currentPos, double currentPercent);
    }

    /** countdown finish listener event */
    public void setOnCountDownFinishEvent(CountDownFinishListener listener){
        this.mListener = listener;
    }

    /** countdown start (default time 10sec) */
    public void onStart() {
        if (mCntState == STATE_START || mCntState == STATE_RESTART){
            onCancel();
        }

        mProgressBar.setMax((int) 10000 / 16);
        mCountDownTimer = new CountDownTimer(10000, 16) {
            @Override
            public void onTick(long millisUntilFinished) {
                mRemainTime = millisUntilFinished;
                mRunningTime = (int) (10000 - millisUntilFinished);

                mProgressBar.setProgress((int) ((10000 - millisUntilFinished) / 16));
            }

            @Override
            public void onFinish() {
                mListener.onFinished();

                mProgressBar.setProgress(0);
                mRemainTime = 0;
                mRunningTime = 0;

                mCountDownTimer = null;
                mCntState = STATE_IDLE;
            }
        };

        mCountDownTimer.start();
        mCntState = STATE_START;
    }

    /** countdown start (param time) */
    public void onStart(long time){
        if (mCntState == STATE_START || mCntState == STATE_RESTART){
            onCancel();
        }

        totalTime = time;

        mProgressBar.setMax((int) time / 16);
        mCountDownTimer = new CountDownTimer(time,16) {
            @Override
            public void onTick(long millisUntilFinished) {
                mRemainTime = millisUntilFinished;
                mRunningTime = (int) (time - millisUntilFinished);

                mProgressBar.setProgress((int) ((time - millisUntilFinished) / 16));
            }

            @Override
            public void onFinish() {
                mListener.onFinished();

                mProgressBar.setProgress(0);
                mRemainTime = 0;
                mRunningTime = 0;

                mCountDownTimer = null;
                mCntState = STATE_IDLE;
            }
        };

        mCountDownTimer.start();
        mCntState = STATE_START;
    }

    /** countdown start with start progress position value */
    public void onStartWithStartPos(long time, double startPos){
        if (mCntState == STATE_START || mCntState == STATE_RESTART){
            onCancel();
        }

        totalTime = time;

        int timeMax = ((int) time / 16);
        int startPercent = (int) (timeMax * startPos);
        mProgressBar.setMax(startPercent + timeMax);
        mProgressBar.setProgress(startPercent);

        mCountDownTimer = new CountDownTimer(time , 16) {
            @Override
            public void onTick(long millisUntilFinished) {
                mRemainTime = millisUntilFinished;
                mRunningTime = (int) (time - millisUntilFinished);

                int count = startPercent + (int) ((time - millisUntilFinished) / 16);
                mProgressBar.setProgress(count);
            }

            @Override
            public void onFinish() {
                mListener.onFinished();

                mProgressBar.setProgress(0);
                mRemainTime = 0;
                mRunningTime = 0;

                mCountDownTimer = null;
                mCntState = STATE_IDLE;
            }
        };

        mCountDownTimer.start();
        mCntState = STATE_START;
    }

    /** countdown timer pause */
    public void onPause(){
        if (mCntState == STATE_START || mCntState == STATE_RESTART){
            mCountDownTimer.cancel();
            mCountDownTimer = null;

            int currentPos = mProgressBar.getProgress();
            double currentPercent = currentPos / (mProgressBar.getMax() * 0.01);

            mListener.onPause(mRunningTime, mRemainTime, currentPos, currentPercent);
            mCntState = STATE_PAUSE;
        }
    }

    /** countdown timer restart (before pause) */
    public void onRestart(){
        if (mCntState == STATE_PAUSE && mRemainTime != 0){
            int currentProgress = mProgressBar.getProgress();
            long currentRemainTime = mRemainTime;

            mCountDownTimer = new CountDownTimer(currentRemainTime, 16) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mRemainTime = millisUntilFinished;
                    mRunningTime = totalTime - millisUntilFinished;

                    mProgressBar.setProgress((int) (currentProgress + (currentRemainTime - millisUntilFinished) / 16));
                }

                @Override
                public void onFinish() {
                    mListener.onFinished();

                    mProgressBar.setProgress(0);
                    mRemainTime = 0;
                    mRunningTime = 0;

                    mCountDownTimer = null;
                    mCntState = STATE_IDLE;
                }
            };

            mCountDownTimer.start();
            mCntState = STATE_RESTART;
        }
    }

    /** countdown timer restart (before pause) */
    public void onRestart(long remainTime){
        if (mCntState == STATE_PAUSE){
            int currentProgress = mProgressBar.getProgress();
            mRemainTime = remainTime;

            mCountDownTimer = new CountDownTimer(remainTime, 16) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mRemainTime = millisUntilFinished;
                    mRunningTime = (int) totalTime - millisUntilFinished;

                    mProgressBar.setProgress((int) (currentProgress + (remainTime - millisUntilFinished) / 16));
                }

                @Override
                public void onFinish() {
                    mListener.onFinished();

                    mProgressBar.setProgress(0);
                    mRemainTime = 0;
                    mRunningTime = 0;

                    mCountDownTimer = null;
                    mCntState = STATE_IDLE;
                }
            };

            mCountDownTimer.start();
            mCntState = STATE_RESTART;
        }
    }

    /** countdown timer restart (before pause) */
    public void onRestart(long remainTime, int cntProgress){
        if (mCntState == STATE_PAUSE){
            mProgressBar.setProgress(cntProgress);
            mCountDownTimer = new CountDownTimer(remainTime, 16) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mRemainTime = millisUntilFinished;
                    mRunningTime = (int) (totalTime - remainTime) - millisUntilFinished;

                    mProgressBar.setProgress((int) (cntProgress + (remainTime - millisUntilFinished) / 16));
                }

                @Override
                public void onFinish() {
                    mListener.onFinished();

                    mProgressBar.setProgress(0);
                    mRemainTime = 0;
                    mRunningTime = 0;

                    mCountDownTimer = null;
                    mCntState = STATE_IDLE;
                }
            };

            mCountDownTimer.start();
            mCntState = STATE_RESTART;
        }
    }

    /** countdown timer cancel */
    public void onCancel(){
        if (mCntState == STATE_START || mCntState == STATE_RESTART){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }

        mProgressBar.setProgress(0);
        mRemainTime = 0;
        mRunningTime = 0;

        mCntState = STATE_IDLE;
    }

    /** countdown timer finish */
    public void onSkip(){
        if (mCntState == STATE_START || mCntState == STATE_RESTART){
            mCountDownTimer.cancel();
            mCountDownTimer.onFinish();
        }
    }

    /** countdown timer release */
    public void onRelease(){
        if (mCntState == STATE_START || mCntState == STATE_RESTART){
            mCountDownTimer.cancel();
        }

        mProgressBar.setProgress(0);
        mRemainTime = 0;
        mRunningTime = 0;

        mCntState = STATE_IDLE;

        mCountDownTimer = null;
    }
}
