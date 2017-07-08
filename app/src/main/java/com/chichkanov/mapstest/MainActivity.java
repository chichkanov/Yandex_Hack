package com.chichkanov.mapstest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity
        implements Drawer.OnDrawerItemClickListener, FragmentManager.OnBackStackChangedListener {

    private static final long NEWS_ID = 0;
    private static final long PLACES_ID = 1;
    private static final String DRAWER_SELECTED = "selected_drawer_item";

    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerBuilder drawerBuilder = new DrawerBuilder().withActivity(this)
                //.withToolbar(toolbar)
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
                .withActivity(this)
                .withOnDrawerItemClickListener(this)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withIdentifier(NEWS_ID)
                                .withIconColorRes(R.color.dark)
                                .withTextColorRes(R.color.dark)
                                .withIconTintingEnabled(true)
                                .withName("Новости"),
                        new PrimaryDrawerItem()
                                .withIdentifier(PLACES_ID)
                                .withIconColorRes(R.color.dark)
                                .withTextColorRes(R.color.dark)
                                .withIconTintingEnabled(true)
                                .withName("Места")
                );
        drawer = drawerBuilder.build();

        if(savedInstanceState == null) {
            replaceFragment(new NewsFragment(), NewsFragment.class.getSimpleName(), true);
        } else {
            drawer.setSelection(savedInstanceState.getLong(DRAWER_SELECTED), false);
            onBackStackChanged();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(DRAWER_SELECTED, drawer.getCurrentSelection());
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch (drawerItem.getIdentifier()) {
            case NEWS_ID:
                replaceFragment(new NewsFragment(), NewsFragment.class.getSimpleName(), true);
                return true;

            case PLACES_ID:
                replaceFragment(new MapsFragment(), MapsFragment.class.getSimpleName(), true);
                return true;
        }
        return false;
    }

    public void replaceFragment(Fragment fragmentToSet, String tag, boolean top) {
        FragmentManager manager = getSupportFragmentManager();
        if(top) {
            boolean removed = manager.popBackStackImmediate();
            while (removed) {
                removed = manager.popBackStackImmediate();
            }
        }

        if(manager.findFragmentByTag(tag) == null) {
            FragmentTransaction transaction = manager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .replace(R.id.container, fragmentToSet, tag);

            if(!top) {
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
        if(supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(hasBackStackFragments);
        }
    }
}