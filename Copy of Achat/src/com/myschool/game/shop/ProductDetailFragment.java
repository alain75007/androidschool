package com.myschool.game.shop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myschool.achat.R;
import com.myschool.achat.dummy.DummyContent;
import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.model.Product;

/**
 * A fragment representing a single Product detail screen. This fragment is
 * either contained in a {@link ProductListActivity} in two-pane mode (on
 * tablets) or a {@link ProductDetailActivity} on handsets.
 */
public class ProductDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	private Bundle mBundle;

	private DatabaseHelper mDatabaseHelper;

	private Product mProduct;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ProductDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBundle = getArguments();
		if (getArguments().containsKey("product_id")) {
			mDatabaseHelper = new DatabaseHelper(getActivity());
			mProduct = mDatabaseHelper.getProduct(mBundle.getInt("product_id"));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_product_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mProduct != null) {
			((TextView) rootView.findViewById(R.id.product_detail))
					.setText(mProduct.getName());
		}

		return rootView;
	}
}
