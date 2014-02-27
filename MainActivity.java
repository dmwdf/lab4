package com.dmwdf.travelGuide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button chanarea;
	TextView display;
	EditText getcity;
	EditText getcity2;
	String temperature;
	String wea;
	String jsonstring;
	String city;
	int z;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		chanarea = (Button)findViewById(R.id.button1);
		display = (TextView)findViewById(R.id.TextView3);
		getcity = (EditText)findViewById(R.id.getcity);
		getcity2 = (EditText)findViewById(R.id.EditText01);
		final int z;
				
		chanarea.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					HttpClient htp = new DefaultHttpClient();
					Editable add= getcity.getText();
					Editable add2= getcity2.getText();
					String url="http://maps.googleapis.com/maps/api/distancematrix/json?origins="+add.toString()+"&destinations="+add2.toString()+"&mode=driving&language=fr-FR&sensor=false";
					HttpResponse response =null;
					
					HttpPost htppost = new HttpPost(url);
					try{
						response = htp.execute(htppost);
					}catch (ClientProtocolException e1){
						e1.printStackTrace();
					}catch (IOException e1){
						e1.printStackTrace();
					}
					StatusLine statusline = response.getStatusLine();
					if(statusline.getStatusCode()==HttpStatus.SC_OK)
					{
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						try{
							response.getEntity().writeTo(out);
							
						}catch (IOException e){
							e.printStackTrace();
						}
						try{ 
							out.close();
						}catch (IOException e){
							e.printStackTrace();
						}
						jsonstring = out.toString();
					}
					try{
						JSONObject obs = new JSONObject(jsonstring);
						JSONArray row = (JSONArray) obs.get("rows") ;
						JSONObject obj2 = (JSONObject) row.get(0) ;
						JSONArray ele = (JSONArray) obj2.get("elements")  ;
						JSONObject obj3 = (JSONObject) ele.get(0) ;
						JSONObject obj = obj3.getJSONObject("distance");
						JSONObject obj11 = obj3.getJSONObject("duration");
						
						
						wea = obj.getString("text");
						city = obj11.getString("text");
						
						
					}catch(JSONException e){
						e.printStackTrace();
					}
					
					display.setText("Estimations \nDistance : "+wea+"\n Time for travel :"+ city);
					
							}
		});				
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/RelativeLayout1"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F241EF"
    android:orientation="vertical"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context=".MainActivity" >

    <TextView
        android:id="@+id/TextView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_gravity="center"
        android:gravity="center"
        android:text="Plan Advisor!!"
        android:textSize="30sp" />

    <TextView
        android:id="@+id/TextView3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/TextView1"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="40dp"
        android:gravity="center"
        android:text="Estimations \nDistance : \n Cost for travel :"
        android:textSize="20sp" />

    <EditText
        android:id="@+id/getcity"
        android:layout_width="133dp"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerInParent="true"
        android:layout_marginBottom="66dp"
        android:ems="10"
        android:gravity="center"
        android:inputType="text"
        android:shadowColor="@android:color/white"
        android:text="Mumbai" />

    <Button
        android:id="@+id/button1"
        android:layout_width="250dp"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:text="Check Suggestions"
        android:textSize="20dp" />

    <EditText
        android:id="@+id/EditText01"
        android:layout_width="133dp"
        android:layout_height="wrap_content"
        android:layout_above="@+id/TextView2"
        android:layout_centerInParent="true"
        android:layout_marginBottom="25dp"
        android:ems="10"
        android:gravity="center"
        android:inputType="text"
        android:shadowColor="@android:color/white"
        android:text="Hyderabad" >

        <requestFocus />
    </EditText>

    <TextView
        android:id="@+id/TextView01"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@+id/EditText01"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="20dp"
        android:gravity="center"
        android:text="Enter Source City"
        android:textSize="20sp" />

    <TextView
        android:id="@+id/TextView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@+id/getcity"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="14dp"
        android:gravity="center"
        android:text="Enter Destination city"
        android:textSize="20sp" />

</RelativeLayout>