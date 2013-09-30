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

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.*;
import com.knuck29.bbshopper.R;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by knolker on 9/28/13.
 */
public class Catalog extends Observable {

	Context mContext;
	OkHttpClient mHttpClient = new OkHttpClient();
	ArrayList<Category> mCategories = new ArrayList<Category>();
	Breadcrumb mParentBreadcrumb = null;

	@Inject
	public Catalog(Context context) {
		mContext = context;
	}

	public ArrayList<Category> getCategories() {
		return mCategories;
	}

	public void loadCatalog(String url) {
		DownloadCatalog task = new DownloadCatalog();
		task.execute(url);
	}

	public void navigateBreadcrumbBack() {
		if ((mParentBreadcrumb != null) && (mParentBreadcrumb.getHref() != null)) {
			loadCatalog(mContext.getString(R.string.base_url) + mParentBreadcrumb.getHref());
		}
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
					mCategories.clear();
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
					if (responseObject.has("breadcrumbs")) {

						JsonElement breadcrumbsElement = responseObject.get("breadcrumbs");

						if (!breadcrumbsElement.isJsonNull() && breadcrumbsElement.isJsonArray()) {

							JsonArray array = breadcrumbsElement.getAsJsonArray();

							Iterator iterator = array.iterator();

							if (iterator.hasNext()) {
								JsonElement json2 = (JsonElement) iterator.next();
								Gson gson = new Gson();
								Breadcrumb breadcrumb = gson.fromJson(json2, Breadcrumb.class);
								mParentBreadcrumb = breadcrumb;
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
