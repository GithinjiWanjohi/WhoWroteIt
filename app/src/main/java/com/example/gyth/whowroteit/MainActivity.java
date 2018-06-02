package com.example.gyth.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mBookInput;
    private TextView mAuthorText;
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the views
        mBookInput = (EditText) findViewById(R.id.bookInput);
        mAuthorText = (TextView)findViewById(R.id.authorText);
        mTitleText = (TextView) findViewById(R.id.titleText);
    }

    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();
        new FetchBook(mTitleText, mAuthorText).execute(queryString);

        // hide keyboard once search button is clicked
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        // Initialize the connection variables
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //Check if there is an active network connection to the internet
        if(networkInfo != null && networkInfo.isConnected() && queryString.length()!=0){
            // Call to execute FetchBook AsyncTask
            new FetchBook(mTitleText, mAuthorText).execute(queryString);

            mTitleText.setText(R.string.loading);
            mAuthorText.setText("");
        } else{
            if(queryString.length() == 0){
                mTitleText.setText("");
                mAuthorText.setText(R.string.enter_search_term);
            }else{
                mTitleText.setText("");
                mAuthorText.setText(R.string.check_network_connection);
            }
        }
    }
}
