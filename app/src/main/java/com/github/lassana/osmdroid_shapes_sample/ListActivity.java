package com.github.lassana.osmdroid_shapes_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author Nikolai Doronin {@literal <lassana.nd@gmail.com>}
 * @since 4/1/16.
 */
public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final int[] buttons = new int[]{
                R.id.btn_example_1,
                R.id.btn_example_2,
                R.id.btn_example_3,
                R.id.btn_example_4,
                R.id.btn_example_5};
        for (int id : buttons) {
            //noinspection ConstantConditions
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int index = 0;
        switch (v.getId()) {
            case R.id.btn_example_1:
                index = 0;
                break;
            case R.id.btn_example_2:
                index = 1;
                break;
            case R.id.btn_example_3:
                index = 2;
                break;
            case R.id.btn_example_4:
                index = 3;
                break;
            case R.id.btn_example_5:
                index = 4;
                break;
            default:
                break;
        }
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_GEO_DATA_INDEX, index);
        startActivity(intent);
    }
}
