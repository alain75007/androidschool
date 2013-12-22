package com.myschool.game.shop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.myschool.achat.R;
import com.myschool.achat.R.string;
import com.myschool.game.database.helper.DatabaseHelper;

public class ProductChooserFragment extends DialogFragment {
	
	public interface ProductChooserListener {
		void onFinishProductChooser(int which);
	}

	private DatabaseHelper mDatabaseHelper;
	private Cursor mCursor;
	private Bundle mBundle;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		/*
		 * You can use getActivity(), which returns the activity associated with
		 * a fragment. The activity is a context (since Activity extends
		 * Context).
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
		
		
		// Set dialog Title
		builder.setTitle(R.string.act_shop_product_chooser_title);
		mDatabaseHelper  = new DatabaseHelper(getActivity());
		mBundle = getArguments();
		int shopId = mBundle.getInt("shop_id");
		mCursor = mDatabaseHelper.getProducts(shopId);
		Log.d("Alain", "Nombre de produits a afficher " + mCursor.getCount());
		// Set dialog item
		builder.setCursor(
				mCursor,
				new DialogInterface.OnClickListener() {  //listener 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mCursor.moveToPosition(which);
						int columnIndex = mCursor.getColumnIndex(DatabaseHelper.KEY_ID);
						int productId  = mCursor.getInt(columnIndex);
						// Get call calling activity (which implement interface
						// method CharTypeChooserListener
						ProductChooserListener activity = (ProductChooserListener) getActivity();
						
						// Call interface method of calling onFinsih
						activity.onFinishProductChooser(productId);

					}
				}, DatabaseHelper.KEY_SHOP_NAME);
		return builder.create();
	}

}
