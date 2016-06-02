package com.example.zaccianflone.multithreaded;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

        ListView listView ;
        private ProgressBar progressBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = (ListView) findViewById(R.id.list);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>());

            listView.setAdapter(adapter);

            progressBar = (ProgressBar) findViewById(R.id.myprogressbar);

        }


        public void Create (View view) {

            new MyTask().execute(1);

        }


        public void Load (View view) {

            new MyTask().execute(2);

            }

        public void Clear (View view) {

            new MyTask().execute(3);

        }

    class MyTask extends AsyncTask<Integer, String, Void>{

        private ArrayAdapter<String> adapter;
        private int progressStatus=0;


        @Override
        protected void onPreExecute() {

            adapter = (ArrayAdapter<String>) listView.getAdapter();


        }

        @Override
        protected Void doInBackground(Integer... params) {

            if (params[0] == 1)
            {
                String fileName = "numbers.txt";

                String Message;
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(fileName, MainActivity.this.MODE_PRIVATE);
                    for (int i=1; i<=10; i++) {
                        Message=Integer.toString(i)+"\n";
                        outputStream.write(Message.getBytes());
                        publishProgress("create");
                        Thread.sleep(250);
                    }
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (params[0] == 2)
            {

                try {
                    String Message;
                    int i=0;
                    FileInputStream fileInputStream = openFileInput("numbers.txt");
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    while((Message=bufferedReader.readLine())!=null) {
                        publishProgress(Message);
                        Thread.sleep(250);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            if (params[0] == 3)
            {
                publishProgress("clear");
            }




            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {



            if (values[0]=="clear"){
                adapter.clear();
                progressBar.setProgress(0);
            }
            else if(values[0]=="create"){
                progressStatus++;
                progressBar.setProgress(progressStatus*10);
            }
            else {
                adapter.add(values[0]);
                progressStatus++;
                progressBar.setProgress(progressStatus*10);
            }


        }

        protected void onPostExecute(Void result){
            progressBar.setProgress(0);
        }


    }





}

