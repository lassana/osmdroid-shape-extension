package com.github.lassana.osmdroid_shapes_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * @author Nikolai Doronin {@literal <lassana.nd@gmail.com>}
 * @since 4/1/16.
 */
public class ListActivity extends AppCompatActivity {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final Spinner datasetSpinner = (Spinner) findViewById(R.id.spinner_dataset);
        final Spinner styleSpinner = (Spinner) findViewById(R.id.spinner_style);

        final String[] dataset = getResources().getStringArray(R.array.spinner_dataset);
        datasetSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dataset));

        final String[] styles = getResources().getStringArray(R.array.spinner_styles);
        styleSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, styles));

        findViewById(R.id.button_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index = datasetSpinner.getSelectedItemPosition();
                final boolean isBitmapPolygon = styleSpinner.getSelectedItemPosition() == 1;
                final Intent intent = new Intent(ListActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_GEO_DATA_INDEX, index);
                intent.putExtra(MainActivity.EXTRA_USE_BITMAPPOLYGON, isBitmapPolygon);
                startActivity(intent);
            }
        });
    }
}
