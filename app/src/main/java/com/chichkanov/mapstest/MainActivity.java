package com.chichkanov.mapstest;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity
        implements Drawer.OnDrawerItemClickListener, FragmentManager.OnBackStackChangedListener {

    private static final int NEWS_ID = 0;
    private static final int PLACES_ID = 1;
    private static final int SWITCH_ID = 2;
    private static final String DRAWER_SELECTED = "selected_drawer_item";

    private String[] mMenuTitles;
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.accent));
        setSupportActionBar(toolbar);

        mMenuTitles = getResources().getStringArray(R.array.drawer_menu_items);

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(NEWS_ID)
                .withIconColorRes(R.color.dark)
                .withTextColorRes(R.color.dark)
                .withIcon(R.drawable.ic_news)
                .withSelectedTextColor(Color.BLACK)
                .withSelectedIconColor(Color.BLACK)
                .withIconTintingEnabled(true)
                .withName(R.string.news);

        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(PLACES_ID)
                .withIconColorRes(R.color.dark)
                .withTextColorRes(R.color.dark)
                .withIcon(R.drawable.ic_location)
                .withSelectedTextColor(Color.BLACK)
                .withSelectedIconColor(Color.BLACK)
                .withIconTintingEnabled(true)
                .withName(R.string.places);

        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(SWITCH_ID)
                .withIconColorRes(R.color.dark)
                .withTextColorRes(R.color.dark)
                .withIcon(R.drawable.ic_arrows)
                .withSelectedTextColor(Color.BLACK)
                .withSelectedIconColor(Color.BLACK)
                .withIconTintingEnabled(true)
                .withName(R.string.switchSchool);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withRootView(R.id.drawer_layout)
                .withToolbar(toolbar)
                .withDrawerWidthDp(300)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(item1, item2, item3)
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            onBackPressed();
                            return true;
                        }
                        return false;
                    }
                })
                .withOnDrawerItemClickListener(this)
                .build();

        if (savedInstanceState == null) {
            replaceFragment(new NewsFragment(), NewsFragment.class.getSimpleName(), true);
        } else {
            drawer.setSelection(savedInstanceState.getLong(DRAWER_SELECTED), false);
            onBackStackChanged();
        }
        setTitle(mMenuTitles[0]);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(DRAWER_SELECTED, drawer.getCurrentSelection());
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch ((int) drawerItem.getIdentifier()) {
            case NEWS_ID:
                replaceFragment(new NewsFragment(), NewsFragment.class.getSimpleName(), true);
                setTitle(mMenuTitles[position]);
                drawer.closeDrawer();
                return true;

            case PLACES_ID:
                replaceFragment(new MapsFragment(), MapsFragment.class.getSimpleName(), true);
                setTitle(mMenuTitles[position]);
                drawer.closeDrawer();
                return true;

            case SWITCH_ID:
                finish();
                startActivity(new Intent(this, PickSchoolNameActivity.class));
                return true;
        }
        return false;
    }

    public void replaceFragment(Fragment fragmentToSet, String tag, boolean top) {
        FragmentManager manager = getSupportFragmentManager();
        if (top) {
            boolean removed = manager.popBackStackImmediate();
            while (removed) {
                removed = manager.popBackStackImmediate();
            }
        }

        if (manager.findFragmentByTag(tag) == null) {
            FragmentTransaction transaction = manager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .replace(R.id.container, fragmentToSet, tag);

            if (!top) {
                transaction.addToBackStack(null);
            }

            transaction.commit();
        }
    }

    @Override
    public void onBackStackChanged() {
        boolean hasBackStackFragments = getSupportFragmentManager().getBackStackEntryCount() > 0;

        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(!hasBackStackFragments);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(hasBackStackFragments);
        }
    }
}