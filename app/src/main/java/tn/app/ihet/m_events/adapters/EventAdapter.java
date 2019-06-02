package tn.app.ihet.m_events.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import tn.app.ihet.m_events.R;
import tn.app.ihet.m_events.interfaces.RecyclerViewOnClickPosition;
import tn.app.ihet.m_events.model.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private List<Event> events;
    private RecyclerViewOnClickPosition mOnClickListener;
    private Context context;
    private Activity activity;

    public EventAdapter(List<Event> events,Context context,Activity activity,RecyclerViewOnClickPosition mOnClickListener)
    {
        this.events = events;
        this.context = context;
        this.activity = activity;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event e = events.get(position);
        holder.display(e);
    }

    public void clear() {
        events.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Event> result) {
        this.events = result;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        private final TextView name;
        private final ImageView picture;

        private Event currentEvent;

         MyViewHolder(final View itemView) {
            super(itemView);

            name =  itemView.findViewById(R.id.description);
            picture =  itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);


         }

         void display(Event event) {
            currentEvent = event;
            name.setText(currentEvent.getName());
             Display display = activity.getWindowManager().getDefaultDisplay();
             Point size = new Point();
             display.getSize(size);
             int width = size.x;
             int height = size.y;
             if(event.getImage() != 0)
             Picasso.with(context).load(event.getImage()).resize(width,height/3).into(picture);
             else
             {
                 Bitmap bitmap = BitmapFactory.decodeFile(event.getImageString());
                 picture.setImageBitmap(bitmap);
                 picture.getLayoutParams().height = height/3;
                 picture.getLayoutParams().width = width;
                 picture.requestLayout();


                 // Picasso.with(context).load(event.getImageString()).resize(width,height/3).into(picture);
             }
        }

        @Override
        public void onClick(View v) {

            mOnClickListener.recyclerViewListClicked(v,this.getAdapterPosition());


        }
    }


}
