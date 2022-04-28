package kr.co.zetta.countdownprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CountDownProgress mCountDownProgress;
    private Button mStart, mCancel, mPause, mRestart;

    private long mRemainTime = 0;
    private int cntProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mCountDownProgress = (CountDownProgress) findViewById(R.id.layout_custom_countdown);
        mStart = (Button) findViewById(R.id.btn_start);
        mCancel = (Button) findViewById(R.id.btn_cancel);
        mPause = (Button) findViewById(R.id.btn_pause);
        mRestart = (Button) findViewById(R.id.btn_restart);

        mCountDownProgress.setProgressText("다음 회 보기");

        mCountDownProgress.setOnCountDownFinishEvent(new CountDownProgress.CountDownFinishListener() {
            @Override
            public void onFinished() {
                Toast.makeText(MainActivity.this, "카운트 다운 종료!!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPause(long runningTime, long remainTime, int currentPos, double currentPercent) {
                Toast.makeText(MainActivity.this, "카운트 다운 일시정지!!", Toast.LENGTH_LONG).show();
                mRemainTime = remainTime;
                cntProgress = currentPos;

                Log.e("test", "[countdown pause] runningTime = " + runningTime + " / remainTime = " + remainTime
                        + " / Progress Pos = " + currentPos + " / Progress loading Percent = " + currentPercent);
            }
        });

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownProgress.onStart(10000);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownProgress.onCancel();
            }
        });

        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownProgress.onPause();
            }
        });

        mRestart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCountDownProgress.onRestart(mRemainTime);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCountDownProgress.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCountDownProgress.onPause();
    }
}