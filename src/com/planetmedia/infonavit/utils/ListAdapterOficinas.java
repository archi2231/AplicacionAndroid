package com.planetmedia.infonavit.utils;

import java.util.ArrayList;

import com.planetmedia.infonavit.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapterOficinas extends BaseAdapter{
	
	private Context context;
	ArrayList<String[]> list;
	
	public ListAdapterOficinas(Context context, ArrayList<String[]> list) {
		this.context =  context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_item_oficina, null);
        }
		
		TextView txtTitulo = (TextView)convertView.findViewById(R.id.txt_titulo_oficina);
		TextView txtSubtitulo = (TextView)convertView.findViewById(R.id.txt_subtitulo_oficina);
		
		txtTitulo.setText(list.get(position)[0]);
		txtSubtitulo.setText(list.get(position)[1]);
		
        return convertView;
	}
}
