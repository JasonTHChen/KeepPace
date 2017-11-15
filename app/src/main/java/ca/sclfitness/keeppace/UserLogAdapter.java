package ca.sclfitness.keeppace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.sclfitness.keeppace.model.Race;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 13, 2017
 */

public class UserLogAdapter extends ArrayAdapter<Race> {

    public UserLogAdapter(Context context, List<Race> races) {
        super(context, 0, races);
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
            paceView.setText(String.valueOf(race.getAveragePace()) + " Km/Hr");
            if (race.getBestTime() == 0) {
                bestTimeView.setText("--:--.--");
            } else {
                bestTimeView.setText(race.bestTimeText());
            }
        }

        return convertView;
    }
}
