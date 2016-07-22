package vikram.com.swampfestation;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vikram on 7/2/16.
 */


public class GeneralActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private NavigationView navigationView;
    private ListView lstView;
    private TextView description;
    ArrayList<Screen.IGButton> buts;
    FragmentInterface fr;
    private HashMap<String, String> emojiMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards);
        description = (TextView) findViewById(R.id.description);
        description.setMovementMethod(new ScrollingMovementMethod());


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        mActivityTitle = Constants.sc.title;
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        lstView = (ListView) findViewById(R.id.buttons);
        setupListView();
        setupFragment();
    }

    public void setupFragment() {

        if (Constants.sc.type.equals("c")) {
            fr = new MsgFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.msg_view, fr);
            transaction.commit();
        } else {
            fr = new DropFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.msg_view, fr);
            transaction.commit();
        }
        fr.setup(this);
    }

    public void setupListView() {
        getSupportActionBar().setTitle(Constants.sc.title);
        description.setText(Constants.sc.getDescription());
        lstView.invalidate();
        buts = new ArrayList<Screen.IGButton>();
        ArrayList<String> links = new ArrayList<String>();
        for (int i = 0; i < Constants.sc.buttons.size(); i++) {
            Screen.IGButton but = Constants.sc.parsedButtons.get(i);
            if (!Constants.condition(this, but.conditions)) {
                links.add(but.title);
                buts.add(but);
            }
        }
        ArrayAdapter linksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, links);
        lstView.setAdapter(linksAdapter);
        lstView.setOnItemClickListener(new ButClickListener(this));
        lstView.postInvalidate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String sel = item.getTitle().toString();
        sel = emojiMap.get(sel);
        if (sel.equals("var list")) {
            Intent intent = new Intent(this, VarActivity.class);
            startActivity(intent);
        }
        for (Characters.Player pl : Constants.ch.players) {
            if (sel.equals(pl.name)) {
                Constants.pl = pl;
                Intent intent = new Intent(this, PlayerActivity.class);
                startActivity(intent);
            }
        }
        return false;
    }

    private void setupDrawer() {
        navigationView.getMenu().clear();
        Menu menu = navigationView.getMenu();
        emojiMap = new HashMap<String, String>();
        for (Characters.Player pl : Constants.ch.players) {
            String key = "\ud83d\ude01    " + pl.name;
            menu.add(key);
            emojiMap.put(key, pl.name);
        }
        menu.add("var list");
        //navigationView(menu);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                Menu menuNav = navigationView.getMenu();
                for (int i = 0; i < menuNav.size(); i++) {
                    String orig = menuNav.getItem(i).getTitle().toString();
                    if (emojiMap.containsKey(orig)) {
                        String name = emojiMap.get(orig);
                        String ne = name;
                        for (Characters.Player pl : Constants.ch.players) {
                            if (name.equals(pl.name)) {
                                if (pl.hap <= 30) {
                                    ne = "\uD83D\uDE1E    " + ne;
                                } else {
                                    ne = "\uD83D\uDE01    " + ne;
                                }
                            }
                        }
                        menuNav.getItem(i).setTitle(ne);
                        emojiMap.put(ne, name);
                    }
                }

                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("See Status of Other Players!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
