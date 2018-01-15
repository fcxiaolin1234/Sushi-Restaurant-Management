package vn.com.fcxiaolin.sushi_app;

import android.drm.DrmStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import android.support.v7.widget.Toolbar;

public class navigation_screen extends AppCompatActivity {
    private DrawerLayout drLayout;
    private ActionBarDrawerToggle drToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_screen);
        drLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drToggle=new ActionBarDrawerToggle(this,drLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drLayout.setDrawerListener(drToggle);
       drToggle.syncState();




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(drToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


}


