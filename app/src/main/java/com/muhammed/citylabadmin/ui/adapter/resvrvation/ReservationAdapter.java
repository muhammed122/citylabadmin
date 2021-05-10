package com.muhammed.citylabadmin.ui.adapter.resvrvation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.muhammed.citylabadmin.R;
import com.muhammed.citylabadmin.data.model.reservation.Booking;
import com.muhammed.citylabadmin.data.model.reservation.Datum;
import com.muhammed.citylabadmin.data.model.user.User;
import com.muhammed.citylabadmin.helper.FileData;
import com.muhammed.citylabadmin.ui.adapter.result.ResultFileClickListener;
import com.muhammed.citylabadmin.ui.adapter.result.ResultImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReservationAdapter  extends RecyclerView.Adapter<ReservationAdapter.ReservationHolder> {

 Context context;
    private List<Datum> reserv = new ArrayList<>();
    public void addreserv(List<Datum> reserv ) {
        this.reserv = reserv;
        notifyDataSetChanged();
    }

    public  ReservationAdapter(Context context) {

        this.context=context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReservationAdapter.ReservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationAdapter.ReservationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ReservationHolder holder, int position) {
        if(reserv.get(position).getmName()!=null && reserv.get(position).getmPhoneNumber()!=null) {

            holder.name.setText(reserv.get(position).getmName());
            holder.phone.setText(reserv.get(position).getmPhoneNumber());
            holder.age.setText(reserv.get(position).getmAge());
            holder.date.setText(reserv.get(position).getmReservationDate().split(" ")[0]);
            if (reserv.get(position).getmType() == 1)
                holder.type.setText("حجز منزلي");
            Glide.with(context).load("http://" + reserv.get(position).getmFile().toString())
                    .into(holder.image_test);
            holder.address.setText(reserv.get(position).getmAddress().toString() + "\n" + reserv.get(position).getmBuildingNo().toString());
        }
    }

    @Override
    public int getItemCount() {
        return reserv.size();
    }

    public static class ReservationHolder extends RecyclerView.ViewHolder {

            ImageView image_test;
            TextView name,age,phone,date,address,type;
        public ReservationHolder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name_id_user_reservation);
                image_test=itemView.findViewById(R.id.image_id_item_resevation);
                age=itemView.findViewById(R.id.age_id_user_reservation);
                phone=itemView.findViewById(R.id.phone_user_reservation);
                date=itemView.findViewById(R.id.day_id_user_reservation);
                address=itemView.findViewById(R.id.address_id_user_reservation);
                type=itemView.findViewById(R.id.type_id_user_reservation);
        }
    }
}
