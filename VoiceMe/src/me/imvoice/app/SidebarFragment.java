package me.imvoice.app;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SidebarFragment extends ListFragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		
		View view = super.onCreateView(inflater, container, savedInstanceState);
		int width = display.getWidth();
		view.setAlpha(0.3f);
		view.getLayoutParams().width = (int)(width * 0.7);
		
	    return view;  
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

		String sideBarList[] = {"Main","Headlines","Events","Search","New Article"};
		ArrayAdapter<String> sidebarAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,sideBarList);
		setListAdapter(sidebarAdapter);
	    


	}
	
}
