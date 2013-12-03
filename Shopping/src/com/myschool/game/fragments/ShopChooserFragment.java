package com.myschool.game.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.myschool.game.R;

public class ShopChooserFragment extends DialogFragment {
	public interface CharTypeChooserListener {
		void onFinishCharTypeChooser(int which);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.character_type_chooser_title).setItems(
				R.array.character_type_chooser_array,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						// The 'which' argument contains the index position
						// of the selected item
						// Log.d("Alain", chooser[which]);
						CharTypeChooserListener activity = (CharTypeChooserListener) getActivity();
						// Toast.makeText(getActivity(),
						// "Le nom du personnage est a renseigner",
						// Toast.LENGTH_SHORT).show();
						// getActivity().finish();
						activity.onFinishCharTypeChooser(which);
						getDialog().dismiss();

					}
				});
		return builder.create();
	}
}
