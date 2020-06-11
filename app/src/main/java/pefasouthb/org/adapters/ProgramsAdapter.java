package pefasouthb.org.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pefasouthb.org.mappers.Programs;
import pefasouthb.org.R;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.ProgramViewHolder> {

    private Context mCtx;
    private List<Programs> programsList;
    private OnProgramsListener mOnProgramsListener;

    public ProgramsAdapter(Context mCtx, List<Programs> programsList, OnProgramsListener onProgramsListener) {
        this.mCtx = mCtx;
        this.programsList = programsList;
        this.mOnProgramsListener = onProgramsListener;
    }

    @Override
    public ProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.programs_list, null);
        return new ProgramViewHolder(view, mOnProgramsListener);
    }


    @Override
    public void onBindViewHolder(ProgramsAdapter.ProgramViewHolder holder, int position) {
        Programs program = programsList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(program.getPhoto())
                .into(holder.imageView);

        holder.txtName.setText(program.getName());
    }

@Override
public int getItemCount() {
    return programsList.size();
}

class ProgramViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView txtName;
    ImageView imageView;

public ProgramViewHolder(View itemView, OnProgramsListener onProgramsListener) {
    super(itemView);

    txtName = itemView.findViewById(R.id.txtName);
    imageView = itemView.findViewById(R.id.imageView);

    mOnProgramsListener = onProgramsListener;
    itemView.setOnClickListener(this);
}

    @Override
    public void onClick(View view) {
        mOnProgramsListener.onProgramsClick(getAdapterPosition());
    }
}

    public  interface OnProgramsListener{
            void onProgramsClick(int position);
    }
}
