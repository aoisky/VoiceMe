package me.imvoice.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SignupBasicFragment extends Fragment{

	private String email;
	private String password;
	private String nickname;
	
	private EditText emailAddress;
	private EditText nickName;
	private EditText passwordEdit;
	private EditText passwordConfirm;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_signup_basic,
				container, false);

		if(getArguments() != null){
			email = getArguments().getString("email");
			password = getArguments().getString("password");
		}
		emailAddress = (EditText) rootView.findViewById(R.id.signup_email);
		nickName = (EditText) rootView.findViewById(R.id.signup_nickname);
		passwordEdit = (EditText) rootView.findViewById(R.id.signup_password);
		passwordConfirm = (EditText) rootView.findViewById(R.id.signup_confirmPassword);
		
		if(email != null){
			emailAddress.setText(email);
		}
		
		if(password != null){
			passwordEdit.setText(password);
		}
		
		return rootView;
	}
}
