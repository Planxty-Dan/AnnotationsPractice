package com.detroitlabs.kyleofori.annotationspractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;

import java.util.List;

@EActivity (R.layout.activity_homepage)
public class MainActivity extends ActionBarActivity implements KhanAcademyApiCallback {

    public static List<Lesson> khanAcademyLessons;
    private KhanAcademyApi khanAcademyApi = KhanAcademyApi.getKhanAcademyApi();

    @ViewById (R.id.resource_loading_status)
    TextView txtResourceLoadingStatus;

    @ViewById (R.id.btn_go_to_search)
    Button btnGoToSearch;

    @ViewById (R.id.progress_wheel)
    ProgressBar progressWheel;

    @Click (R.id.btn_go_to_search)
    void openSearchResultsListFragment() {
        Intent toResultsActivity = new Intent(this, ResultsActivity_.class);
        startActivity(toResultsActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        khanAcademyApi.getKhanAcademyPlaylists(this);
    }

    @Override
    public void onSuccess(JSONArray response) {
        txtResourceLoadingStatus.setText(R.string.resources_loaded);
        progressWheel.setVisibility(View.GONE);
        khanAcademyLessons = KhanAcademyJSONParser.parseJSONObject(response);
    }

    @Override
    public void onError() {
        Toast.makeText(this, R.string.error_loading_lesson_plans, Toast.LENGTH_SHORT).show();
        btnGoToSearch.setEnabled(false);
    }
}
