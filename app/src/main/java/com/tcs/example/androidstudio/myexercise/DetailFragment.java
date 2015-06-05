package com.tcs.example.androidstudio.myexercise;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters of Earthquake
    private static final String ARG_PLACE = "place";
    private static final String ARG_MAGNITUDE = "magnitude";
    private static final String ARG_TIME = "time";
    private static final String ARG_DEPTH = "depth";

    // TODO: Rename and change types of parameters
    private String place;
    private String magnitude;
    private String time;
    private String depth;

    private OnFragmentInteractionListener mListener;

    //Static method for get instance of Detail Fragment
    public static DetailFragment newInstance(String place, String magnitude,String time, String depth) {
        //Set arguments
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLACE, place);
        args.putString(ARG_MAGNITUDE, magnitude);
        args.putString(ARG_TIME, time);
        args.putString(ARG_DEPTH, depth);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get arguments to asign field values
        if (getArguments() != null) {
            place = "PLACE: "+getArguments().getString(ARG_PLACE);
            magnitude = "MAGNITUDE: "+getArguments().getString(ARG_MAGNITUDE);
            time = "DATE & TIME: "+getArguments().getString(ARG_TIME);
            depth = "DEPTH: "+getArguments().getString(ARG_DEPTH);

            Log.i("PLACE IN FRAGMENT",place);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        //Initialite TextViews
        TextView textViewPlace = (TextView) view.findViewById(R.id.place);
        TextView textViewMagnitude = (TextView) view.findViewById(R.id.magnitude);
        TextView textViewTime = (TextView) view.findViewById(R.id.time);
        TextView textViewDepth = (TextView) view.findViewById(R.id.depth);

        //Asign text to TextView
        textViewPlace.setText(place);
        textViewMagnitude.setText(magnitude);
        textViewTime.setText(time);
        textViewDepth.setText(depth);

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
