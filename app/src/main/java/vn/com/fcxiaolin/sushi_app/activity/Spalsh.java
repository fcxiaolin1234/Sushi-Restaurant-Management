package vn.com.fcxiaolin.sushi_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import vn.com.fcxiaolin.sushi_app.R;

public class Spalsh extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        tv = (TextView) findViewById(R.id.splashtv);
        iv = (ImageView) findViewById(R.id.splashiv);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.trans);
        iv.startAnimation(anim);
        final Intent i = new Intent(this,NavigationActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                }

            }
        };
        timer.start();

        System.out.println("Hello World");
    }
}
