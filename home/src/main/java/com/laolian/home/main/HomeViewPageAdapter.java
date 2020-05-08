package com.laolian.home.main;

import com.laolian.home.newblog.NewBlogFragment;
import com.laolian.home.newproject.NewProjectFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * @author laolian
 * email : 3077075551@qq.com
 * @date 2020/3/19 12:07
 */
public class HomeViewPageAdapter extends FragmentStateAdapter  {

    List<Fragment> fragments = new ArrayList<>() ;

    public HomeViewPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
        fragments.add(new NewBlogFragment());
        fragments.add(new NewProjectFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
