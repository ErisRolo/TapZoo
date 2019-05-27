package com.example.ghx.tapzoo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.base.BaseFragment;

/**
 * Created by ghx on 2019/3/30.
 * 探索
 */

public class ExploreFragment extends BaseFragment {

    private TextureMapView mMapView;
    private AMap mAMap;
    private MyLocationStyle mMyLocationStyle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMapView = getView().findViewById(R.id.mMapView);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(18.0F));
        mAMap.moveCamera(CameraUpdateFactory.changeTilt(180.0F));
        mAMap.getUiSettings().setZoomControlsEnabled(false);

        mMyLocationStyle = new MyLocationStyle();
        mMyLocationStyle.interval(3000);//设置连续定位模式下的定位间隔
        mMyLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        mAMap.setMyLocationStyle(mMyLocationStyle);
        mAMap.setMyLocationEnabled(true);
    }
}
