package com.planetmedia.infonavit.listadapters;

import java.util.ArrayList;

import com.planetmedia.infonavit.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResumenCreditoListAdapter extends BaseExpandableListAdapter {
	
	private ArrayList<String> groups;
    private ArrayList<ArrayList<ArrayList<String>>> children;

	private Context context;
	
	private LayoutInflater infalInflater;
	
	private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET =
            {android.R.attr.state_expanded};
	
    private static final int[][] GROUP_STATE_SETS = {
        EMPTY_STATE_SET, // 0
        GROUP_EXPANDED_STATE_SET // 1
	};
    
   
    public ResumenCreditoListAdapter(Context context, ArrayList<String> groups, ArrayList<ArrayList<ArrayList<String>>> children) {
        this.context = context;
        this.groups = groups;
        this.children = children;
    }
    
    @Override
    public boolean areAllItemsEnabled(){
        return true;
    }
    
    @Override
    public ArrayList<String> getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }
    
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(getChildrenCount(groupPosition) > 0){
        	if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.row_child_resumen_credito, null);
            }        	
        	
        	Log.d("group", ""+groupPosition);
        	Log.d("child", ""+childPosition);
        	
        	/*if(! ((getChildrenCount(groupPosition)-1) > childPosition)){
        		convertView.setBackgroundResource(R.drawable.standar_seis3);
        	}*/
        	
        	if(isLastChild){
        		convertView.setBackgroundResource(R.drawable.standar_seis3);
        	}
        	
        	TextView txtChildKey = (TextView) convertView.findViewById(R.id.txt_item_child_key);      
        	txtChildKey.setText((getChild(groupPosition, childPosition)).get(0));
        	
        	TextView txtChildValue = (TextView) convertView.findViewById(R.id.txt_item_child_key);      
        	//txtChildValue.setText((getChild(groupPosition, childPosition)).get(1));
        	txtChildValue.setText("grupo " + getChildrenCount(groupPosition) + " child " + childPosition);
        	return convertView;
        }
        return null;
    }

    @Override
    public String getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	
		if (convertView == null) {
		    infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    convertView = infalInflater.inflate(R.layout.row_group_resumen_credito, null);
		}  	 
		
		ImageView imgAction = (ImageView) convertView.findViewById(R.id.row_img_action);
		TextView description = (TextView) convertView.findViewById(R.id.textView9);
		
		String group = (String) getGroup(groupPosition);
		description.setText(group);
		
		RelativeLayout bodyItem = (RelativeLayout) convertView.findViewById(R.id.expandable_body_item);
		
		if( imgAction != null ) {
			ImageView indicator = (ImageView)imgAction;
			if( getChildrenCount( groupPosition ) == 0 ) {
				indicator.setVisibility( View.INVISIBLE );				
			} else {
				indicator.setVisibility( View.VISIBLE );
				int stateSetIndex = ( isExpanded ? 1 : 0) ;
				Drawable drawable = indicator.getDrawable();
				drawable.setState(GROUP_STATE_SETS[stateSetIndex]);
				
				Drawable drawableRowBody = bodyItem.getBackground();
				drawableRowBody.setState(GROUP_STATE_SETS[stateSetIndex]);
			}
		}
		
		return convertView;
        
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }
        
}


