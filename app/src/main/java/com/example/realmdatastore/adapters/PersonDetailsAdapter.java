package com.example.realmdatastore.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.realmdatastore.models.PersonDetailsModel;
import com.example.realmdatastore.R;
import com.example.realmdatastore.activities.MainActivity;

import java.util.ArrayList;

public class PersonDetailsAdapter extends BaseAdapter {

    private ArrayList<PersonDetailsModel> personDetailsArrayList;
    private Context context;
    private LayoutInflater inflater;

    public PersonDetailsAdapter(Context context, ArrayList<PersonDetailsModel> personDetailsArrayList) {
        this.context = context;
        this.personDetailsArrayList = personDetailsArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return personDetailsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return personDetailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.inflate_list_item, null);
            holder = new Holder();
            holder.tvPersonName = (TextView) v.findViewById(R.id.tvPersonName);
            holder.ivEditPesonDetail=(ImageView)v.findViewById(R.id.ivEditPesonDetail);
            holder.ivDeletePerson=(ImageView)v.findViewById(R.id.ivDeletePerson);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

        holder.tvPersonName.setText(personDetailsArrayList.get(position).getName());
        holder.ivEditPesonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDetailsModel dataToEditModel= MainActivity.getInstance().searchPerson(personDetailsArrayList.get(position).getId());
                MainActivity.getInstance().addOrUpdatePersonDetailsDialog(dataToEditModel,position);
            }
        });
        holder.ivDeletePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowConfirmDialog(context,personDetailsArrayList.get(position).getId(), position);
            }
        });
        return v;
    }

    class Holder {
        TextView tvPersonName;
        ImageView ivDeletePerson, ivEditPesonDetail;
    }

    public static void ShowConfirmDialog(Context context,final int personId,final int position)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage("Are you sure you want to delete this record?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        MainActivity.getInstance().deletePerson(personId,position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
