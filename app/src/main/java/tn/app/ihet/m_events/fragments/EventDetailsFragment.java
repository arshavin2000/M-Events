package tn.app.ihet.m_events.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tn.app.ihet.m_events.R;
import tn.app.ihet.m_events.adapters.DataHolder;
import tn.app.ihet.m_events.model.Event;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetailsFragment newInstance(String param1, String param2) {
        EventDetailsFragment fragment = new EventDetailsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        Event event = DataHolder.getInstance().getEvent();
        ImageView imageView = view.findViewById(R.id.image);
        TextView description = view.findViewById(R.id.description);
        TextView name = view.findViewById(R.id.title);
        TextView nb = view.findViewById(R.id.nb);
        TextView price = view.findViewById(R.id.price);
        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateOrDeleteEventFragment updateOrDeleteEventFragment = UpdateOrDeleteEventFragment.getInstance();
                updateOrDeleteEventFragment.show(getFragmentManager(),UpdateOrDeleteEventFragment.TAG);
            }
        });
        description.setText(event.getDescription());
        name.setText(event.getName());
        nb.setText(event.getDate_debut());
        price.setText("Prix : "+ event.getPrix() + " DT");

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("IHET");
        // set new toolbar
        AppCompatActivity appCompatActivity = ((AppCompatActivity) getActivity());
        appCompatActivity.setSupportActionBar(toolbar);

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        CardView sv =  view.findViewById(R.id.card);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) sv
                .getLayoutParams();

        layoutParams.setMargins(0, Double.valueOf(height * 0.3).intValue(), 0, 0);
        sv.setLayoutParams(layoutParams);


//        description.setText(event.getDescription());
        if(event.getImage() != 0)
        Picasso.with(getContext()).load(event.getImage())
                .resize(width, Double.valueOf(height * 0.3).intValue())
               .into(imageView);
        else
        {
            Bitmap bitmap = BitmapFactory.decodeFile(event.getImageString());
            imageView.setImageBitmap(bitmap);
            imageView.getLayoutParams().height = height/3;
            imageView.getLayoutParams().width = width;
            imageView.requestLayout();


            // Picasso.with(context).load(event.getImageString()).resize(width,height/3).into(picture);
        }
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

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
