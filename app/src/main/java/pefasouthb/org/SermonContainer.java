package pefasouthb.org;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pefasouthb.org.mappers.Sermons;
import pefasouthb.org.utils.Constants;

public class SermonContainer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    private static final String TAG = "SermonContainer";
    private TextView subject;
    private TextView date;
    private TextView text;
    private TextView speaker;
    private ImageView imageView;
    private WebView webView;
    private YouTubePlayerView youTubePlayerView;
    Sermons sermon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sermon_container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Maroon)));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Sermons");

        initialise();
        youTubePlayerView.initialize(YouTubeConfig.getApi_key(), this);
        if(getIntent().hasExtra("selected_sermon")){
            sermon = getIntent().getParcelableExtra("selected_sermon");
            Log.d(TAG, "onCreate: "+ sermon.toString());

        }

        Log.d(TAG, "onCreate value of video string: "+ sermon.getVideo_url());


    }

    private void initialise() {
        subject = findViewById(R.id.txtSermonContainerSubject);
        date = findViewById(R.id.txtSermonContainerDate);
        text = findViewById(R.id.txtSermonContainerText);
        speaker = findViewById(R.id.txtSermonContainerSpeaker);
        imageView = findViewById(R.id.imgSermonContainerImage);
        webView = findViewById(R.id.wViewSermonContainer);
        youTubePlayerView = findViewById(R.id.youTubePlayerView);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        /** Start buffering **/
        if(sermon.getVideo_url().equalsIgnoreCase("null")){
            youTubePlayerView.setVisibility(View.GONE);
            Glide.with(this).load(Constants.sermon_image_path + sermon.getImage()).into(imageView);
            loadSermonComponents();
        }else {
            imageView.setVisibility(View.GONE);
            if (!b) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.cueVideo(sermon.getVideo_url());
                youTubePlayer.play();
            }
            loadSermonComponents();
        }


    }

    private void loadSermonComponents() {
        subject.setText(sermon.getSubject());
        date.setText(sermon.getDate());
        speaker.setText(sermon.getSpeaker());
        text.setText(sermon.getText());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(sermon.getScripture(), "text/html", null);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        String errorMessage = youTubeInitializationResult.toString();
        Log.d("errorMessage:", errorMessage);

    }
}
