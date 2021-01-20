package com.example.chatapp1.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chatapp1.Adapater.UserAdapter;
import com.example.chatapp1.GroupChatActivity;
import com.example.chatapp1.Model.User;
import com.example.chatapp1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GroupsFragment extends Fragment {

    private View groupFragmentview;
    private ListView list_View;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String>  list_of_groups = new ArrayList<>();

    private DatabaseReference GroupRef;
    private FirebaseAuth auth;

    public GroupsFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        groupFragmentview=  inflater.inflate(R.layout.fragment_groups, container, false);

        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            String userid=auth.getCurrentUser().getUid();
            GroupRef=FirebaseDatabase.getInstance().getReference().child("Gruplar");

            InıtalizeFields();

            RetriveAndDisplayGroups();

            list_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    String currentGroupName = adapterView.getItemAtPosition(position).toString();
                    Intent groupChatIntnet =  new Intent(getContext(), GroupChatActivity.class);
                    groupChatIntnet.putExtra("groupname",currentGroupName);
                    startActivity(groupChatIntnet);
                }
            });
        }


        return groupFragmentview;
    }


    private void InıtalizeFields() {
        list_View=(ListView) groupFragmentview.findViewById(R.id.list_view);
        arrayAdapter=new ArrayAdapter<String >(getContext(),android.R.layout.simple_list_item_1,list_of_groups);
        list_View.setAdapter(arrayAdapter);
    }
    private void RetriveAndDisplayGroups() {
        GroupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Set<String> set=new HashSet<>();
                Iterator iterator=datasnapshot.getChildren().iterator();

                while ((iterator.hasNext())){
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }
                list_of_groups.clear();
                arrayAdapter.notifyDataSetChanged();
                list_of_groups.addAll(set);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
