package com.example.jsondata;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1= findViewById(R.id.t1);



        josnTask jTask= new josnTask();
        jTask.execute();



    }




    public class josnTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {


            HttpURLConnection httpURLConnection = null; // Can be disconnect connection in the final catch block.. That's why null is initialize..

            BufferedReader bufferedReader = null;  // same thing as a http url connection stop
            StringBuffer lastbuffer= new StringBuffer();

            String name = null;
            Integer age=null;
            String description=null;


            try {
                URL url= new URL("https://api.myjson.com/bins/vzzla");
                httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream= httpURLConnection.getInputStream(); // input stream read the line to enter the any link or api
                bufferedReader= new BufferedReader(new InputStreamReader(inputStream)); //Buffer reader convert the bite code to character.
                StringBuffer stringBuffer= new StringBuffer(); //Line to line data receive
                String line="";  //line to line read
                while ((line= bufferedReader.readLine()) !=null){
                    stringBuffer.append(line);

                }


                String file= stringBuffer.toString();
                JSONObject fileObject= new JSONObject(file); //json er object banano holo
                JSONArray jsonArray= fileObject.getJSONArray("studentinfo");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject arrayObject= jsonArray.getJSONObject(i);
                    name= arrayObject.getString("name");
                    age= arrayObject.getInt("age");
                    description= arrayObject.getString("description");
                    lastbuffer.append(name+"\n"+age+"\n"+description+"\n\n");
                    //


                }



                return lastbuffer.toString();







            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            t1.setText(s);

        }
    }

}
