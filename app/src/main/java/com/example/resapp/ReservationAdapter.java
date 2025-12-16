package com.example.resapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private List<Reservation> reservationList;

    public ReservationAdapter(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation res = reservationList.get(position);
        holder.tvName.setText(res.getName());
        holder.tvDateTime.setText(res.getDate() + " | " + res.getTime());
        holder.tvGuests.setText("Guests: " + res.getGuests());
        holder.tvRequests.setText("Requests: " + res.getRequests());
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDateTime, tvGuests, tvRequests;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvResName);
            tvDateTime = itemView.findViewById(R.id.tvResDateTime);
            tvGuests = itemView.findViewById(R.id.tvResGuests);
            tvRequests = itemView.findViewById(R.id.tvResRequests);
        }
    }
}
