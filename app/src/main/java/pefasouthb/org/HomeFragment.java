package pefasouthb.org;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pefasouthb.org.adapters.SermonAdapter;
import pefasouthb.org.mappers.Sermons;
import pefasouthb.org.utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SermonAdapter.OnSermonsListener {
    //a list to store all the sermons
    List<Sermons> sermonsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    

    //the recyclerview
    private RecyclerView recyclerView;
    SermonAdapter adapter;
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();


        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtubePlay, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YouTubeConfig.getApi_key(), new YouTubePlayer.OnInitializedListener(){

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.cueVideo(Constants.VIDEO_ID);
                    player.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                //Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

        // initialise swipe refresh layout
        swipeRefreshLayout = view.findViewById(R.id.refreshSermons);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(sermonsList != null){
                    sermonsList.clear();
                }
                loadProducts();
                adapter.notifyDataSetChanged();


                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }


        });

        //getting the recyclerview from xml
        recyclerView =  view.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the productlist
        sermonsList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();
        return view;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void loadProducts() {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.SERMONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                sermonsList.add(new Sermons(
                                        product.getInt("id"),
                                        product.getString("subject"),
                                        product.getString("text"),
                                        product.getString("Speaker"),
                                        product.getString("date"),
                                        product.getString("scripture"),
                                        product.getString("image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                             adapter = new SermonAdapter(getContext(), sermonsList, new SermonAdapter.OnSermonsListener() {
                                @Override
                                public void onSermonsClick(int position) {
                                    Intent intent = new Intent(getActivity(), SermonContainer.class);
                                    intent.putExtra("selected_sermon", sermonsList.get(position));
                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


    @Override
    public void onSermonsClick(int position) {

    }




}
