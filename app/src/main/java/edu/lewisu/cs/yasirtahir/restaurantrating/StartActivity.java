package edu.lewisu.cs.yasirtahir.restaurantrating;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class StartActivity extends AppCompatActivity
        implements RestaurantRatingListAdapter.ListAdapterOnClickHandler {
    private boolean mTwoPane;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if(findViewById(R.id.detail_container) != null){
            mTwoPane = true;
        }

        FirebaseApp.initializeApp(this);

        RecyclerView recyclerView = findViewById(R.id.rating_recycler_view);
        RestaurantRatingListAdapter mRestaurantRatingListAdapter = new RestaurantRatingListAdapter(getApplicationContext(), this);
        recyclerView.setAdapter(mRestaurantRatingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRestaurantRatingListAdapter.setRestaurantRatings(RestaurantRatingDatabase.getInstance(this).getRestaurantRatings());


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = sharedPreferences.getBoolean(getString(R.string.pref_theme_key), false);
        if(darkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK) {
                        //Toast.makeText(getApplicationContext(), "I am back", Toast.LENGTH_SHORT).show();
                        Intent data = result.getData();
                        int rating = data.getIntExtra("rating", 0);
                        String restaurantName = data.getStringExtra("restaurantName");
                        String toastString = getResources().getString(R.string.rating_entered);

                        String displayRestaurant = getResources().getString(R.string.display_restaurant, restaurantName);
                        toastString += displayRestaurant;

                        String ratingString = getResources().getQuantityString(R.plurals.star_rating, rating, rating);
                        toastString += ratingString;

                        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT).show();

                    }
                }
        );

        /*Button launchRatingButton = findViewById(R.id.rating_button);
        launchRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ratingIntent = new Intent(getApplicationContext(), MainActivity.class);
                launcher.launch(ratingIntent);
            }
        });*/

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_add_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTwoPane){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment fragment = RatingFragment.newInstance(0);
                    fragmentManager.beginTransaction()
                            .replace(R.id.detail_container, fragment)
                            .commit();
                } else {
                    Intent ratingIntent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(ratingIntent);
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int id = (int)viewHolder.itemView.getTag();

            }
        }).attachToRecyclerView(recyclerView);

        ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                (result) -> {
                    if(result.getResultCode() == RESULT_CANCELED){
                        finish();
                    }
                }
        );

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){
                    Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setTheme(R.style.Theme_RestaurantRating)
                            .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build()))
                            .build();
                    signInLauncher.launch(signInIntent);
                }
            }
        };

    }

    @Override
    public void onClick(RestaurantRating restaurantRating) {
        if(mTwoPane){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = RatingFragment.newInstance(0);
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();
        } else {
            Intent ratingIntent = new Intent(this, MainActivity.class);
            ratingIntent.putExtra("id", 0);
            startActivity(ratingIntent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
        }

        if(id == R.id.sign_out){
            AuthUI.getInstance().signOut(this);
        }
        return super.onOptionsItemSelected(item);
    }
}