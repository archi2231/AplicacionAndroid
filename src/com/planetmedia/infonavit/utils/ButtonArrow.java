package com.planetmedia.infonavit.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ButtonArrow extends ImageButton{

	private boolean isExpanded;
	private LinearLayout layout;
	private ImageView arrow;
	
	private Context context;
	
	OnClickListener clicker = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			
			if(!isExpanded){
				isExpanded = true;
				arrow.setSelected(true);
				view.setSelected(true);
				layout.setVisibility(View.VISIBLE);
				
			}
			else{
				isExpanded = false;		
				arrow.setSelected(false);
				view.setSelected(false);
				layout.setVisibility(View.GONE);
			}			
		}
	};
	
	public void attachResources(LinearLayout layout, ImageView arrow){
		this.layout = layout;
		this.arrow = arrow;
		layout.setVisibility(View.GONE);	
	}
	
	public ButtonArrow(Context context) {
		super(context);
		this.context = context;
		setOnClickListener(clicker);				
	}

	public ButtonArrow(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnClickListener(clicker);
	}
	

	public ButtonArrow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);	
		setOnClickListener(clicker);
	}
}
