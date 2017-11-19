package ca.sclfitness.keeppace;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ca.sclfitness.keeppace.model.Race;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 13, 2017
 */

public class UserLogAdapter extends ArrayAdapter<Race> {

    private Context mContext;

    public UserLogAdapter(Context context, List<Race> races) {
        super(context, 0, races);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Race race = this.getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.race_list_row, parent, false);
        }

        final TextView nameView = (TextView) convertView.findViewById(R.id.textView_raceList_name);
        final TextView paceView = (TextView) convertView.findViewById(R.id.textView_raceList_pace);
        final TextView bestTimeView = (TextView) convertView.findViewById(R.id.textView_raceList_bestTime);

        if (race != null) {
            nameView.setText(race.getName());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            String unit = sharedPreferences.getString(mContext.getString(R.string.key_unit), "1");
            if (unit.equals("2")) {
                String pace = String.format(Locale.getDefault(), "%.2f " + mContext.getString(R.string.pace_mile_per_hr), race.convertToMile(race.getAveragePace()));
                paceView.setText(pace);
            } else {
                String pace = String.format(Locale.getDefault(), "%.2f " + mContext.getString(R.string.pace_km_per_hr), race.getAveragePace());
                paceView.setText(pace);
            }

            if (race.getBestTime() == 0) {
                bestTimeView.setText("--:--.--");
            } else {
                bestTimeView.setText(race.bestTimeText());
            }
        }

        return convertView;
    }
}
