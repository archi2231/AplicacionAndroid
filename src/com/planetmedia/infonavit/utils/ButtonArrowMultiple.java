package com.planetmedia.infonavit.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ButtonArrowMultiple extends ImageButton{

	private boolean isExpanded;
	private LinearLayout layout;
	private ImageView arrow;
	
	public void attachResources(LinearLayout layout, ImageView arrow){
		this.layout = layout;
		this.arrow = arrow;
		layout.setVisibility(View.GONE);
		
	}
	
	public ButtonArrowMultiple(Context context) {
		super(context);					
	}

	public ButtonArrowMultiple(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	public ButtonArrowMultiple(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);	
	}
	
	public void checkSelected(){
		
		if(!isExpanded){
			isExpanded = true;
			arrow.setSelected(true);
			this.setSelected(true);
			layout.setVisibility(View.VISIBLE);
			
		}
		else{
			isExpanded = false;		
			arrow.setSelected(false);
			this.setSelected(false);
			layout.setVisibility(View.GONE);
		}
	}
}