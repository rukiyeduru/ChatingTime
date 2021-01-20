package com.example.chatapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatapp1.Fragments.ChatsFragment;
import com.example.chatapp1.Fragments.GroupsFragment;

public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                GroupsFragment usersFragment = new GroupsFragment();
                return usersFragment;
            case 1:
                ChatsFragment chatsFragment=new ChatsFragment();
                return chatsFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "GRUPLAR";
            case 1:
                return "MESAJLAR";
            default:
                return null;
        }
    }
}
