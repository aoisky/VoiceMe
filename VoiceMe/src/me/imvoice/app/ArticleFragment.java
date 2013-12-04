package me.imvoice.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Show a list of articles
 * @author Yudong Yang
 *
 */
public class ArticleFragment extends ListFragment implements OnItemClickListener{

	private ListView listView;
	private List<ArticleItem> articleItems;
	private myArticleAdapter articleAdapter;
	private myBundleArticleAdapter bundleArticleAdapter;
	private boolean loginStatus = false;
	private boolean showHeadlines = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.sidebar_fragment,
                container, false);
		
        listView = (ListView) view.findViewById(android.R.id.list);
        articleItems = new ArrayList<ArticleItem>();
		
	    return view;  
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    if(getArguments() != null){
	    	showHeadlines = getArguments().getBoolean("isHeadline", true);
	    }
	    
	    if(showHeadlines){
	    	//TEMPLATE Article items for headlines
		    ArticleItem article1 = new ArticleItem(new ColorDrawable(Color.parseColor("#00A9FF")), "Jesica E. Hollinger","Conte Distinguished Lecture Series Begins -- New Mining Techniques Explored and Excavated", "10-25-2013","Graph Optimization Problems in Data Mining will be the first presentation of the 2013-2014 Sam Conte Distinguished Lecture Series presented by Paul M. Van Dooren, professor of mathematical engineering at Catholic University in Louvain at 3:30 p.m. Monday, Oct. 28 in room 1142 of the Lawson Computer Science building.");
		    ArticleItem article2 = new ArticleItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Elizabeth K. Gardner", "Computer Science professors from Purdue lead the organization of the 2013 Splash Conference in Indianapolis","10-23-2013","This year's conference, which focuses on solving problems faced by the software industry, includes ways to improve tools the average person uses daily like smartphones");
		    ArticleItem article3 = new ArticleItem(new ColorDrawable(Color.parseColor("#00A9FF")), "Jesica E. Hollinger","IEEE Computer Society Board Names Elisa Bertino Editor-in-Chief of Top International Journal","10-16-2013", "Elisa Bertino, a professor of computer science at Purdue University and acting research director for the Center for Education and Research in Information Assurance and Security, was named editor-in-chief of IEEE Transactions on Secure and Dependable ");
		    ArticleItem article4 = new ArticleItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Fadi Meawad","Hosking Receives Research Awards from Qualcomm, Inc.","10-15-2013", "Elisa Bertino, a professor of computer science at Purdue University and acting research director for the Center for Education and Research in Information Assurance and Security, was named editor-in-chief of IEEE Transactions on Secure and Dependable ");
		    ArticleItem article5 = new ArticleItem(new ColorDrawable(Color.parseColor("#00A9FF")),"Buster Dunsmore","CS Student Project Receives Entrepreneurship Prototyping Grant","10-13-2013", "Elisa Bertino, a professor of computer science at Purdue University and acting research director for the Center for Education and Research in Information Assurance and Security, was named editor-in-chief of IEEE Transactions on Secure and Dependable ");
		    articleItems.add(article1);
		    articleItems.add(article2);
		    articleItems.add(article3);
		    articleItems.add(article4);
		    articleItems.add(article5);

	    
        articleAdapter = new myArticleAdapter(getActivity(), android.R.id.list, articleItems);
        listView.setAdapter(articleAdapter);
        listView.setOnItemClickListener(this);

	    }else{
	    	
	    	SQLHandler sql = new SQLHandler(getActivity());
	    	List<Bundle> allArticles = sql.getAllArticlesByUserId(1);
	    	sql.close();
	        bundleArticleAdapter = new myBundleArticleAdapter(getActivity(), android.R.id.list, allArticles);
	        listView.setAdapter(bundleArticleAdapter);
	        bundleArticleAdapter.notifyDataSetChanged();
	        listView.setOnItemClickListener(this);
	    }
	}
	
	public class ArticleItem{
		private Drawable userAvatar;
		private CharSequence userName;
		private CharSequence articleTitle;
		private CharSequence textSummary;
		private CharSequence articleTime;
		private int articleId;
	
		public ArticleItem(Drawable userIcon, CharSequence userName, CharSequence articleTitle, CharSequence articleTime, CharSequence articleSummary){
			this.userAvatar = userIcon;
			this.userName = userName;
			this.articleTitle = articleTitle;
			this.articleTime = articleTime;
			this.textSummary = articleSummary;
		}
		
		public Drawable getAvatar(){
			return userAvatar;
		}
		
		public CharSequence getTitle(){
			return articleTitle;
		}
		
		public CharSequence getSummary(){
			return textSummary;
		}
		
		public CharSequence getTime(){
			return articleTime;
		}
		
		public CharSequence getUserName(){
			return userName;
		}
		
		public int getArticleId(){
			return articleId;
		}
	}
	

	public class myBundleArticleAdapter extends ArrayAdapter<Bundle>{
		
	    private class ViewHolder {
	        ImageView userAvatar;
	        TextView userName;
	        TextView articleTitle;
	        TextView articleTime;
	        TextView articleSummary;
	    }
		
		ViewHolder holder = null;
		Context context;
		public myBundleArticleAdapter(Context context, int resource, List<Bundle> articles) {
			super(context, resource, articles);
			this.context = context;
			// TODO Auto-generated constructor stub
		}
		
		
		
	    public View getView(int position, View view, ViewGroup parent) {
			
			Bundle article = getItem(position);
			
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
			
			UserInfo userInfo = APIHandler.readUserInfo(getActivity());
			
			holder.userName.setText(userInfo.getUserName());
			holder.articleTitle.setText(article.getString(SQLHandler.ARTICLE_TITLE));
			holder.userAvatar.setImageBitmap(userInfo.getUserAvatar());
			holder.articleTime.setText(article.getString(SQLHandler.ARTICLE_TIMESTAMP));
			holder.articleSummary.setText(article.getString(SQLHandler.ARTICLE_CONTENT));
	    	return view;
	        
	    }
	}
	
	
	public class myArticleAdapter extends ArrayAdapter<ArticleItem>{
		
	    private class ViewHolder {
	        ImageView userAvatar;
	        TextView userName;
	        TextView articleTitle;
	        TextView articleTime;
	        TextView articleSummary;
	    }
		
		ViewHolder holder = null;
		Context context;
		public myArticleAdapter(Context context, int resource, List<ArticleItem> articles) {
			super(context, resource, articles);
			this.context = context;
			// TODO Auto-generated constructor stub
		}
		
		
		
	    public View getView(int position, View view, ViewGroup parent) {
			
			ArticleItem article = getItem(position);
			
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
			holder.articleTitle.setText(article.getTitle());
			holder.userAvatar.setImageDrawable(article.getAvatar());
			holder.articleTime.setText(article.getTime());
			holder.articleSummary.setText(article.getSummary());
	    	return view;
	        
	    }
		
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int item, long longInt) {
		// TODO Auto-generated method stub

		Intent articleIntent = new Intent(getActivity(), ArticleActivity.class);
		TextView articleTitle = (TextView) view.findViewById(R.id.article_title);
		TextView articleContent = (TextView) view.findViewById(R.id.article_summary);
		articleIntent.putExtra(ArticleActivity.ARTICLE_TITLE, articleTitle.getText().toString());
		articleIntent.putExtra(ArticleActivity.ARTICLE_CONTENT, articleContent.getText().toString());
		
		startActivity(articleIntent);
	}
}


