package me.imvoice.app;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
/**
 * Show a list of articles
 * @author Yudong Yang
 *
 */
public class ArticleFragment extends ListFragment{

	String[][] contents;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//String[][] contents = (String[][])getArguments().get("contents");
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
	    return view;  
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

		String sideBarList[] = {"Purdue University development, investment offices ","Article2","Article3","Article4","Drug may reduce chronic pain for spinal cord injuries","The move, which becomes effective Jan. 1, puts Purdue in alignment with its peer institutions. Currently, nine schools out of 14 in the Big Ten Conference have separate fundraising foundations. In Indiana, the other large public schools"};
		ArrayAdapter<String> articleAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, sideBarList);
		setListAdapter(articleAdapter);
	    
	}
	
	public class ArticleItem{
		
	}
	
	public class myArticleAdapter extends ArrayAdapter<String[]>{

		Context context;
		public myArticleAdapter(Context context, int resource, List<String[]> strings) {
			super(context, resource, strings);
			this.context = context;
			// TODO Auto-generated constructor stub
		}
		
	    public View getView(int position, View convertView, ViewGroup parent) {
			
			String[] contents = getItem(position);
	        LayoutInflater mInflater = (LayoutInflater) context
	                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
	    	return parent;
	    }
		
	}
}


