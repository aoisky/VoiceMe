package me.imvoice.app;

import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

public class ArticleContentFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.article_content_fragment, container, false);
	    return view;  
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    ArticleTitle title = new ArticleTitle("This is an Article Title", new ColorDrawable(Color.parseColor("#00A9FF")), "I am the author");
	    ArticleContent content = new ArticleContent(new ColorDrawable(Color.parseColor("#0000FF")), "balabala.");
	    Article article = new Article(title, content);
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
