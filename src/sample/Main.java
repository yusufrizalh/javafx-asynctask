package sample;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.google.gson.Gson;

import com.victorlaerte.asynctask.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class Main extends Application {
    // instance variable
    private static final String JSON_URL = "http://api.openweathermap.org/data/2.5/weather?q=Sumenep&appid=845c5a7e9700d81bcfb6f9c0edcd67ff";

    @Override
    public void start(Stage primaryStage) throws Exception{
        //URI uri = new File("C:\\Users\\Yusuf Rizal\\IdeaProjects\\JavaFxAsyncTask\\src\\sample\\sample.fxml").toURI();
        //Parent root = FXMLLoader.load(uri.toURL());

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        Button btnOpenJson = new Button("Open Data JSON");

        root.getChildren().addAll(btnOpenJson);

        btnOpenJson.setOnAction(e -> {
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
        });

        ScrollPane scrollPane = new ScrollPane(root);
        Scene scene = new Scene(scrollPane, 600, 500);
        primaryStage.setTitle("Consume Data JSON");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        public void onPreExecute() {
            // loading
            // gambar loading
            // progressbar
            System.out.println("Memulai aplikasi diawal");
        }

        @Override
        public String doInBackground(String... strings) {
            /* String resultJson = null
            membuat HttpUrlConnection
            resultJson = getDataJson(UrlJson)
            return resultJson
            * */

            /* membuat method getDataJson()
            * */

            String resultJson = "";

            try {
                URL url_weather = new URL(JSON_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)
                        url_weather.openConnection();

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(
                            httpURLConnection.getInputStream()
                    );
                    BufferedReader bufferedReader = new BufferedReader(
                            inputStreamReader, 8192
                    );

                    String baris = null;
                    while ((baris = bufferedReader.readLine()) != null) {
                        resultJson += baris;
                    }
                    bufferedReader.close();
                    String weather_result = parseResult(resultJson);

                    System.out.println(weather_result);
                } else {
                    System.out.println("Error pada HttpURLConnection");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override

        public void onPostExecute(String sukses) {
            System.out.println("Mengakhiri aplikasi diakhir");
            if (sukses != null) {
                System.out.println("Sukses");
            } else {
                System.out.println("Gagal");
            }
        }

        @Override
        public void progressCallback(Integer... integers) {
            System.out.println("Progress: " + integers[0]);
        }
    }

    private String parseResult(String json) throws JSONException {
        String parsedResult = "";
        JSONObject jsonObject = new JSONObject(json);
        parsedResult += "Jumlah JSONObject: " + jsonObject.length() + "\n\n";

        // menampilkan coord
        JSONObject jsonObjectCoord = jsonObject.getJSONObject("coord");
        Double result_lon = jsonObjectCoord.getDouble("lon");
        Double result_lat = jsonObjectCoord.getDouble("lat");

        return "coord:\t\tlon: " + result_lon + "\t\tlat: " + result_lat;
    }

}
