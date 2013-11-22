package me.imvoice.app;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class to save user information
 * @author Yudong Yang
 *
 */
public class UserInfo implements Parcelable{
	private int mData;
	private int uid;
	private String userName;
	private Drawable userAvatar;
	private int age;
	private String email;
	private String md5Password;
	
	public UserInfo(String userName, int uid, Drawable userAvatar, int age, String email, String md5Password){
		this.uid = uid;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.age = age;
		this.email = email;
		this.md5Password = md5Password;
	}
	
	public int getuid(){
		return uid;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public Drawable getUserAvatar(){
		return userAvatar;
	}
	
	public int getAge(){
		return age;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getmd5Password(){
		return md5Password;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		// TODO Auto-generated method stub
		out.writeInt(mData);
	}
	
    public static final Parcelable.Creator<UserInfo> CREATOR
    	= new Parcelable.Creator<UserInfo>() {
    	public UserInfo createFromParcel(Parcel in) {
    	return new UserInfo(in);
	}

	public UserInfo[] newArray(int size) {
		return new UserInfo[size];
	}
    };

	private UserInfo(Parcel in) {
		mData = in.readInt();
	}
}
