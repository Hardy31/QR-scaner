package ru.trk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_PERMISSION = 5555;
    private final String[] REQUEST_PERMISSION = new String[]{"android.permission.CAMERA"};

    private final int SUSPENSION_TIME = 5000;   //5 секунд между запросами
    PreviewView mPreviewView;
    public boolean isProcess;
    ImageCapture imageCapture;


    private EditText prefixUrl_EditText;
    private TextView fullUrl_TextView;
    private Button submitRequest_Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prefixUrl_EditText = findViewById(R.id.prefixUrl);
        fullUrl_TextView = findViewById(R.id.fullUrl);
        submitRequest_Button = findViewById(R.id.submit_request_button);


        mPreviewView = findViewById(R.id.camera);

        if (allPermissionGranted()){
            startCamera();
        }else {
            ActivityCompat.requestPermissions(this, REQUEST_PERMISSION, REQUEST_CODE_PERMISSION);
        }

        //отправка запроса на сервер при нажатии на кнопку
        submitRequest_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String prefixUrl = prefixUrl_EditText.getText().toString();
                String prefixUrl = "https://192.168.31.207:8080/qr/";
                String qr = "2";
                String uri = prefixUrl + qr;



                //тправка запроса
//                getRequest(url);

//                new RequestSender().execute(url);
                URL urlf;
                HttpURLConnection connection;

                try {
                     urlf = new URL(uri);
                    http://localhost:8080/qr/2



                    connection = (HttpURLConnection) urlf.openConnection();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Toast.makeText(MainActivity.this, "OK!!!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Gkj[j!!!", Toast.LENGTH_SHORT).show();
                    }

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }
        });

    }

    private boolean allPermissionGranted(){
        for (String permission: REQUEST_PERMISSION){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION){
            if(allPermissionGranted()){
                startCamera();
            }else {
                this.finish();  //закрываем приложение, если нет необходимых разрешений.
            }
        }
    }

    //    Обработчик декодированного на изображении QR Кода
    public void qRCodeHandler(String qrCodeText){

        //https://www.youtube.com/watch?v=450JZOgdeig&list=PLDyJYA6aTY1nZqYprT1PKtDFthBcZWAMZ&index=3
//        String url ="http://192.168.31.207:8080/id/";
//        String CodeText = url+qrCodeText;

        String prefix = prefixUrl_EditText.getText().toString();
        String qr = qrCodeText;
        String url = prefix + qr;
        fullUrl_TextView.setText(url);

        Context context = this;
        runOnUiThread(()-> Toast.makeText(context,url, Toast.LENGTH_LONG).show());

        //гдето здесь должен  быть обработчик нажатия на Toast c Get Request
        //https://stackoverflow.com/questions/25217486/is-it-possible-to-provide-click-listener-to-toast-in-android



        new Thread(
                ()->{
                    try {
                        Thread.sleep(SUSPENSION_TIME);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    isProcess = false;
                }
        ).start();



    }

//    Метод привязки камеры к предпросмотрщику
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider){
        CameraSelector cameraSelector = new CameraSelector
                .Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().build();

        imageAnalysis.setAnalyzer(Executors.newFixedThreadPool(1), new QRCodeDecoder(this));


        ImageCapture.Builder builder = new ImageCapture.Builder();

        HdrImageCaptureExtender hdrImageCaptureExtender = HdrImageCaptureExtender.create(builder);

        if(hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)) {
            hdrImageCaptureExtender.enableExtension(cameraSelector);

        }

        Preview preview = new Preview.Builder().build();
        imageCapture = builder
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .build();
        preview.setSurfaceProvider(mPreviewView.createSurfaceProvider());

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageAnalysis, imageCapture);


    }

    private void startCamera(){
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(()->{
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException ignored) {
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void getRequest(String url){
        // https://ru.stackoverflow.com/questions/169762/%D0%9A%D0%B0%D0%BA-%D0%BE%D1%82%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-get-%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81-%D0%BD%D0%B0-android
//        Request request = new Request.Builder()
//                .url(url)
//                .build();

//        new OkHttpClient().newCall(request)
//                .enqueue(new Callback() {
//                    @Override
//                    public void onFailure(final Call call, IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException {
//                        String res = response.body().string();
//                    }
//                });




//        new RequestSender().execute(url);
    }



    private class RequestSender extends AsyncTask <String, String, String> {

//        protected  void onPreExecute(){
//            super.onPreExecute();
//            Toast.makeText(MainActivity.this, "RequestSender - onPreExecute ", Toast.LENGTH_SHORT).show();
//        }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;

            URL url = null;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(connection != null)
                connection.disconnect();

            Toast.makeText(MainActivity.this, "RQVEST", Toast.LENGTH_SHORT).show();

            return "Запрос ущел";
        }
    }

}