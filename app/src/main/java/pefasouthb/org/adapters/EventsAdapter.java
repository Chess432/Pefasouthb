package pefasouthb.org.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pefasouthb.org.mappers.Event;
import pefasouthb.org.R;
import pefasouthb.org.utils.Constants;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {


    private Context mCtx;
    private List<Event> eventsList;
    private OnEventsListener mOnEventsListener;

    public EventsAdapter(Context mCtx, List<Event> eventsList, OnEventsListener onEventsListener) {
        this.mCtx = mCtx;
        this.eventsList = eventsList;
        this.mOnEventsListener = onEventsListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.events_list, null);
        return new EventViewHolder(view, mOnEventsListener);
    }


    @Override
    public void onBindViewHolder(EventsAdapter.EventViewHolder holder, int position) {
        Event event = eventsList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(Constants.events_image_path+event.getImage())
                .into(holder.imageView);

        //holder.imageView.setImageResource(event.getImage());

        holder.textViewSubject.setText(event.getSubject());
        holder.textViewVenue.setText(event.getVenue());
        holder.textViewDate.setText(event.getDate());


    }

@Override
public int getItemCount() {
    return eventsList.size();
}

class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView textViewSubject, textViewVenue, textViewDate;
    ImageView imageView;
    RelativeLayout parentLayout;
    OnEventsListener onEventsListener;

    public EventViewHolder(View itemView, OnEventsListener onEventsListener) {
        super(itemView);

        textViewSubject = itemView.findViewById(R.id.txtSubject);
        textViewVenue = itemView.findViewById(R.id.txtVenue);
        textViewDate = itemView.findViewById(R.id.txtDate);
        imageView = itemView.findViewById(R.id.imageView);
        parentLayout = itemView.findViewById(R.id.parent_layout);

        mOnEventsListener = onEventsListener;

        itemView.setOnClickListener(this);
         }

    @Override
    public void onClick(View view) {
        mOnEventsListener.onEventsClick(getAdapterPosition());
    }
}

    public interface OnEventsListener{
        void onEventsClick(int position);
    }
}
