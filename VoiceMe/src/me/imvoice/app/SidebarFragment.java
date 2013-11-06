package me.imvoice.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Create a sidebar fragment
 * @author Yudong Yang
 *
 */
public class SidebarFragment extends ListFragment {


	private ListView listView;
	private List<MenuItem> menuItems;
	private mySidebarAdapter sidebarAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		
		View view = inflater.inflate(R.layout.sidebar_fragment,
                container, false);
		
        listView = (ListView) view.findViewById(android.R.id.list);
        menuItems = new ArrayList<MenuItem>();
        
		int width = display.getWidth();
		view.setAlpha(0.95f);
		view.getLayoutParams().width = (int)(width * 0.7);
		view.setBackgroundColor(Color.parseColor("#E5E9EF"));
	    return view;  
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    MenuItem homeMenu = new MenuItem(new ColorDrawable(Color.parseColor("#00A9FF")), "Home");
	    MenuItem headlineMenu = new MenuItem(new ColorDrawable(Color.parseColor("#00A9FF")), "User");
	    MenuItem newArticleMenu = new MenuItem(new ColorDrawable(Color.parseColor("#00A9FF")), "New Article");
	    MenuItem favoriteMenu = new MenuItem(new ColorDrawable(Color.parseColor("#00A9FF")), "Favorite");
	    MenuItem otherMenu = new MenuItem(new ColorDrawable(Color.parseColor("#00A9FF")), "Others");
	    menuItems.add(homeMenu);
	    menuItems.add(headlineMenu);
	    menuItems.add(favoriteMenu);
	    menuItems.add(newArticleMenu);
	    menuItems.add(otherMenu);
	    
        sidebarAdapter = new mySidebarAdapter(getActivity(), android.R.id.list, menuItems);
        listView.setAdapter(sidebarAdapter);
	}
	
	public class MenuItem{
		private Drawable menuDrawable;
		private CharSequence menuName;
		
		public MenuItem(Drawable drawable, CharSequence menuName){
			this.menuDrawable = drawable;
			this.menuName = menuName;
		}
		
		public Drawable getIcon(){
			return menuDrawable;
		}
		
		public CharSequence getMenuName(){
			return menuName;
		}
	}
	
	public class mySidebarAdapter extends ArrayAdapter<MenuItem>{

		Context context;
		public mySidebarAdapter(Context context, int resource, List<MenuItem> menu) {
			super(context, resource, menu);
			this.context = context;
			// TODO Auto-generated constructor stub
		}
		
	    private class ViewHolder {
	        ImageView imageView;
	        TextView txtMenuName;
	    }
		
	    public View getView(int position, View view, ViewGroup parent) {
	    	ViewHolder holder = null;
			MenuItem rowItem = getItem(position);
			
	        LayoutInflater mInflater = (LayoutInflater) context
	                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			if(view == null){
				view = mInflater.inflate(R.layout.main_sidebar, null);
				 holder = new ViewHolder();
		            holder.txtMenuName = (TextView) view.findViewById(R.id.menu_name);
		            holder.imageView = (ImageView) view.findViewById(R.id.menu_image);
		            view.setTag(holder);
		        } else
		            holder = (ViewHolder)view.getTag();
			
			
			holder.txtMenuName.setText(rowItem.getMenuName());
			holder.imageView.setImageDrawable(rowItem.getIcon());
	    	return view;
	    }
		
	}
	
}
