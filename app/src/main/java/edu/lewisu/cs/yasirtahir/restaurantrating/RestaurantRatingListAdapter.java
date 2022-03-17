package edu.lewisu.cs.yasirtahir.restaurantrating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantRatingListAdapter
        extends RecyclerView.Adapter<RestaurantRatingListAdapter.RatingHolder> {
    private List<RestaurantRating> mRestaurantRatings;
    private final Context context;

    public interface ListAdapterOnClickHandler{
        void onClick(RestaurantRating restaurantRating);
    }

    private final ListAdapterOnClickHandler clickHandler;

    public RestaurantRatingListAdapter(Context context, ListAdapterOnClickHandler clickHandler) {
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rating_row, parent, false);
        return new RatingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingHolder holder, int position) {
        if(mRestaurantRatings != null){
            RestaurantRating ratingObject = mRestaurantRatings.get(position);

            holder.mRowRestaurantNameView.setText(ratingObject.getRestaurantName());
            holder.mRowCommentsView.setText((ratingObject.getComments()));
            //holder.mRowLikedTypeView.setText(ratingObject.getLikedType());

            int rating = ratingObject.getRating();
            String ratingString = context.getResources().getQuantityString(R.plurals.star_rating, rating, rating);
            holder.mRowRatingView.setText(ratingString);

        }
    }

    @Override
    public int getItemCount() {
        if(mRestaurantRatings!=null){
            return mRestaurantRatings.size();
        }
        return 0;
    }

    public void setRestaurantRatings(List<RestaurantRating> restaurantRatings){
        mRestaurantRatings = restaurantRatings;
        notifyDataSetChanged();
    }

    class RatingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mRowRestaurantNameView;
        private final TextView mRowCommentsView;
        //private final TextView mRowLikedTypeView;
        private final TextView mRowRatingView;

        public RatingHolder(@NonNull View itemView) {
            super(itemView);
            mRowRestaurantNameView = itemView.findViewById(R.id.row_restaurant_name_view);
            mRowCommentsView = itemView.findViewById(R.id.row_comments_view);
            //mRowLikedTypeView = itemView.findViewById(R.id.row_liked_type_view);
            mRowRatingView = itemView.findViewById(R.id.row_restaurant_rating_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            RestaurantRating ratingObject = mRestaurantRatings.get(adapterPosition);
            clickHandler.onClick(ratingObject);
        }
    }
}
