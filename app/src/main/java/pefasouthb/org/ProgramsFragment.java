package pefasouthb.org;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pefasouthb.org.adapters.ProgramsAdapter;
import pefasouthb.org.adapters.SermonAdapter;
import pefasouthb.org.mappers.Programs;
import pefasouthb.org.mappers.Sermons;
import pefasouthb.org.utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramsFragment extends Fragment implements ProgramsAdapter.OnProgramsListener{

    List<Programs> programsList;

    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    ProgramsAdapter adapter;
    public static ProgramsFragment newInstance() {
        return new ProgramsFragment();
    }


//    public ProgramsFragment() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_programs, container, false);

        // initialise swipe refresh
        swipeRefreshLayout = view.findViewById(R.id.refreshPrograms);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(programsList != null){
                    programsList.clear();
                }
                loadPrograms();
                adapter.notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        recyclerView =  view.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initializing the programs list
        programsList = new ArrayList<>();
        
        // Fetch and parse Json then load it to the recylerview using loadPrograms method
        loadPrograms();
        
        
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void loadPrograms() {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.PROGRAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject program = array.getJSONObject(i);

                                //adding the product to product list
                                programsList.add(new Programs(
                                        program.getInt("id"),
                                        program.getString("name"),
                                        program.getString("description"),
                                        program.getString("photo")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                             adapter = new ProgramsAdapter(getContext(), programsList, new ProgramsAdapter.OnProgramsListener() {
                                @Override
                                public void onProgramsClick(int position) {
                                    Intent intent = new Intent(getActivity(), ProgramsContainer.class);
                                    intent.putExtra("selected_program", programsList.get(position));
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
    public void onProgramsClick(int position) {

    }
}
