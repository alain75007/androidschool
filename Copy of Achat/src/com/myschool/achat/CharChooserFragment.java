package com.myschool.achat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CharChooserFragment extends DialogFragment {
	
	public interface CharTypeChooserListener {
		void onFinishCharTypeChooser(int which);
	}
	
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
		
		// Set dialog item
		builder.setItems(
				R.array.character_type_array,
				new DialogInterface.OnClickListener() {  //listener 
					public void onClick(DialogInterface dialog, int which) {

						// Get call calling activity (which implement interface
						// method CharTypeChooserListener
						CharTypeChooserListener activity = (CharTypeChooserListener) getActivity();

						// Call interface method of calling onFinsih
						activity.onFinishCharTypeChooser(which);

						// close dialog
						getDialog().dismiss();


					}
				});
		return builder.create();
	}

}
