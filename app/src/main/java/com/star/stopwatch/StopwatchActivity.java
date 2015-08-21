package com.star.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    public static final String SECONDS = "seconds";
    public static final String RUNNING = "running";
    public static final String WAS_RUNNING = "wasRunning";

    private int mSeconds = 0;
    private boolean mRunning = false;
    private boolean mWasRunning = false;

    private Button mStartButton;
    private Button mStopButton;
    private Button mResetButton;

    private TextView mTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        if (savedInstanceState != null) {
            mSeconds = savedInstanceState.getInt(SECONDS);
            mRunning = savedInstanceState.getBoolean(RUNNING);
            mWasRunning = savedInstanceState.getBoolean(WAS_RUNNING);
        }

        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunning = true;
            }
        });

        mStopButton = (Button) findViewById(R.id.stop_button);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunning = false;
            }
        });

        mResetButton = (Button) findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunning = false;
                mSeconds = 0;
            }
        });

        mTimeTextView = (TextView) findViewById(R.id.time_text_view);

        runTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mWasRunning) {
            mRunning = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mWasRunning = mRunning;
        mRunning = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SECONDS, mSeconds);
        outState.putBoolean(RUNNING, mRunning);
        outState.putBoolean(WAS_RUNNING, mWasRunning);
    }

    public void runTimer() {
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = mSeconds / 3600;
                int minutes = mSeconds % 3600 / 60;
                int seconds = mSeconds % 60;

                String time = String.format("%d:%02d:%02d", hours, minutes, seconds);

                mTimeTextView.setText(time);

                if (mRunning) {
                    mSeconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

}
