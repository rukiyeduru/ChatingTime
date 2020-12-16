package com.example.chatingtime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               RequestsFragment requestsFragment=new RequestsFragment();
               return requestsFragment;
           case 1:
               ChatsFragment chatsFragment=new ChatsFragment();
               return chatsFragment;
           case 2:
               FriendsFragment friendsFragment=new FriendsFragment();
               return friendsFragment;
           default:
               return null;
       }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "ISTEKLER";
            case 1:
                return "MESAJLAR";
            case 2:
                return "ARKADAŞLAR";
            default:
                return null;
        }
    }
}
