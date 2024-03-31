package com.example.myprojectdemo.ui;

import com.example.myprojectdemo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.myclass> implements Filterable {

    Context context;
    String fname;
    ArrayList<RecipeModel> recepieModels;
    ArrayList<RecipeModel> recepieModelfull;


    public RecipeAdapter(Context context, ArrayList recepieModels) {
        this.context = context;
        this.recepieModels = recepieModels;
        recepieModelfull = new ArrayList<>(recepieModels);
    }


    @Override
    public myclass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_row,parent,false);
        return new myclass(view);


    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull myclass holder, int position) {
          RecipeModel model=recepieModels.get(position);

        holder.textViewname.setText(model.getName());
        // holder.textViewname1.setText(model.getIngredint());
       //  holder.textViewname2.setText(model.getDescription());
            try{
              byte[]  image= model.getImage();
              Bitmap  bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
                holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));

            }
            catch (Exception e){
                e.printStackTrace();
            }

        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                Intent intent = new Intent(context, RecipePage.class);
                intent.putExtra("title",model.getName());
                intent.putExtra("ingredient",model.getIngredint());
                intent.putExtra("description",model.getDescription());

                intent.putExtra("FETCHID",model.getId());
               byte[] img= model.getImage();
               Bitmap b= BitmapFactory.decodeByteArray(img,0,img.length);


                saveImageToExternalStorage(b);

                intent.putExtra("fname",fname);
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

        public myclass( View itemView) {
            super(itemView);

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
            List<RecipeModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(recepieModelfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (RecipeModel item : recepieModelfull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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