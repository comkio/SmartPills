package upf.edu.smartpills;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class InboxFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_about, container, false);

        ((CalendarActivity) getActivity()).getSupportActionBar().setTitle("Fragment Inbox");

        Button buttonChangeText = (Button) view.findViewById(R.id.buttonFragmentInbox);

        final TextView textViewInboxFragment = (TextView) view.findViewById(R.id.textViewInboxFragment);

        buttonChangeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewInboxFragment.setText("This is the Inbox Fragment");
                textViewInboxFragment.setTextColor(getResources().getColor(R.color.colorBlack));

            }
        });

        return view;
    }

}
