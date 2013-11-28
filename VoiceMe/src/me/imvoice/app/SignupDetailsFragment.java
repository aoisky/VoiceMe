package me.imvoice.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SignupDetailsFragment extends Fragment implements OnClickListener{
	
	private Button registerBtn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_signup_details,
				container, false);
		registerBtn = (Button)rootView.findViewById(R.id.signup_button_register);
		registerBtn.setOnClickListener(this);
		return rootView;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		getActivity().finish();
		
	}
}
