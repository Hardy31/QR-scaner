package ru.trk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity1 extends AppCompatActivity {

    private EditText prefixUrl_EditText;
    private TextView fullUrl_TextView;
    private Button submitRequest_Button;
    private  TextView textResponce;
    OkHttpClient client = new OkHttpClient();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        submitRequest_Button = findViewById(R.id.submit_request_button);
        //Обработчик нажатия кнопки - отправка запроса https://192.168.31.207:8080/qr/2 на сервер
        submitRequest_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String prefixUrl = prefixUrl_EditText.getText().toString();
                String prefixUrl = "http://192.168.31.207:8080/id/";
                String qr = "3";
                String uri = prefixUrl + qr;
//                Toast.makeText(MainActivity1.this, uri, Toast.LENGTH_SHORT).show();
//                getRecuest();

// Вариант 1 слитает
//                URL url = null;
//                try {
//                    url = new URL("https://192.168.31.207:8080/qr/2");
//                } catch (MalformedURLException e) {
//                    throw new RuntimeException(e);
//                }
//                HttpURLConnection connection = null;
//                try {
//                    connection = (HttpURLConnection)url.openConnection();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                        Toast.makeText(MainActivity1.this, "OK!!!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(MainActivity1.this, "Not OK!!!", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
// \Вариант 1







////                Вариант3
                OkHttpClient client = new OkHttpClient();
//
                Request request = new Request.Builder()
                        .url(uri)
////                        .url("http://publicobject.com/helloworld.txt")
                        .build();
//
////                Toast.makeText(MainActivity1.this, request.toString(), Toast.LENGTH_SHORT).show();
//
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response response = client.newCall(request).execute();
//                            Toast.makeText(MainActivity1.this, " запрос отправлен", Toast.LENGTH_SHORT).show();

//                            textResponce.setText(response.toString());
                            // Process the response
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Handle the error
                        }
                    }
                });
                thread.start();
//                \Вариант3



////            \Вариант 4 Не работает!!
//                Request request = new Request.Builder()
//                        .url("http://192.168.31.207:8080/qr/2")
//                        .build();
//
//                OkHttpClient client = new OkHttpClient();
//
//                Call call = client.newCall(request);
//                Response response = null;
//                try {
//                    response = call.execute();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//                Toast.makeText(MainActivity1.this, response.code(), Toast.LENGTH_SHORT).show();
////                assertThat(response.code(), equalTo(200));


                // Не работает!!
//                HttpUrl.Builder urlBuilder
//                        = HttpUrl.parse("http://192.168.31.207:8080/qr").newBuilder();
//                urlBuilder.addQueryParameter("id", "1");
//
//                String url = urlBuilder.build().toString();
//                Toast.makeText(MainActivity1.this, url, Toast.LENGTH_SHORT).show();
//
//                Request request = new Request.Builder()
//                        .url(url)
//                        .build();
//                Call call = client.newCall(request);
//                Response response = null;
//                try {
//                    response = call.execute();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
////                assertThat(response.code(), equalTo(200));
                // \Вариант4

            }

//            public void whenAsynchronousGetRequest_thenCorrect(String uri) {
//                Request request = new Request.Builder()
//                        .url(uri)
//                        .build();
//
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//                    public void onResponse(Call call, Response response)
//                            throws IOException {
//                        // ...
//                    }
//
//                    public void onFailure(Call call, IOException e) {
////                        fail();
//                    }
//                });
//            }


//            void getRequest(String uri) throws IOException {
//
//                Request request = new Request.Builder()
//                        .url(uri)
//                        .build();
////                Response response = client.newCall(request).execute();
//
//
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException {
//                        if (!response.isSuccessful()) {
//                            throw new IOException("Unexpected code " + response);
//                        }
//
//                        // you code to handle response
//                    }
//                });
//            }


//            //Вариант 8
//            public  void getRecuest(){
//                new Thread(new Runnable(){
//
//                    @Override
//                    public void run() {
//                        try {
//                            URL url = new URL("https://192.168.31.207:8080/id/3");
//                            System.out.println("--------------------" + url.toString());
//                            fullUrl_TextView.setText(url.toString());
//
//                            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//                            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                                textResponce.setText("Отправлено");
//                            } else {
//                                textResponce.setText("Ошибка");
//                            }
//                        } catch (Exception e) {
//                            textResponce.setText("Исключение");
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }
//
//            // \Вариант8

        });
    }
}
