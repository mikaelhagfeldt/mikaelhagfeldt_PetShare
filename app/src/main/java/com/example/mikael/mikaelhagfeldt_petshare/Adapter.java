package com.example.mikael.mikaelhagfeldt_petshare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/*
    En "adapter" som agerar brygga mellan layout cardview_after_row.xml och klassen Blog. En "adapter" kopplar samman data
    mellan layouts och klasser genom att använda sig av RecyclerView.ViewHolder. Datan i sin tur presenteras i en ny aktivitet (sida)
    via en RecyclerView.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    private Context fieldContext;
    private List<Blog> fieldBlogList;

    public Adapter(Context fieldContext, List<Blog> fieldBlogList)
    {
        this.fieldContext = fieldContext;
        this.fieldBlogList = fieldBlogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Kopplar vår view med korrekt kontext och vår layout cardview_after_row där vår design ligger.

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_after_row, parent, false);
        return new ViewHolder(view, fieldContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        /*
            Kopplar datan till "views". Metoden hämtar väsentligt blogg element (objekt) från klassens lista, och med hjälp
            av parametern "holder" skickar denna data vidare.

            För att kunna använda korrekt tid och datum använder jag mig här av en DateFormat, som man uppenbarligen
            måste konvertera till en "Long" för att få den att fungera.
         */

        Blog blog = fieldBlogList.get(position);
        String strImageURL = null;

        holder.textViewTitle.setText(blog.getFieldStrTitle());
        holder.textViewDescripton.setText(blog.getFieldStrDescription());
        holder.textViewTimeStamp.setText(blog.getFieldStrTimeStamp());

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String strFormattedDate = dateFormat.format(new Date(Long.valueOf(blog.getFieldStrTimeStamp())).getTime());

        strImageURL = blog.getFieldStrImage();
    }

    @Override
    public int getItemCount()
    {
        return fieldBlogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewTitle;
        public TextView textViewDescripton;
        public TextView textViewTimeStamp;
        public ImageView imageViewImage;

        String strUserID;

        public ViewHolder(View itemView, Context context)
        {
            super(itemView);

            fieldContext = context;

            textViewTitle = itemView.findViewById(R.id.id_cardviewafterrow_textView1);
            textViewDescripton = itemView.findViewById(R.id.id_cardviewafterrow_textView2);
            textViewTimeStamp = itemView.findViewById(R.id.id_cardviewafterrow_textView3);
            imageViewImage = itemView.findViewById(R.id.id_cardviewafterrow_imageView);

            strUserID = null;

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // Ännu inte implementerat.
                }
            });
        }
    }
}
