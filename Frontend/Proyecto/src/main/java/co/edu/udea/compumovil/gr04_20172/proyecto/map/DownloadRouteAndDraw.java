package co.edu.udea.compumovil.gr04_20172.proyecto.map;

/**
 * Created by jersonlopez on 25/11/17.
 */

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownloadRouteAndDraw extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
    private GoogleMap mMap;
    ArrayList<LatLng> points;

    public DownloadRouteAndDraw(GoogleMap mMap) {
        this.mMap = mMap;
    }

    @Override
    protected List<HashMap<String, String>> doInBackground(String... url) {
        String data = "";
        try {
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return parseDataToRoutes(data);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();/**/
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private List<HashMap<String, String>> parseDataToRoutes(String data) {
        JSONObject jObject;
        List<HashMap<String, String>> routes = null;

        try {
            jObject = new JSONObject(data);
            DirectionsJSONParser parser = new DirectionsJSONParser();

            routes = parser.parse(jObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> result) {
        if(result == null) return; // If no internet connection or no routes are founded
        points = new ArrayList<>();
        for (int j = 0; j < result.size(); j++) {
            HashMap<String, String> point = result.get(j);
            double lat = Double.parseDouble(point.get("lat"));
            double lng = Double.parseDouble(point.get("lng"));
            LatLng position = new LatLng(lat, lng);

            points.add(position);
        }
        drawPolylineOnMap();
    }

    private void drawPolylineOnMap() {
        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(points);
        lineOptions.width(5);
        lineOptions.color(Color.BLUE);
        lineOptions.geodesic(true);

        mMap.addPolyline(lineOptions);
    }
}