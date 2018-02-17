package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

import static android.view.View.GONE;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView sandwichImage;
    private TextView alsoKnownLabel;
    private TextView alsoKnownTv;
    private TextView descriptionTv;
    private TextView originLabel;
    private TextView originTv;
    private TextView ingredientsTv;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sandwichImage = findViewById(R.id.image_iv);
        alsoKnownLabel = findViewById(R.id.also_known_label);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        descriptionTv = findViewById(R.id.description_tv);
        originLabel = findViewById(R.id.origin_label);
        originTv = findViewById(R.id.origin_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichImage);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        String alsoKnownAs = getItems(sandwich.getAlsoKnownAs());
        if (!alsoKnownAs.isEmpty()) {
            alsoKnownTv.setText(alsoKnownAs);
        }
        else {
            alsoKnownLabel.setVisibility(GONE);
            alsoKnownTv.setVisibility(GONE);
        }

        descriptionTv.setText(sandwich.getDescription());

        String origin = sandwich.getPlaceOfOrigin();
        if (!origin.isEmpty()) {
            originTv.setText(origin);
        }
        else {
            originLabel.setVisibility(GONE);
            originTv.setVisibility(GONE);
        }


        ingredientsTv.setText(getItems(sandwich.getIngredients()));
    }

    private String getItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i));
            if (i < items.size() -1) {
                sb.append(", ");
            } else {
                sb.append(".");
            }
        }
        return sb.toString();
    }
}
