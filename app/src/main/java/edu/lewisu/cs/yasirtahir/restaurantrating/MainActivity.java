package edu.lewisu.cs.yasirtahir.restaurantrating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RestaurantRating mRestaurantRating;
    private EditText mRestaurantNameEditText;
    private EditText mCommentsEditText;
    private Spinner mCuisineTypeSpinner;
    private RatingBar mRatingBarr;
    private Button mSubmitButton;
    private RadioGroup mLikedRadio;
    private RadioButton mLikedButtonId;
    private RadioButton mLikedButton;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.rating_container);

        if(fragment == null){
            int ratingId = getIntent().getIntExtra("id", 0);
            fragment = RatingFragment.newInstance(ratingId);
            fragmentManager.beginTransaction()
                    .replace(R.id.rating_container, fragment)
                    .commit();
        }

    }


}