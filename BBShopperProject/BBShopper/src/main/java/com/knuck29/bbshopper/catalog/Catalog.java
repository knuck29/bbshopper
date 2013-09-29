package com.knuck29.bbshopper.catalog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import javax.inject.Inject;

import android.os.AsyncTask;

import com.google.gson.*;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by knolker on 9/28/13.
 */
public class Catalog extends Observable {

	private static String BASE_URL = "https://emsapi.bbhosted.com";

	OkHttpClient mHttpClient = new OkHttpClient();
	ArrayList<Category> mCategories = new ArrayList<Category>();

	@Inject
	public Catalog() {
		initialize();
	}

	public ArrayList<Category> getCategories() {
		return mCategories;
	}

	public void initialize() {
		loadCatalog();
	}

	public void loadCatalog() {
		DownloadCatalog task = new DownloadCatalog();
		task.execute(BASE_URL);
	}

	private byte[] readFully(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		for (int count; (count = in.read(buffer)) != -1;) {
			out.write(buffer, 0, count);
		}
		return out.toByteArray();
	}

	private class DownloadCatalog extends AsyncTask<String, Boolean, Boolean> {

		@Override
		protected Boolean doInBackground(String... urls) {

			URL url = null;

			if (urls.length > 0) {
				try {
					url = new URL(urls[0]);
				}
				catch (MalformedURLException e) {
					e.printStackTrace();
				}

				HttpURLConnection connection = mHttpClient.open(url);
				InputStream in = null;
				try {
					// Read the response.
					in = connection.getInputStream();
					byte[] response = readFully(in);

					String jsonResponse = new String(response, "UTF-8");
					JsonElement json = new JsonParser().parse(jsonResponse);

					JsonObject responseObject = json.getAsJsonObject();

					if (responseObject.has("categories")) {

						JsonElement categoryElement = responseObject.get("categories");

						if (!categoryElement.isJsonNull() && categoryElement.isJsonArray()) {

							JsonArray array = categoryElement.getAsJsonArray();

							Iterator iterator = array.iterator();

							while (iterator.hasNext()) {
								JsonElement json2 = (JsonElement) iterator.next();
								Gson gson = new Gson();
								Category category = gson.fromJson(json2, Category.class);
								mCategories.add(category);
								setChanged();
							}
						}
					}

				}
				catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				finally {
					if (in != null)
						try {
							in.close();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			if (success) {
				notifyObservers();
			}
			super.onPostExecute(success);
		}
	}

}
