package com.thai.qlkh;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KhachHangAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<KhachHangModel> khachHang;
    ArrayList<KhachHangModel> khachHangold;
    Manager managerActivity;

    public KhachHangAdapter(Context context, ArrayList<KhachHangModel> khachHang, Manager managerActivity) {
        this.context = context;
        this.khachHang = khachHang;
        this.managerActivity = managerActivity;
        this.khachHangold = khachHang;
    }

    @Override
    public int getCount() {
        return khachHang.size();
    }

    @Override
    public Object getItem(int position) {
        return khachHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view =inflater.inflate(R.layout.item_khachhang,viewGroup,false);
        CircleImageView imgAvatar = view.findViewById(R.id.lvAvatar);
        TextView txtHoTen = view.findViewById(R.id.lvTen);
        TextView txtDiaChi = view.findViewById(R.id.lvDiaChi);
        TextView txtSDT = view.findViewById(R.id.lvSDT);

        txtHoTen.setText(String.valueOf(khachHang.get(i).getHT()));
        txtDiaChi.setText(String.valueOf(khachHang.get(i).getDiaChi()));
        txtSDT.setText(String.valueOf(khachHang.get(i).getSDT()));
        if (!khachHang.get(i).getAvatar().equals("")|| !TextUtils.isEmpty(khachHang.get(i).getAvatar()))
            Picasso.get().load(khachHang.get(i).getAvatar()).into(imgAvatar);
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    khachHang = khachHangold;
                }
                else{
                    ArrayList<KhachHangModel> list = new ArrayList<>();
                    for (KhachHangModel khachHangModel: khachHangold){
                        if (khachHangModel.getHT().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(khachHangModel);
                        }
                    }
                    khachHang = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = khachHang;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                khachHang = (ArrayList<KhachHangModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
