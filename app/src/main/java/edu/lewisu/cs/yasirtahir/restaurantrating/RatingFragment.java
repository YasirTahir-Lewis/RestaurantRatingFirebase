package edu.lewisu.cs.yasirtahir.restaurantrating;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingFragment extends Fragment {

    private static final String ARG_RATING_ID = "ratingId";
    private RestaurantRating mRestaurantRating;
    private EditText mRestaurantNameEditText;
    private EditText mCommentsEditText;
    private Spinner mCuisineTypeSpinner;
    private RatingBar mRatingBarr;
    private Button mSubmitButton;
    private RadioGroup mLikedRadio;
    public RadioButton mLikedButtonId;
    private Button btnDisplay;
    private SharedPreferences mPreferences;
    private boolean mAdding = false;

    public RatingFragment() {
        // Required empty public constructor
    }


    public static RatingFragment newInstance(int ratingId) {
        RatingFragment fragment = new RatingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RATING_ID, ratingId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int ratingId = 0;
        if (getArguments() != null) {
            ratingId = getArguments().getInt(ARG_RATING_ID);
        }
        if(ratingId != 0){

        }else{
            mAdding = true;
            mRestaurantRating = new RestaurantRating();

            mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String ratingString = mPreferences.getString(getString(R.string.pref_rating_key), "0");
            int default_rating = Integer.parseInt(ratingString);
            mRestaurantRating.setRating(default_rating);

            String defaultRestaurantName = mPreferences.getString("restaurant", "");
            mRestaurantRating.setRestaurantName(defaultRestaurantName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        mRestaurantNameEditText = view.findViewById(R.id.restaurantNameEditText);
        mRestaurantNameEditText.addTextChangedListener(new NameTextListener(mRestaurantNameEditText));

        mCommentsEditText = view.findViewById(R.id.commentsEditText);
        mCommentsEditText.addTextChangedListener(new NameTextListener(mCommentsEditText));

        mCuisineTypeSpinner = view.findViewById(R.id.cuisineTypeSpinner);
        mCuisineTypeSpinner.setOnItemSelectedListener(new CuisineTypeSelectedListener());

        mLikedRadio = (RadioGroup) view.findViewById(R.id.radioLiked);
        mLikedRadio.setOnClickListener(new LikedTypeSelectedListener());

        mRatingBarr = view.findViewById(R.id.ratingbar);
        mRatingBarr.setOnRatingBarChangeListener(new RatingChangedListener());

        mRestaurantNameEditText.setText(mRestaurantRating.getRestaurantName());
        mCommentsEditText.setText(mRestaurantRating.getComments());

        List<String> cuisineTypes = Arrays.asList(getResources().getStringArray(R.array.cuisine_types));
        int index = cuisineTypes.indexOf(mRestaurantRating.getCuisineType());
        mCuisineTypeSpinner.setSelection(index);

        mRatingBarr.setRating(mRestaurantRating.getRating());
        mSubmitButton = view.findViewById(R.id.submitButton);
        mSubmitButton.setOnClickListener(new SubmitButtonListener());
        /*
        String restaurantName = mRestaurantRating.getRestaurantName();
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString("restaurant", restaurantName);
            editor.apply();

            int rating = mRestaurantRating.getRating();
         */
        return view;
    }

    private class SubmitButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(mAdding){

            }else{

            }
        }
    }

    private class CuisineTypeSelectedListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String cuisineType = (String)adapterView.getItemAtPosition(i);
            if (i!=0){
                mRestaurantRating.setCuisineType(cuisineType);

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class RatingChangedListener implements RatingBar.OnRatingBarChangeListener{
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            mRestaurantRating.setRating((int)v);
        }
    }

    private class NameTextListener implements TextWatcher {
        private View editText;

        public NameTextListener(View editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (editText == mRestaurantNameEditText){
                mRestaurantRating.setRestaurantName(charSequence.toString());
            } else if (editText == mCommentsEditText){
                mRestaurantRating.setComments(charSequence.toString());

            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    public class LikedTypeSelectedListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int selectedId = mLikedRadio.getCheckedRadioButtonId();
            mLikedButtonId = (RadioButton) view.findViewById(selectedId);
            String likedType = (String) mLikedButtonId.getText();
            mRestaurantRating.setLikedType(likedType);
        }
    }

}