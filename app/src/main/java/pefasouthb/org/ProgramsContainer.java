package pefasouthb.org;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import pefasouthb.org.mappers.Programs;
import pefasouthb.org.utils.Constants;

public class ProgramsContainer extends AppCompatActivity {
    private static final String TAG = "ProgramsContainer";
    private TextView name;
    private ImageView imageView;
    private WebView webView;
    Programs program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs_container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Maroon)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Programs");

        initialise();

        // check where there is bundle and if it has anything
        if(getIntent().hasExtra("selected_program")){
            program = getIntent().getParcelableExtra("selected_program");
            Log.d(TAG, "onCreate: "+program.toString());
        }

        Glide.with(this).load(Constants.programs_image_path+program.getPhoto()).into(imageView);
        name.setText(program.getName());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(program.getDescription(), "text/html", null);

    }

    private void initialise() {
        name = findViewById(R.id.txtProgramsContainerName);
        imageView = findViewById(R.id.imgProgramsContainerImage);
        webView = findViewById(R.id.wViewProgramsContainer);
    }

}
