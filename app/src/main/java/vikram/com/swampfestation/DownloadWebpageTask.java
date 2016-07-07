package vikram.com.swampfestation;

/**
 * Created by vikram on 7/1/16.
 */

import android.graphics.Point;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DownloadWebpageTask extends AsyncTask<Void, Void, String[][]> {
    private String url;
    private InstructionsActivity act;

    //"https://spreadsheets.google.com/tq?key=1G_zFPHjp4WI1qpWwTCL8mASi_Sro0PBIIkkCkl4Ea_s";
    public DownloadWebpageTask(InstructionsActivity act) {
        //String spreadsheetID = "1G_zFPHjp4WI1qpWwTCL8mASi_Sro0PBIIkkCkl4Ea_s";
        //this.url =  "https://spreadsheets.google.com/feeds/list/" + spreadsheetID + "/od6/public/values?alt=json";
        this.url = "https://spreadsheets.google.com/feeds/cells/1G_zFPHjp4WI1qpWwTCL8mASi_Sro0PBIIkkCkl4Ea_s/od6/public/basic?alt=json-in-script";
        this.act = act;
    }

    @Override
    protected String[][] doInBackground(Void... voids) {

        // params comes from the execute() call: params[0] is the url.
        try {
            String[][] data = parse(downloadUrl(url));
            if (data != null) {
                process(data);
            }
            return data;
        } catch (IOException e) {
            return null;
        }
    }

    private void process(String[][] data) {
        for (int i = 1; i < data[0].length; i++) {
            Constants.ch.addCharacter(data, i);
        }
        int index = 8;
        while (data[index][0] == null) {
            String key = data[index][1];
            String value = data[index][2];
            try {
                int val = Integer.parseInt(value);
                Constants.intVar.put(key, val);
            } catch (Exception e) {
                Toast.makeText(act, "bad data" + key + " " + value, Toast.LENGTH_SHORT).show();
            }
            index++;
        }
        int k = 5;
        while (data[index][k] == null) {
            k++;
        }
        index++;
        int msgStart = 4;
        int msgEnd = k - 1;
        for (int i = index; i < data.length; i++) {
            if (data[i][0] != null && data[i][3] != null) {
                String id = data[i][0];
                String type = data[i][3];
                String title = "";
                if (data[i][1] != null) {
                    title = data[i][1];
                }
                String desc = "";
                if (data[i][2] != null) {
                    desc = data[i][2];
                }

                ArrayList<String> msgs = new ArrayList<String>();
                for (int j = msgStart; j <= msgEnd; j++) {
                    if (data[i][j] != null) {
                        msgs.add(data[i][j]);
                    }
                }
                ArrayList<String> buttons = new ArrayList<String>();
                try {
                    for (int j = k; data[i][j] != null; j++) {
                        buttons.add(data[i][j]);
                    }
                } catch (Exception e){

                }
                Screen sc = new Screen(id, title, desc, type, msgs, buttons);
                Constants.screens.put(id, sc);
            }
        }
    }

    private String[][] parse(String s) {
        int start = s.indexOf("{", s.indexOf("{") + 1);
        int end = s.lastIndexOf("}");
        String jsonResponse = s.substring(start, end);
        HashMap<Character, Integer> charIndMAp = new HashMap<Character, Integer>();
        HashMap<Point, String> content = new HashMap<Point, String>();
        Character ch = 'A';
        for (int i = 0; i < 26; i++) {
            charIndMAp.put(ch, i);
            ch++;
        }
        try {
            JSONObject table = new JSONObject(jsonResponse);
            JSONArray cells = table.getJSONArray("entry");
            int maxY = 0;
            int maxX = 0;
            for (int i = 0; i < cells.length(); i++) {
                JSONObject cell = cells.getJSONObject(i);
                String index = cell.getJSONObject("title").getString("$t");
                int y = charIndMAp.get(index.charAt(0));
                int x = Integer.parseInt(index.substring(1)) - 1;
                maxX = Math.max(x, maxX);
                maxY = Math.max(y, maxY);
                content.put(new Point(x, y), cell.getJSONObject("content").getString("$t"));
            }
            String[][] rawData = new String[maxX + 1][maxY + 1];
            for (Map.Entry<Point, String> entry : content.entrySet()) {
                Point pt = entry.getKey();
                rawData[pt.x][pt.y] = entry.getValue();
            }
            return rawData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String[][] result) {
        Toast.makeText(act, "new data fetched!", Toast.LENGTH_SHORT).show();
    }

    private String downloadUrl(String urlString) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();

            String contentAsString = convertStreamToString(is);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}