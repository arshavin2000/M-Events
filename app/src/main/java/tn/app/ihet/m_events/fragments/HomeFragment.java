package tn.app.ihet.m_events.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tn.app.ihet.m_events.R;
import tn.app.ihet.m_events.adapters.DataHolder;
import tn.app.ihet.m_events.adapters.EventAdapter;
import tn.app.ihet.m_events.db.EventManager;
import tn.app.ihet.m_events.interfaces.RecyclerViewOnClickPosition;
import tn.app.ihet.m_events.model.Event;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private static List<Event> events = new ArrayList<>();
    public static  EventAdapter eventAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final EventManager eventManager = new EventManager(getContext());

        events = eventManager.getAllEvents();

        if (events.size() < 1) {
            Resources resources = Objects.requireNonNull(getContext()).getResources();
            int resourceId = resources.getIdentifier("event1", "drawable",
                    getContext().getPackageName());
            final Event event1 = new Event(1, "La cuisine tunisienne", "Déguster un menu 100% tunisien, c’est possible cette semaine du mardi à jeudi soir grâce aux étudiants de Sidi Dhrif qui sont accompagné par les meilleurs chefs de gastro tunisien  comme chef Haykil bin ZAYDA etc ,,, pour vous faire revivre au beau vieux temps  la cuisine tunisienne de nous grand-mère avec une touche moderne et raffinée  \n", "NB : soyer présent avant une demi heure de l’heure de diner ", "", 15.0, resourceId, 0.0);
            eventManager.addEvent(event1);

            resourceId = resources.getIdentifier("event2", "drawable",
                    getContext().getPackageName());
            Event event2 = new Event(2, "Soirée française", "Cette semaine du mardi à jeudi  toutes les soirées sont  dans le thème de la gastronomie française créer par les futures chefs de Sidi Dhrif, des démonstrations culinaires et des repas spéciaux. Le but de la démarche est de faire découvrir la diversité de la cuisine gastronomique d’aujourd’hui et de promouvoir toute la richesse de la cuisine et de la pâtisserie.\n", "NB : soyer présent avant une demi heure de l’heure de diner ", "", 15.0, resourceId, 0.0);
            eventManager.addEvent(event2);

            resourceId = resources.getIdentifier("event3", "drawable",
                    getContext().getPackageName());
            Event event3 = new Event(3, "Soirée pêcheur ", "La cuisine de mer est un continent vierge dont les explorateurs rechignent à sortir des sentiers battus. Cet événement, organisé dans le cadre d’exploiter les ressources maritime tunisienne. Une excellente initiative pour vivre la richesse et la qualité des produits de mer ; un pur délice. Des produits frais et une touche tunisienne par les étudiants de Sidi Dhrif que cette semaine vont nous rêver par des plats gastronomique.\n", "NB : soyer présent avant une demi heure de l’heure de diner", "", 15.0, resourceId, 0.0);
            eventManager.addEvent(event3);

            resourceId = resources.getIdentifier("event4", "drawable",
                    getContext().getPackageName());
            Event event4 = new Event(4, "La cuisine tunisienne", "Déguster un menu 100% tunisien, c’est possible cette semaine du mardi à jeudi soir grâce aux étudiants de Sidi Dhrif qui sont accompagné par les meilleurs chefs de gastro tunisien  comme chef Haykil bin ZAYDA etc ,,, pour vous faire revivre au beau vieux temps  la cuisine tunisienne de nous grand-mère avec une touche moderne et raffinée  \n", "NB : soyer présent avant une demi heure de l’heure de diner \n", "", 15.0, resourceId, 0.0);
            eventManager.addEvent(event4);


            resourceId = resources.getIdentifier("event5", "drawable",
                    getContext().getPackageName());
            Event event5 = new Event(5, "Soirée française", "Cette semaine du mardi à jeudi  toutes les soirées sont  dans le thème de la gastronomie française créer par les futures chefs de Sidi Dhrif, des démonstrations culinaires et des repas spéciaux. Le but de la démarche est de faire découvrir la diversité de la cuisine gastronomique d’aujourd’hui et de promouvoir toute la richesse de la cuisine et de la pâtisserie.\n", "NB : soyer présent avant une demi heure de l’heure de diner \n", "", 15.0, resourceId, 0.0);
            eventManager.addEvent(event5);


        }

         eventAdapter = new EventAdapter(events, getContext(), getActivity(), new RecyclerViewOnClickPosition() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                Log.e("hama", "onClick: ");
                EventDetailsFragment fragment = new EventDetailsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
                DataHolder.getInstance().setEvent(events.get(position));


                System.out.println("EVENTS "+events.get(position).toString());
            }
        });
        recyclerView.setAdapter(eventAdapter);



        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                eventAdapter.clear();
                // ...the data has come back, add new items to your adapter...
                 events =  eventManager.getAllEvents();
                 eventAdapter.setData(events);
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEventFragment paymentDialog = AddEventFragment.getInstance();
                paymentDialog.show(getFragmentManager(), AddEventFragment.TAG);
            }
        });
        return view ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
