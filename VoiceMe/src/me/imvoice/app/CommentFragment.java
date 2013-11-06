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

		View view = inflater.inflate(R.layout.sidebar_fragment,
                container, false);
		
        listView = (ListView) view.findViewById(android.R.id.list);
        commentItems = new ArrayList<CommentItem>();
		
	    return view;  
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    CommentItem comment1 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")), "Jesica E. Hollinger","Conte Distinguished Lecture Series Begins -- New Mining Techniques Explored and Excavated", "10-25-2013","Graph Optimization Problems in Data Mining will be the first presentation of the 2013-2014 Sam Conte Distinguished Lecture Series presented by Paul M. Van Dooren, professor of mathematical engineering at Catholic University in Louvain at 3:30 p.m. Monday, Oct. 28 in room 1142 of the Lawson Computer Science building.");
	    CommentItem comment2 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Elizabeth K. Gardner", "Computer Science professors from Purdue lead the organization of the 2013 Splash Conference in Indianapolis","10-23-2013","This year's conference, which focuses on solving problems faced by the software industry, includes ways to improve tools the average person uses daily like smartphones");
	    CommentItem comment3 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")), "Jesica E. Hollinger","IEEE Computer Society Board Names Elisa Bertino Editor-in-Chief of Top International Journal","10-16-2013", "Elisa Bertino, a professor of computer science at Purdue University and acting research director for the Center for Education and Research in Information Assurance and Security, was named editor-in-chief of IEEE Transactions on Secure and Dependable ");
	    CommentItem comment4 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Fadi Meawad","Hosking Receives Research Awards from Qualcomm, Inc.","10-15-2013", "Elisa Bertino, a professor of computer science at Purdue University and acting research director for the Center for Education and Research in Information Assurance and Security, was named editor-in-chief of IEEE Transactions on Secure and Dependable ");
	    CommentItem comment5 = new CommentItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Buster Dunsmore","CS Student Project Receives Entrepreneurship Prototyping Grant","10-13-2013", "Elisa Bertino, a professor of computer science at Purdue University and acting research director for the Center for Education and Research in Information Assurance and Security, was named editor-in-chief of IEEE Transactions on Secure and Dependable ");
	    commentItems.add(comment1);
	    commentItems.add(comment2);
	    commentItems.add(comment3);
	    commentItems.add(comment4);
	    commentItems.add(comment5);
	    
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
			this.commentTime = comment;
		}
		
		public Drawable getAvatar(){
			return userAvatar;
		}
		
		public CharSequence getSummary(){
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
	        TextView articleTitle;
	        TextView articleTime;
	        TextView articleSummary;
	    }
		
		ViewHolder holder = null;
		Context context;
		public CommentAdapter(Context context, int resource, List<CommentItem> articles) {
			super(context, resource, articles);
			this.context = context;
			// TODO Auto-generated constructor stub
		}
		
		
		
	    public View getView(int position, View view, ViewGroup parent) {
			
	    	CommentItem article = getItem(position);
			
	        LayoutInflater mInflater = (LayoutInflater) context
	                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			if(view == null){
				view = mInflater.inflate(R.layout.article_row, null);
				 holder = new ViewHolder();
		            holder.userAvatar = (ImageView) view.findViewById(R.id.avatar_image);
		            holder.articleTitle = (TextView) view.findViewById(R.id.article_title);
		            holder.articleTime = (TextView) view.findViewById(R.id.article_time);
		            holder.articleSummary = (TextView) view.findViewById(R.id.article_summary);
		            holder.userName = (TextView) view.findViewById(R.id.articleuser_name);
		            view.setTag(holder);
		        } else
		            holder = (ViewHolder)view.getTag();
			
			holder.userName.setText(article.getUserName());
			holder.userAvatar.setImageDrawable(article.getAvatar());
			holder.articleTime.setText(article.getTime());
			holder.articleSummary.setText(article.getSummary());
	    	return view;
	        
	    }
		
	}
}


