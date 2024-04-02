package com.example.myprojectdemo.ui;

import com.example.myprojectdemo.R;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.myclass> implements Filterable {

    Context context;
    String fname;
    ArrayList<ArtistModel> recepieModels;
    ArrayList<ArtistModel> recepieModelfull;
    MainDatabase db;

    public FavoriteAdapter(Context context, ArrayList recepieModels) {
        this.context = context;
        this.recepieModels = recepieModels;
        recepieModelfull = new ArrayList<>(recepieModels);
    }


    @Override
    public myclass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_member_row,parent,false);
        return new myclass(view);


    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull myclass holder, int position) {
        ArtistModel model=recepieModels.get(position);

        holder.textViewname.setText(model.getTitle());
        // holder.textViewname1.setText(model.getIngredint());
       //  holder.textViewname2.setText(model.getDescription());
            try{

                Picasso.get().load(recepieModels.get(position).getAlbumCover()).into(holder.imageView);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db=new MainDatabase(context);
                db.delete(String.valueOf(model.getId()));
                Intent i=new Intent(context, ViewFavouriteActivity.class);
                context.startActivity(i);
            }
        });

        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FavoriteDetailPage.class);
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
        return recepieModels.size();
    }


    public class myclass extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewname;
        TextView textViewname1;
        TextView textViewname2;
        CardView cd;
        ImageButton deleteBtn;
        public myclass( View itemView) {
            super(itemView);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);
            textViewname = itemView.findViewById(R.id.title);
            textViewname1 = itemView.findViewById(R.id.subtitle);
            textViewname2 = itemView.findViewById(R.id.desc);
            imageView=itemView.findViewById(R.id.idIVCourseImage);
            cd=itemView.findViewById(R.id.cd);

        }


    }

    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        System.out.println(file.getAbsolutePath());
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
     /*   MediaScannerConnection.scanFile(context, new String[] { file.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
*/
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ArtistModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(recepieModelfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ArtistModel item : recepieModelfull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recepieModels.clear();
            recepieModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}