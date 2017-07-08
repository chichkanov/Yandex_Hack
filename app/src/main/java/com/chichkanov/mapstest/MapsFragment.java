package com.chichkanov.mapstest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chichkanov.mapstest.model.Place;
import com.chichkanov.mapstest.model.Storage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private Map<String, Boolean> mCurrentCategories = new LinkedHashMap<>();

    private RecyclerView mRecyclerPlaces;
    private PlacesAdapter mAdapter;
    private ImageButton mBtnFilter;
    private EditText mEditSearch;
    private Button mBtnShowList;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_map, container, false);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initPlacesList(layout);
        initFilterButton(layout);
        initBottomSheet(layout);
        initSearch(layout);

        return layout;
    }

    private void initFilterButton(View layout) {
        mBtnFilter = (ImageButton) layout.findViewById(R.id.btn_filter);
        final String[] categories = Storage.getInstance(getActivity().getApplicationContext()).getCategories();
        for(String c : categories) {
            mCurrentCategories.put(c, true);
        }
        mBtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle(R.string.choose_categories);
                boolean[] choosedCategories = new boolean[mCurrentCategories.values().size()];
                for(int i = 0; i < mCurrentCategories.values().size(); i++) {
                    choosedCategories[i] = (boolean) mCurrentCategories.values().toArray()[i];
                }

                builder.setMultiChoiceItems(categories, choosedCategories, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        List<String> keys = new ArrayList<>(mCurrentCategories.keySet());
                        String filter = keys.get(which);
                        mCurrentCategories.put(filter, isChecked);
                        updateMarkers();
                    }
                });

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });
    }

    private void initPlacesList(View layout) {
        mRecyclerPlaces = (RecyclerView) layout.findViewById(R.id.list_places);
        List<Place> data = Storage.getInstance(getActivity().getApplicationContext()).getPlaces();
        mAdapter = new PlacesAdapter(data, new PlacesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Place place) {
                moveCameraToPlace(place);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        mRecyclerPlaces.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 1));
        mRecyclerPlaces.setAdapter(mAdapter);
        mRecyclerPlaces.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    private void initBottomSheet(View layout) {
        final View bottomSheet = layout.findViewById(R.id.view_bottom_sheet);
        mBtnShowList = (Button) layout.findViewById(R.id.btn_show_list);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                mBtnShowList.setVisibility(newState == BottomSheetBehavior.STATE_HIDDEN ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        mBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void initSearch(View layout) {
        mEditSearch = (EditText) layout.findViewById(R.id.txt_search);
        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateMarkers();
            }
        });
    }

    private void moveCameraToPlace(Place place) {
        LatLng latLng = new LatLng(place.getLat(), place.getLon());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
    }

    private void updateMarkers() {
        List<String> searchCategories = new ArrayList<>();
        for(Map.Entry<String, Boolean> entry : mCurrentCategories.entrySet()) {
            if(entry.getValue()) searchCategories.add(entry.getKey());
        }
        List<Place> data = Storage.getInstance(getActivity().getApplicationContext()).getPlacesWithCategories(searchCategories, mEditSearch.getText().toString());
        mAdapter.updateData(data);

        mMap.clear();
        for(Place place : data) {
            LatLng latLng = new LatLng(place.getLat(), place.getLon());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(place.getName());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        updateMarkers();
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}

