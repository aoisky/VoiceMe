package me.imvoice.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.imvoice.app.ArticleFragment.myArticleAdapter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ArticleContentFragment extends Fragment {
	
	private int pageNum;
	private ViewPager pagerView;
	private ArrayList<Article> articles = new ArrayList<Article>();
	LayoutInflater inflater;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.article_content_fragment, container, false);
		
//		this.inflater = inflater;
//		pagerView = (ViewPager)view.findViewById(R.id.article);
		setButtonColor(view);
	    return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    ArticleTitle title = new ArticleTitle("This is an Article Title", new ColorDrawable(Color.parseColor("#00A9FF")), "I am the author");
	    ArticleContent content = new ArticleContent(new ColorDrawable(Color.parseColor("#0000FF")), "balabala.");
	    Article article = new Article(title, content);
	    
	    articles.add(article);
	    
//	    MyPagerAdapter articleAdapter = new MyPagerAdapter();
//	    pagerView.setAdapter(articleAdapter);
	}
	
	private void setButtonColor(View view){
		Button like_btn = (Button)(view.findViewById(R.id.like_btn));
		Button comment_btn = (Button)(view.findViewById(R.id.comment_btn));
		Button share_btn = (Button)(view.findViewById(R.id.share_btn));
		like_btn.setBackgroundResource(R.drawable.aritcle_btns);
		comment_btn.setBackgroundResource(R.drawable.aritcle_btns);
		share_btn.setBackgroundResource(R.drawable.aritcle_btns);
	}
	
//	public class MyPagerAdapter extends PagerAdapter {
//
//		@Override
//		public Object instantiateItem(View collection, int position) {
//		    Article article = articles.get(position);
//
//		    View view = inflater.inflate(R.layout.article_content_fragment, null);
//
//		    setView(view, article);
//
//		    ((ViewPager) collection).addView(view);
//
//		    return view;
//		}
//		
//		@Override
//		public void destroyItem(View collection, int position, Object view) {
//		     ((ViewPager) collection).removeView((View) view);
//		}
//		
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//		
//		private void setView(View view, Article article) {
//			String title = article.getTitle().getTitle();
//			Drawable avatar = article.getTitle().getAvatar();
//			String author = article.getTitle().getAuthor();
//			Drawable pic = article.getContent().getPic();
//			String content = article.getContent().getContent();
//			
//			TextView titleView = (TextView)view.findViewById(R.id.article_title);
//			ImageView avatarView = (ImageView)view.findViewById(R.id.article_avatar);
//			TextView contentView = (TextView)view.findViewById(R.id.article_content);
//			ImageView picView = (ImageView)view.findViewById(R.id.article_pic);
//			
//			titleView.setText(title);
//			avatarView.setImageDrawable(avatar);
//			contentView.setText(content);
//			picView.setImageDrawable(pic);
//		}
//		
//	}
//	
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
		private Drawable pic;
		private String content;
		
		public ArticleContent(Drawable pic, String content) {
			this.pic = pic;
			this.content = content;
		}
		
		public Drawable getPic() {
			return pic;
		}
		
		public String getContent() {
			return content;
		}
	}
	

}
