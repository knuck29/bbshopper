package com.knuck29.bbshopper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.Views;

import com.knuck29.bbshopper.app.BaseActionBarActivity;
import com.knuck29.bbshopper.catalog.Catalog;
import com.knuck29.bbshopper.catalog.Product;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends BaseActionBarActivity {
	@InjectView(android.R.id.text1) TextView title;
	@InjectView(R.id.priceValue) TextView priceValue;
	@InjectView(R.id.priceSale) TextView priceSale;
	@InjectView(R.id.priceRetail) TextView priceRetail;
	@InjectView(R.id.imageFull) ImageView imageFull;
	@InjectView(R.id.add_to_cart) Button addToCart;

	@Inject Catalog catalog;
	Product product = null;
	private NumberFormat mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productdetail);
		Views.inject(this);
		initialize(getIntent().getBundleExtra("bundle"));
	}

	private void initialize(Bundle bundle) {

		product = (Product) bundle.getSerializable("product");

		initUI();
	}

	private void initUI() {
        Picasso.with(ProductDetailActivity.this).load(product.getFullImage()).into(imageFull);
        imageFull.setTag(product.getFullImage());
        imageFull.setScaleType(ImageView.ScaleType.CENTER_CROP);
		title.setText(product.getTitle());
		priceValue.setText(String.format("%s: %s %s", getString(R.string.price_value),
				mCurrencyFormat.format(product.getValuePrice()), product.getCurrency()));
        priceValue.setVisibility((product.getValuePrice() !=0) ? View.VISIBLE: View.GONE);

        priceSale.setText(String.format("%s: %s %s", getString(R.string.price_sale),
				mCurrencyFormat.format(product.getSalePrice()), product.getCurrency()));
        priceSale.setVisibility((product.getSalePrice() !=0) ? View.VISIBLE: View.GONE);

        priceRetail.setText(String.format("%s: %s %s", getString(R.string.price_retail),
				mCurrencyFormat.format(product.getRetailPrice()), product.getCurrency()));
        priceRetail.setVisibility((product.getRetailPrice() !=0) ? View.VISIBLE: View.GONE);

        addToCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addItemToCart(product.getId(), 1);
			}
		});
	}

	/**
	 * Backward-compatible version of {@link ActionBar#getThemedContext()} that
	 * simply returns the {@link android.app.Activity} if
	 * <code>getThemedContext</code> is unavailable.
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private Context getActionBarThemedContextCompat() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return getActionBar().getThemedContext();
		}
		else {
			return this;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void addItemToCart(String productId, int quantity) {
		if ((productId != null) && (quantity != 0)) {
			String formBody = String.format("id=%s&qty=%s", product.getId(), "1");

            AddToCart task = new AddToCart();
            task.execute(formBody);

		}
	}

	private class AddToCart extends AsyncTask<String, Boolean, Boolean> {

		OkHttpClient client = new OkHttpClient();

		@Override
		protected Boolean doInBackground(String... params) {
			boolean success = true;
			if ((params != null) && (params[0].length() > 0)) {
				String body = params[0];
				String url = String.format("%s%s", getString(R.string.base_url), getString(R.string.checkout_cart_url));
				OutputStream out = null;
				InputStream in = null;
				try {
					HttpURLConnection connection = client.open(new URL(url));

					// Write the request.
					connection.setRequestMethod("POST");
					out = connection.getOutputStream();
					out.write(body.getBytes());
					out.close();

					// Read the response.
					if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
						success = false;
						throw new IOException("Unexpected HTTP response: " + connection.getResponseCode() + " "
								+ connection.getResponseMessage());
					}
					in = connection.getInputStream();
                    String dummy = readFirstLine(in);
                    boolean imat = true;
				}
				catch (Exception e) {
					success = false;
					e.printStackTrace();
				}
				finally {
					// Clean up.
					if (out != null)
						try {
							out.close();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					if (in != null)
						try {
							in.close();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
				}

			}
			return success;
		}

		String readFirstLine(InputStream in) throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			return reader.readLine();
		}

		@Override
		protected void onPostExecute(Boolean success) {
			String msg = null;
			if (success) {
				msg = "Item Added";
			}
			else {
				msg = "Item NOT Added";
			}
			Toast.makeText(ProductDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
			super.onPostExecute(success);
		}
	}

}
