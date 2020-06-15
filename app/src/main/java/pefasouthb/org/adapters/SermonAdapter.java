package pefasouthb.org.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import pefasouthb.org.R;
import pefasouthb.org.mappers.Sermons;
import pefasouthb.org.utils.Constants;

public class SermonAdapter extends RecyclerView.Adapter<SermonAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Sermons> sermonsList;
    private OnSermonsListener mOnSermonsListener;
    private static final String TAG = "SermonAdapter";

    public SermonAdapter(Context mCtx, List<Sermons> sermonsList, OnSermonsListener onSermonsListener) {
        this.mCtx = mCtx;
        this.sermonsList = sermonsList;
        this.mOnSermonsListener = onSermonsListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.sermon_list, null);
        return new ProductViewHolder(view, mOnSermonsListener);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Sermons product = sermonsList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(Constants.sermon_image_path+product.getImage())
                .into(holder.imageView);

        Log.d(TAG, "onBindViewHolder: "+Constants.sermon_image_path+product.getImage());
        holder.textViewTitle.setText(product.getSubject());
        holder.textViewShortDesc.setText(product.getText());
        holder.textViewRating.setText(String.valueOf(product.getSpeaker()));
        holder.textViewPrice.setText(String.valueOf(product.getDate()));
    }

    @Override
    public int getItemCount() {
        return sermonsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;
        OnSermonsListener onSermonsListener;

        public ProductViewHolder(View itemView, OnSermonsListener onSermonsListener) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);

            mOnSermonsListener = onSermonsListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnSermonsListener.onSermonsClick(getAdapterPosition());
        }
    }

    public interface OnSermonsListener{
        void onSermonsClick(int position);
    }

}
