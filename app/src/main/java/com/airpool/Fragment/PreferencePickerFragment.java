package com.airpool.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.airpool.Model.TransportationPreference;
import com.airpool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maury on 11/14/14.
 */
public class PreferencePickerFragment extends DialogFragment {
    OnPreferencePickedListener callback;
    List<TransportationPreference> preferences;
    List<String> preferencesString;
    boolean[] currentPreferences;
    boolean[] savedPreferences;

    public int noPreferenceWhich = 0;

    public interface OnPreferencePickedListener {
        public void onPreferencePicked(ArrayList<TransportationPreference> preference);
    }

    public PreferencePickerFragment() {
        preferences = new ArrayList<TransportationPreference>();
        preferencesString = new ArrayList<String>();
        currentPreferences = new boolean[TransportationPreference.values().length];
        savedPreferences = new boolean[TransportationPreference.values().length];

        int i = 0;
        for (TransportationPreference preference : TransportationPreference.values()) {
            if (preference == TransportationPreference.NOPREF) {
                noPreferenceWhich = i;
                currentPreferences[i] = true;
                savedPreferences[i] = true;
            } else {
                currentPreferences[i] = false;
                savedPreferences[i] = false;
            }
            preferences.add(preference);
            preferencesString.add(preference.toString());
            i++;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (OnPreferencePickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPreferencePickedListener");
        }
    }

    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Build a new dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Select the Title
        builder = builder.setTitle(R.string.select_transportation_preferences);

        int i = 0;
        for (TransportationPreference preference : TransportationPreference.values()) {
            currentPreferences[i] = savedPreferences[i++];
        }

        builder = builder.setMultiChoiceItems(preferencesString.toArray(new CharSequence[preferencesString.size()]),
                currentPreferences,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (which == noPreferenceWhich && isChecked) {
                            int i = 0;
                            while (i < currentPreferences.length) {
                                if (i != noPreferenceWhich) {
                                    currentPreferences[i] = false;
                                    ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                                } else {
                                    currentPreferences[noPreferenceWhich] = true;
                                    ((AlertDialog) dialog).getListView().setItemChecked(noPreferenceWhich, true);
                                }

                                i++;
                            }
                        } else {
                            currentPreferences[noPreferenceWhich] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(noPreferenceWhich, false);
                        }
                    }
                });

        builder = builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.arraycopy(currentPreferences, 0, savedPreferences, 0, currentPreferences.length);
                ArrayList<TransportationPreference> passedPreferences = new ArrayList<TransportationPreference>();
                int j = 0;
                while (j < savedPreferences.length) {
                    if (savedPreferences[j]) {
                        passedPreferences.add(preferences.get(j));
                    }
                    j++;
                }
                callback.onPreferencePicked(passedPreferences);
            }
        });

        builder = builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.arraycopy(savedPreferences, 0, currentPreferences, 0, currentPreferences.length);
            }
        });

        return builder.create();
    }
}
