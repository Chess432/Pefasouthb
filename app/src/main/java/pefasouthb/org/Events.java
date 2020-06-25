package pefasouthb.org;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pefasouthb.org.adapters.EventsAdapter;
import pefasouthb.org.mappers.Event;
import pefasouthb.org.utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class Events extends Fragment implements EventsAdapter.OnEventsListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private EventsAdapter adapter;
    private List<Event> eventsList;
    private static final String TAG = "Events";


    //the recyclerview
    private RecyclerView recyclerView;
    public static Events newInstance() {
        return new Events();
    }



//    public Profile() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        // initialize swipe refresh layout
        swipeRefreshLayout = view.findViewById(R.id.refreshNews);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(eventsList != null){
                    eventsList.clear();
                }
                loadEvents();
                adapter.notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        //getting the recyclerview from xml
        recyclerView =  view.findViewById(R.id.eventsRecylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the Events list
        eventsList = new ArrayList<>();


        //this method will fetch and parse json
        //to display it in recyclerview
        loadEvents();
        return  view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void loadEvents() {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.NEWS,
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
                                eventsList.add(new Event(
                                        product.getInt("id"),
                                        product.getString("subject"),
                                        product.getString("venue"),
                                        product.getString("description"),
                                        product.getString("date"),
                                        product.getString("photo")
                                ));

                            }

                            //creating adapter object and setting it to recyclerview
                            adapter = new EventsAdapter(getActivity(), eventsList, new EventsAdapter.OnEventsListener() {
                                @Override
                                public void onEventsClick(int position) {
                                    //Toast.makeText(getActivity(), position+"", Toast.LENGTH_LONG).show();
                                    Intent intent =new Intent(getActivity(), EventContainer.class);
                                    intent.putExtra("selected_event", eventsList.get(position));
                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: "+ e.toString());
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
    public void onEventsClick(int position) {
        //eventsList.get(position);
        //Toast.makeText(getActivity(), position+"", Toast.LENGTH_LONG).show();

    }
}
