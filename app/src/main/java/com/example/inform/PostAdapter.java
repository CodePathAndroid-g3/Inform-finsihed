package com.example.inform;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;
    private final postInterface PostInterface;

    public PostAdapter(Context context, List<Post> posts, postInterface postInterface){
        this.context = context;
        this.posts = posts;
        PostInterface = postInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Post post = posts.get(position);
       holder.bind(post);
    }
    public void openpage(){

        Intent i = new Intent(context,EventsActivity.class);
        /*
        i.putExtra("tvUsername",username);
        i.putExtra("tvCoontact", contact);
        //i.putExtra("ivImage", );
        i.putExtra("tvDescription", description);
         */

        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsername;
        private TextView tvContact;
        private ImageView ivImage;
        private TextView tvLocation;
        private TextView tvDescrpition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.card_image);
            tvLocation = itemView.findViewById(R.id.location);
            tvContact = itemView.findViewById(R.id.tvContact);
            tvDescrpition = itemView.findViewById(R.id.tvDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openpage();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(PostInterface !=null ){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION ) {
                            posts.get(pos).deleteInBackground();
                            PostInterface.onItemLongClick(pos);
                        }
                    }
                    return true;
                }
            });


        }

        public void bind(Post post) {
            tvLocation.setText(post.getLocation());
            tvContact.setText(post.getContact());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
        }
    }

}
