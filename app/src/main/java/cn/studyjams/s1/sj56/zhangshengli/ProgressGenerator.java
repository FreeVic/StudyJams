package cn.studyjams.s1.sj56.zhangshengli;

import android.os.Handler;

import com.dd.processbutton.ProcessButton;

import java.util.Random;

public class ProgressGenerator {
    private int mProgress;
    ProcessButton button;
    public ProgressGenerator(ProcessButton button) {
        this.button = button;
    }

    public void start() {
        mProgress = 0;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                button.setProgress(mProgress);
                if (mProgress < 100) {
                    handler.postDelayed(this, generateDelay());
                }
            }
        }, generateDelay());
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(1000);
    }


}