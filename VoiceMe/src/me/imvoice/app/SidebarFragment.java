package me.imvoice.app;

import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Create a sidebar fragment
 * @author Yudong Yang
 *
 */
public class SidebarFragment extends ListFragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		
		View view = super.onCreateView(inflater, container, savedInstanceState);
		int width = display.getWidth();
		view.setAlpha(0.95f);
		view.getLayoutParams().width = (int)(width * 0.7);
		view.setBackgroundColor(Color.parseColor("#E5E9EF"));
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
