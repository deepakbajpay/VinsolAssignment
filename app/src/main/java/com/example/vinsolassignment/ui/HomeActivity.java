package com.example.vinsolassignment.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.vinsolassignment.R;
import com.example.vinsolassignment.databinding.ActivityHomeBinding;
import com.example.vinsolassignment.ui.base.BaseActivity;
import com.example.vinsolassignment.ui.schedule.MySchedulesFragment;
import com.example.vinsolassignment.ui.schedule.HomeViewModel;
import com.example.vinsolassignment.ui.utils.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProviderFactory factory;
    private HomeViewModel homeViewModel;
    private ActivityHomeBinding mActivityMainBinding;

    @Override
    public int getBindingVariable() {
        return com.example.vinsolassignment.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        homeViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        return homeViewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        homeViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        getSupportFragmentManager().beginTransaction().add(R.id.home_container, MySchedulesFragment.newInstance(), null).commitAllowingStateLoss();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
