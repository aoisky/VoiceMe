package me.imvoice.app;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
		ViewPager viewPager =  (ViewPager)getActivity().findViewById(R.id.pager_signup);

		SignupBasicFragment basicFragment = (SignupBasicFragment)viewPager.getAdapter().instantiateItem(viewPager, 0);
	    SignupUploadFragment uploadFragment = (SignupUploadFragment)viewPager.getAdapter().instantiateItem(viewPager, 1);
		
		if(basicFragment != null){
			String email = basicFragment.getEmail();
			String password = basicFragment.getPassword();
			String confirmPassword = basicFragment.getConfirmPassword();
			
			if(email != null && !email.contains("@")){
				viewPager.setCurrentItem(0);
				EditText emailEdit = (EditText)basicFragment.getView().findViewById(R.id.signup_email);
				emailEdit.setError("Invalid Email Address");
				emailEdit.requestFocus();
				return;
			}
			
			if(password != null && password.length() < 6){
				viewPager.setCurrentItem(0);
				EditText passwordEdit = (EditText)basicFragment.getView().findViewById(R.id.signup_password);
				passwordEdit.setError("Password too short");
				passwordEdit.requestFocus();
				return;
			}
			
			if(password != null && confirmPassword != null && !password.equals(confirmPassword)){
				viewPager.setCurrentItem(0);
				EditText passwordEdit = (EditText)basicFragment.getView().findViewById(R.id.signup_confirmPassword);
				passwordEdit.setError("Password not match");
				passwordEdit.requestFocus();
				return;
			}
		}
		
		if(!uploadFragment.isIconSet()){
			viewPager.setCurrentItem(1);
			Toast.makeText(getActivity(), "You need to choose an user icon", Toast.LENGTH_SHORT).show();
			return;
		}
		
		ImageView userIcon = (ImageView) uploadFragment.getView().findViewById(R.id.signup_userIcon);
		
		Bitmap userIconBitmap = ((BitmapDrawable)userIcon.getDrawable()).getBitmap();


		//getActivity().finish();
		
	}
}
