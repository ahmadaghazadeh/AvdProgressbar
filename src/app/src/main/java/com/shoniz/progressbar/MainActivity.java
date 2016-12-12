package com.shoniz.progressbar;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    AdvProgressbar advProgressbar;
    int percent=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          advProgressbar= (AdvProgressbar) findViewById(R.id.view);
        ScheduledThreadPoolExecutor executor_ =
                new ScheduledThreadPoolExecutor(1);

        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percent++;
                advProgressbar.setPercent(percent);

                advProgressbar.setText(toPersianNumber(""+percent+" : "+percent));
            }
        });

        final Handler handler = new Handler();
        final Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            if(percent==100){
                                timer.cancel();
                            }else
                            {
                                percent++;
                                advProgressbar.setPercent(percent);

                                advProgressbar.setText(toPersianNumber(""+percent+" : "+percent));
                            }
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0,500);

    }

    private String toPersianNumber(String input) {
        String[] persian = { "۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹" };

        for (int j = 0; j < persian.length; j++)
            input = input.replace("" + j, persian[j]);

        return input;
    }
}
