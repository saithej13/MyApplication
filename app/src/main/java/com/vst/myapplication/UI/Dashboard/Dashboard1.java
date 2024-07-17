package com.vst.myapplication.UI.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vst.myapplication.UI.Dashboard.DashboardFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Login.Login;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.databinding.Dashboard1Binding;

import java.util.ArrayList;
import java.util.List;

public class Dashboard1 extends BaseFragment {
    Dashboard1Binding binding;
    private ProjectRepository repository;
    private ViewPager viewPager;
//    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dashboard1, parent, false);
        repository = new ProjectRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        viewPager = binding.viewPager;
        tabLayout = binding.tabLayout;
        return binding.getRoot();
    }



    private void setupUI() {
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("isTitle", true);
        DashboardFragment loginFragment = new DashboardFragment();
        loginFragment.setArguments(mBundle);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.frame, loginFragment, "")
                .addToBackStack("")
                .commitAllowingStateLoss();
//        BottomNavigationView bottomNavigationView = binding.navView;
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new DashboardFragment());
//        fragments.add(new graphFragment());
////
//        List<String> titles = new ArrayList<>();
//        titles.add("Dashboard");
//        titles.add("Graph");
//
//        viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(), fragments, titles);
//        viewPager.setAdapter(viewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onResume() {
        super.onResume();
        setupUI();
    }


//    public class ViewPagerAdapter extends FragmentStatePagerAdapter  {
//        private List<Fragment> fragmentList;
//        private List<String> fragmentTitleList;
//
//        public ViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList, List<String> fragmentTitleList) {
//            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//            this.fragmentList = fragmentList;
//            this.fragmentTitleList = fragmentTitleList;
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return fragmentTitleList.get(position);
//        }
//    }
}