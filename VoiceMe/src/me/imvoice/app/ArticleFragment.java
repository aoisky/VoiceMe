package me.imvoice.app;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ArticleFragment extends ListFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
	    return view;  
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

		String sideBarList[] = {"Article1","Article2","Article3","Article4","More Articles..."};
		ArrayAdapter<String> articleAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, sideBarList);
		setListAdapter(articleAdapter);
	    

	}
}