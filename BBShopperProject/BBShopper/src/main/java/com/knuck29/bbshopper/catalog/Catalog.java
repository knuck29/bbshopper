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
	ArrayList<Product> mProducts = new ArrayList<Product>();
	ArrayList<Breadcrumb> mBreadcrumbs = new ArrayList<Breadcrumb>();
	String mCartJsonString = "";

	@Inject
	public Catalog(Context context) {
		mContext = context;
	}

	public ArrayList<Product> getProducts() {
		return mProducts;
	}

	public void loadCatalog(String url) {
		DownloadCatalog task = new DownloadCatalog();
		task.execute(url);
	}

	public void loadCart() {
		LoadCart task = new LoadCart();
		task.execute();
	}

	public boolean canNavigateBack() {
		return ((mBreadcrumbs != null) && (mBreadcrumbs.size() >= 2));
	}

	public void navigateBreadcrumbBack() {
		if ((mBreadcrumbs != null) && (mBreadcrumbs.size() >= 2)) {
			String parentBreadcrumb = mBreadcrumbs.get(mBreadcrumbs.size() - 2).getHref();
			loadCatalog(mContext.getString(R.string.base_url) + parentBreadcrumb);
		}
	}

	public String getCartString() {
		return mCartJsonString;
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

					mProducts.clear();
					mBreadcrumbs.clear();

					if (responseObject.has("categories")) {

						JsonElement categoryElement = responseObject.get("categories");

						if (!categoryElement.isJsonNull() && categoryElement.isJsonArray()) {

							JsonArray array = categoryElement.getAsJsonArray();

							Iterator iterator = array.iterator();

							while (iterator.hasNext()) {
								JsonElement json2 = (JsonElement) iterator.next();
								Gson gson = new Gson();
								Product product = gson.fromJson(json2, Product.class);
								product.setNodeType(Product.NodeType.Category);
								mProducts.add(product);
								setChanged();
							}
						}
					}
					if (responseObject.has("breadcrumbs")) {

						JsonElement breadcrumbsElement = responseObject.get("breadcrumbs");

						if (!breadcrumbsElement.isJsonNull() && breadcrumbsElement.isJsonArray()) {

							JsonArray array = breadcrumbsElement.getAsJsonArray();

							Iterator iterator = array.iterator();

							while (iterator.hasNext()) {
								JsonElement json2 = (JsonElement) iterator.next();
								Gson gson = new Gson();
								mBreadcrumbs.add(gson.fromJson(json2, Breadcrumb.class));
								setChanged();
							}
						}
					}
					if (responseObject.has("products")) {

						JsonElement productsElement = responseObject.get("products");

						if (!productsElement.isJsonNull() && productsElement.isJsonArray()) {

							JsonArray array = productsElement.getAsJsonArray();

							Iterator iterator = array.iterator();

							while (iterator.hasNext()) {
								JsonElement json2 = (JsonElement) iterator.next();
								if ((!json2.isJsonNull()) && (json2.isJsonObject())) {
									JsonObject pobj = json2.getAsJsonObject();
									Product product = new Product();
									if (pobj.has("id")) {
										product.setId(pobj.get("id").getAsString());
									}
									if (pobj.has("title")) {
										product.setTitle(pobj.get("title").getAsString());
									}
									if (pobj.has("href")) {
										product.setHref(pobj.get("href").getAsString());
									}
									if (pobj.has("image")) {
										JsonElement imageElement = pobj.get("image");
										Image image = new Image();
										if ((!imageElement.isJsonNull()) && (imageElement.isJsonObject())) {
											JsonObject io = imageElement.getAsJsonObject();

											if (io.has("full")) {
												image.setFullImage(io.get("full").getAsString());
											}
											if (io.has("thumbs")) {
												JsonObject to = io.get("thumbs").getAsJsonObject();
												image.setSmallThumbnail(to.get("small").getAsString());
												image.setLargeThumbnail(to.get("large").getAsString());
											}
										}
										product.setImage(image);
									}
									if (pobj.has("price")) {
										JsonElement priceElement = pobj.get("price");

										if ((!priceElement.isJsonNull()) && (priceElement.isJsonObject())) {
											JsonObject po = priceElement.getAsJsonObject();

											if ((po.has("currency")) && (po.has("value"))) {
												Price price = new Price(po.get("value").getAsDouble(), po.get("currency")
														.getAsString());
												if (po.has("retail")) {
													price.setRetail(po.get("retail").getAsDouble());
												}
												if (po.has("sale")) {
													price.setSale(po.get("sale").getAsDouble());
												}
												product.setPrice(price);
											}
										}
									}
									product.setNodeType(Product.NodeType.Product);
									mProducts.add(product);
									setChanged();
								}
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

	private class LoadCart extends AsyncTask<String, Boolean, Boolean> {

		OkHttpClient client = new OkHttpClient();

		@Override
        protected Boolean doInBackground(String... params) {
            boolean success = true;

            String url = String.format("%s%s",mContext.getString(R.string.base_url), mContext.getString(R.string.checkout_cart_url));

            try {
                HttpURLConnection connection = new OkHttpClient().open(new URL(url));
                InputStream in;

                // Read the response.
                in = connection.getInputStream();
                byte[] response = readFully(in);

                setChanged();
                mCartJsonString = new String(response, "UTF-8");
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                success = false;
                e.printStackTrace();
            }

            return success;

        }

		@Override
		protected void onPostExecute(Boolean success) {

			super.onPostExecute(success);
			notifyObservers();

		}
	}
}
