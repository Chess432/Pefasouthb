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

import pefasouthb.org.mappers.Sermons;

public class SermonContainer extends AppCompatActivity {
    private static final String TAG = "SermonContainer";
    private TextView subject;
    private TextView date;
    private TextView text;
    private TextView speaker;
    private ImageView imageView;
    private WebView webView;
    Sermons sermon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sermon_container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Maroon)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sermons");

        initialise();
        if(getIntent().hasExtra("selected_sermon")){
            sermon = getIntent().getParcelableExtra("selected_sermon");
            Log.d(TAG, "onCreate: "+ sermon.toString());

        }

        Glide.with(this).load(sermon.getImage()).into(imageView);
        subject.setText(sermon.getSubject());
        date.setText(sermon.getDate());
        speaker.setText(sermon.getSpeaker());
        text.setText(sermon.getText());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(sermon.getScripture(), "text/html", null);
    }

    private void initialise() {
        subject = findViewById(R.id.txtSermonContainerSubject);
        date = findViewById(R.id.txtSermonContainerDate);
        text = findViewById(R.id.txtSermonContainerText);
        speaker = findViewById(R.id.txtSermonContainerSpeaker);
        imageView = findViewById(R.id.imgSermonContainerImage);
        webView = findViewById(R.id.wViewSermonContainer);
    }

}
