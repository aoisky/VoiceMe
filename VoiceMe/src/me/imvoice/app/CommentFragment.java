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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Show a list of comments of an article.
 * @author deenliu
 *
 */
public class CommentFragment extends ListFragment{

	private ListView listView;
	private List<CommentItem> commentItems;
	private CommentAdapter commentAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.list_text_field,
                container, false);
		
        listView = (ListView) view.findViewById(android.R.id.list);
        commentItems = new ArrayList<CommentItem>();
		
	    return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    CommentItem comment1 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")), "Jesica E. Hollinger", "10-25-2013","Nice.");
	    CommentItem comment2 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Elizabeth K. Gardner", "10-13-2013", "I don't like it.");
	    CommentItem comment3 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")), "Jesica E. Hollinger", "10-16-2013", "LOL");
	    CommentItem comment4 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Fadi Meawad", "10-15-2013", "Great!");
	    CommentItem comment5 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Buster Dunsmore", "10-13-2013", "This is really cool!");
	    CommentItem comment6 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Fadi Meawad", "10-15-2013", "Great!");
	    CommentItem comment7 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Fadi Meawad", "10-15-2013", "Great!");
	    CommentItem comment8 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Fadi Meawad", "10-15-2013", "Great!");
	    CommentItem comment9 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Fadi Meawad", "10-15-2013", "Great!");
	    CommentItem comment10 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Fadi Meawad", "10-15-2013", "Great!");
	    commentItems.add(comment1);
	    commentItems.add(comment2);
	    commentItems.add(comment3);
	    commentItems.add(comment4);
	    commentItems.add(comment5);
	    commentItems.add(comment6);
	    commentItems.add(comment7);
	    commentItems.add(comment8);
	    commentItems.add(comment9);
	    commentItems.add(comment10);
	    
	    commentAdapter = new CommentAdapter(getActivity(), android.R.id.list, commentItems);
        listView.setAdapter(commentAdapter);
	}
	
	public class CommentItem{
		private Drawable userAvatar;
		private CharSequence userName;
		private CharSequence comment;
		private CharSequence commentTime;
	
		public CommentItem(Drawable userIcon, CharSequence userName, CharSequence commentTime, CharSequence comment){
			this.userAvatar = userIcon;
			this.userName = userName;
			this.commentTime = commentTime;
			this.comment = comment;
		}
		
		public Drawable getAvatar(){
			return userAvatar;
		}
		
		public CharSequence getComment(){
			return comment;
		}
		
		public CharSequence getTime(){
			return commentTime;
		}
		
		public CharSequence getUserName(){
			return userName;
		}
	}
	

	
	public class CommentAdapter extends ArrayAdapter<CommentItem>{
		
	    private class ViewHolder {
	        ImageView userAvatar;
	        TextView userName;
	        TextView commentTime;
	        TextView comment;
	    }
		
		ViewHolder holder = null;
		Context context;
		public CommentAdapter(Context context, int resource, List<CommentItem> comments) {
			super(context, resource, comments);
			this.context = context;
			// TODO Auto-generated constructor stub
		}
		
	    public View getView(int position, View view, ViewGroup parent) {
			
	    	CommentItem comment = getItem(position);
			
	        LayoutInflater mInflater = (LayoutInflater) context
	                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			if(view == null){
				view = mInflater.inflate(R.layout.comment_row, null);
				holder = new ViewHolder();
				holder.userAvatar = (ImageView) view.findViewById(R.id.avatar_image);
				holder.commentTime = (TextView) view.findViewById(R.id.comment_time);
				holder.comment = (TextView) view.findViewById(R.id.comment);
				holder.userName = (TextView) view.findViewById(R.id.commentuser_name);
				view.setTag(holder);
		    } else
		    	holder = (ViewHolder)view.getTag();
			
			holder.userName.setText(comment.getUserName());
			holder.userAvatar.setImageDrawable(comment.getAvatar());
			holder.commentTime.setText(comment.getTime());
			holder.comment.setText(comment.getComment());
	    	return view;
	        
	    }
		
	}
}


