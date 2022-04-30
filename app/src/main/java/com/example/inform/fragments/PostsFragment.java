package com.example.inform.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inform.Post;
import com.example.inform.PostAdapter;
import com.example.inform.R;
import com.example.inform.postInterface;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PostsFragment extends Fragment implements postInterface {

    public static final String TAG = "Post fragments";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView rvPosts;
    protected PostAdapter adapter;
    protected List<Post> allposts;
    // TODO: Rename and change types of parameters


    public PostsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onViewCreated(view,savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        allposts = new ArrayList<>();
        adapter = new PostAdapter(getContext(),allposts, this);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting post",e);
                    return;
                }
                for (Post post: posts){
                    Log.i(TAG,"Post: "+post.getDescription()+" ,username: "+ post.getUser().getUsername());
                }
                allposts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onItemLongClick(int position){
        allposts.remove(position);
        adapter.notifyItemRemoved(position);

    }



}