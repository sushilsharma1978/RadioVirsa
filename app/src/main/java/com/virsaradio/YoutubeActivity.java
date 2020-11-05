package com.virsaradio;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.virsaradio.Fragment.FragmentAllvideos;
import com.virsaradio.Fragment.FragmentPlaylistVideos;

import java.util.ArrayList;
import java.util.List;

public class YoutubeActivity extends AppCompatActivity implements View.OnClickListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView iv_back_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        Init();
        ClickListener();

    }

    private void ClickListener() {
        iv_back_arrow.setOnClickListener(this);
    }

    private void Init()
    {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        addTabs(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        iv_back_arrow=(ImageView)findViewById(R.id.iv_back_arrow);
    }

    private void addTabs(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentAllvideos(), "All Videos");
        adapter.addFrag(new FragmentPlaylistVideos(), "Youtube Playlists");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view==iv_back_arrow)
        {
            Intent intent=new Intent(YoutubeActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(YoutubeActivity.this,MainActivity.class);
        startActivity(intent);

    }
}
