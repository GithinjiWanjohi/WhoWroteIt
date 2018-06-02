package com.example.gyth.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBook extends AsyncTask <String, Void, String>{
    private TextView mTitleText;
    private TextView mAuthorText;

    public FetchBook(TextView mTitleText, TextView mAuthorText){
        this.mTitleText = mTitleText;
        this.mAuthorText = mAuthorText;
    }

    @Override
    protected String doInBackground(String... params) {
        return NetworkUtils.getBookInfo(params[0]);
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        try{
            // Convert the response into a JSON object
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSON Array of book items
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Iterate through the results
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                String authors = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try{
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }

                // Update TextViews if both title and author exist then return
                if (title != null && authors != null){
                    mTitleText.setText(title);
                    mAuthorText.setText(authors);
                    return;
                } else{
                    mTitleText.setText(R.string.no_result_found);
                    mAuthorText.setText("");
                }
            }
        }catch (Exception e){
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results
            mTitleText.setText(R.string.no_result_found);
            mAuthorText.setText("");
            e.printStackTrace();
        }
    }
}
