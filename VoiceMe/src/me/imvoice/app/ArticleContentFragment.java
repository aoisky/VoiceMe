package me.imvoice.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Used to display one article.
 * @author deenliu
 *
 */

public class ArticleContentFragment extends Fragment {
	
    ArticleTitle title;
    ArticleContent content;
    Article article;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.article_content_fragment, container, false);
		setButtonColor(view);
		
	    return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    Bundle bundle = this.getArguments();
	    
	    if(bundle.containsKey(ArticleActivity.ARTICLE_TITLE)){
	    	title = new ArticleTitle(bundle.getString(ArticleActivity.ARTICLE_TITLE),new ColorDrawable(Color.parseColor("#00A9FF")),"Author");
	    	if( bundle.containsKey(ArticleActivity.PIC_URL) ){
	    		content = new ArticleContent(bundle.getString(ArticleActivity.PIC_URL), bundle.getString(ArticleActivity.ARTICLE_CONTENT) );
	    	}else{
	    		content = new ArticleContent(null, bundle.getString(ArticleActivity.ARTICLE_CONTENT) );
	    	}
	    	article = new Article(title, content);
	    }else{
	    
		    title = new ArticleTitle("Purdue helps bring SplashCon to Indianapolis; provides unique opportunity to Indiana computer science students", new ColorDrawable(Color.parseColor("#00A9FF")), "I am the author");
		    content = new ArticleContent("https://www.cs.purdue.edu/images/slider/splash.jpg", ""
		    		+"WEST LAFAYETTE, Ind. - Computer scientists from Purdue University led the organization of the 2013 Splash Conference, and helped bring the top software development conference to Indianapolis.\n\n"
		    		+"\"This is the first time this important conference has been held in our area and is easily accessible to students in Indiana and the Midwest,\" said Hosking, an associate professor of computer science. “There are already more than 30 Purdue undergraduate students signed up to go for whom travel to past locations would have been prohibitively expensive. We really want to encourage students to take advantage of this opportunity to learn from giants in the field and to network with other students and industry representatives.\"\n\n"
		    		+"Purdue students can attend the conference for free on Tuesday (Oct. 29) and the university is providing bus transportation from the West Lafayette campus to and from the conference for any students within the area. Students interested in riding the bus should contact Pat Morgan at pam@cs.purdue.edu.");

	    }
	    
	    article = new Article(title, content);
	    setView(this.getView(), article);
	    
	}
	
	public class UpdatePicTask extends AsyncTask<String, Void, Void> {

	    private Bitmap bitmap = null;

	    protected Void doInBackground(String... urls) {
	    	URL url;
	    	HttpURLConnection connection;
			try {
				
				url = new URL(urls[0]);
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
		        connection.connect();
		        InputStream input = connection.getInputStream();
		        bitmap = BitmapFactory.decodeStream(input);
		        
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			return null;
	    }

	    protected void onPostExecute(Void in) {
	    	if(getView() == null) return;
	    	ImageView picView = (ImageView)getView().findViewById(R.id.article_pic);
	    	picView.setImageBitmap(bitmap);
	    }
	}
	
	private void setView(View view, Article article) {
		String title = article.getTitle().getTitle();
		Drawable avatar = article.getTitle().getAvatar();
		String author = article.getTitle().getAuthor();
		String picUrl = article.getContent().getPicUrl();
		String content = article.getContent().getContent();
		
		TextView titleView = (TextView)view.findViewById(R.id.article_title);
		ImageView avatarView = (ImageView)view.findViewById(R.id.article_avatar);
		TextView contentView = (TextView)view.findViewById(R.id.article_content);
		
		titleView.setText(title);
		avatarView.setImageDrawable(avatar);
		contentView.setText(content);
		
		if(picUrl != null){
			String[] urls = new String[1];
			urls[0] = picUrl;
			UpdatePicTask task = new UpdatePicTask();
			task.execute(urls);
		}
	}
	
	public void clickHandler(View target) {
		switch(target.getId()) {
		
			case R.id.like_btn:
				Toast.makeText(getActivity(), "Like it", Toast.LENGTH_SHORT).show();
	        	break;
	        	
			case R.id.comment_btn:
				Intent comment = new Intent(this.getActivity(), CommentActivity.class);
				startActivity(comment);
				break;
				
			case R.id.share_btn:
				Toast.makeText(getActivity(), "Share it", Toast.LENGTH_SHORT).show();
				break;
	        	
			default:
				break;
		
		}
	}
	
	public class Article{
		private ArticleTitle title;
		private ArticleContent content;
		
		public Article(ArticleTitle title, ArticleContent content) {
			this.title = title;
			this.content = content;
		}
		
		public ArticleTitle getTitle() {
			return title;
		}
		
		public ArticleContent getContent() {
			return content;
		}
	}
	
	public class ArticleTitle{
		private Drawable avatar;
		private String author;
		private String title;
		
		public ArticleTitle(String title, ColorDrawable avatar, String author){
			this.title = title;
			this.avatar = avatar;
			this.author = author;
		}
		
		public Drawable getAvatar(){
			return avatar;
		}
		
		public String getAuthor(){
			return author;
		}
		
		public String getTitle(){
			return title;
		}
	}
	
	public class ArticleContent{
		private String picUrl;
		private String content;
		
		public ArticleContent(String picUrl, String content) {
			this.picUrl = picUrl;
			this.content = content;
		}
		
		public String getPicUrl() {
			return picUrl;
		}
		
		public String getContent() {
			return content;
		}
	}
	
	private void setButtonColor(View view){
		Button like_btn = (Button)(view.findViewById(R.id.like_btn));
		Button comment_btn = (Button)(view.findViewById(R.id.comment_btn));
		Button share_btn = (Button)(view.findViewById(R.id.share_btn));
		like_btn.setBackgroundResource(R.drawable.aritcle_btns);
		comment_btn.setBackgroundResource(R.drawable.aritcle_btns);
		share_btn.setBackgroundResource(R.drawable.aritcle_btns);
	}
	

}
