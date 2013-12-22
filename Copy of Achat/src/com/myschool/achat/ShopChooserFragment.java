package com.myschool.achat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.myschool.game.database.helper.DatabaseHelper;

public class ShopChooserFragment extends DialogFragment {
	
	public interface ShopChooserListener {
		void onFinishShopChooser(int which);
	}

	private DatabaseHelper mDatabaseHelper;
	private Cursor mCursor;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		/*
		 * You can use getActivity(), which returns the activity associated with
		 * a fragment. The activity is a context (since Activity extends
		 * Context).
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
		
		
		// Set dialog Title
		builder.setTitle(R.string.character_type_chooser_title);
		mDatabaseHelper  = new DatabaseHelper(getActivity());
		mCursor = mDatabaseHelper.getShops();
		// Set dialog item
		builder.setCursor(
				mCursor,
				new DialogInterface.OnClickListener() {  //listener 
					public void onClick(DialogInterface dialog, int which) {

						mCursor.moveToPosition(which);
						int columnIndex = mCursor.getColumnIndex(DatabaseHelper.KEY_ID);
						int shopId  = mCursor.getInt(columnIndex);
						// Get call calling activity (which implement interface
						// method CharTypeChooserListener
						ShopChooserListener activity = (ShopChooserListener) getActivity();
						
						// Call interface method of calling onFinsih
						activity.onFinishShopChooser(shopId);

					}
				}, DatabaseHelper.KEY_SHOP_NAME);
		return builder.create();
	}

}
