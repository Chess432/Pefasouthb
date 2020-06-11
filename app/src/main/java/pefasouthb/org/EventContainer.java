package pefasouthb.org;

import android.content.Context;
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
import android.widget.Toast;

import pefasouthb.org.mappers.Event;

public class EventContainer extends AppCompatActivity {

    private static final String TAG = "EventContainer";
    private TextView subject;
    private TextView date;
    private TextView venue;
    private ImageView imageView;
    private WebView webView;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Maroon)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News");

        initialise();


        if(getIntent().hasExtra("selected_event")){
             event = getIntent().getParcelableExtra("selected_event");
            Log.d(TAG, "onCreate: "+event.toString());
        }
        else {
            Log.d(TAG, "onCreate: "+ "Nothing to show");
        }

        //loading the image
        Glide.with(this).load(event.getImage()).into(imageView);
        subject.setText(event.getSubject());
        venue.setText(event.getVenue());
        date.setText(event.getDate());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(event.getDescription(), "text/html", null);


    }

    private void initialise() {
        imageView = findViewById(R.id.imgEventContainerImage);
        subject  = findViewById(R.id.txtEventContainerSubject);
        date = findViewById(R.id.txtEventContainerDate);
        venue = findViewById(R.id.txtEventContainerVenue);
        webView = findViewById(R.id.wViewEventContainer);
    }

}
