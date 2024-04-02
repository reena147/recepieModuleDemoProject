package com.example.myprojectdemo.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprojectdemo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TracklistAdapter extends RecyclerView.Adapter<TracklistAdapter.myclass>  {

    Context context;

    ArrayList<ArtistModel> artistModels;
    ArrayList<ArtistModel> artistModelArrayList;


    public TracklistAdapter(Context context, ArrayList artistModels) {
        this.context = context;
        this.artistModels = artistModels;
        artistModelArrayList = new ArrayList<>(artistModels);
    }


    @Override
    public myclass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_row,parent,false);
        return new myclass(view);


    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull myclass holder, int position) {
        ArtistModel model=artistModels.get(position);

        holder.textViewname.setText(model.getTitle());
        Picasso.get().load(artistModels.get(position).getAlbumCover()).into(holder.imageView);


        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(context, ArtistDetailPage.class);
                intent.putExtra("id",model.getId());
                intent.putExtra("title",model.getTitle());
                intent.putExtra("imageUrl",model.getAlbumCover());
                intent.putExtra("albumname",model.getAlbumName());
                intent.putExtra("duration",model.getDuration());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return artistModels.size();
    }


    public class myclass extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewname;
        TextView textViewname1;
        TextView textViewname2;
        CardView cd;

        public myclass( View itemView) {
            super(itemView);

            textViewname = itemView.findViewById(R.id.title);
            textViewname1 = itemView.findViewById(R.id.subtitle);
            textViewname2 = itemView.findViewById(R.id.desc);
            imageView=itemView.findViewById(R.id.idIVCourseImage);
            cd=itemView.findViewById(R.id.cd);

        }


    }

}