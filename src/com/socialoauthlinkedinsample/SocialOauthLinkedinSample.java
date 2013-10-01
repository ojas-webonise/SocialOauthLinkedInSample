package com.socialoauthlinkedinsample;


import java.util.List;

import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SocialOauthLinkedinSample extends Activity {

	private SocialAuthAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView textview = (TextView)findViewById(R.id.text);
		textview.setText("Welcome to SocialAuth Demo. We are sharing " + 
				"text SocialAuth Android by share button.");

		// Single button
		Button share = (Button)findViewById(R.id.sharebutton);
		share.setText("Share");
		share.setTextColor(Color.WHITE);
		//	    share.setBackgroundResource(R.drawable.button_gradient);


		LinearLayout bar = (LinearLayout)findViewById(R.id.linearbar);
		//	    bar.setBackgroundResource(R.drawable.bar_gradient);

		//		ListView listview = (ListView)findViewById(R.id.listView);  
		//
		//		listview.setAdapter(new CustomAdapter(this, adapter));

		adapter = new SocialAuthAdapter(new ResponseListener());
		//		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);  
		//		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		adapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);
		//		adapter.addProvider(Provider.MYSPACE, R.drawable.myspace);
		adapter.enable(share);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	private final class ResponseListener implements DialogListener {

		@Override
		public void onCancel() {
		}

		@Override
		public void onComplete(Bundle arg0) {
			Log.d("ShareButton" , "Authentication Successful");

			Profile profileMap = adapter.getUserProfile();
			Log.d("Custom-UI",  "First Name = "       + profileMap.getFirstName());
			Log.d("Custom-UI",  "Last Name  = "       + profileMap.getLastName());
			Log.d("Custom-UI",  "Email      = "       + profileMap.getEmail());
			Log.d("Custom-UI",  "Profile Image URL  = " + profileMap.getProfileImageURL());
			Log.d("Custom-UI" , "Successful");

			List<Contact> contactsList = adapter.getContactList();

			if (contactsList != null && contactsList.size() > 0) {
				for (Contact p : contactsList)  {

					if (TextUtils.isEmpty(p.getFirstName())
							&& TextUtils.isEmpty(p.getLastName()))  
					{
						p.setFirstName(p.getDisplayName());
					}

					Log.d("Custom-UI" ,"Display Name = " +  p.getDisplayName());
					Log.d("Custom-UI" , "First Name = " + p.getFirstName());
					Log.d("Custom-UI" , "Last Name = " + p.getLastName());
					Log.d("Custom-UI" ,"Contact ID = " + p.getId());
					Log.d("Custom-UI" ,"Profile URL = " + p.getProfileUrl()); 
				}
			}

			adapter.updateStatus("Sample application post.."); 
		}

		@Override
		public void onError(SocialAuthError arg0) {
			arg0.printStackTrace();
			Log.d("ERROR" , "Authentication Error >> ");
		}

	}

}
