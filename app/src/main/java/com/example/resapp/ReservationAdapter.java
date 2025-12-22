package com.example.resapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private List<Reservation> reservations;
    private boolean isStaff;
    private DatabaseHelper dbHelper;

    public ReservationAdapter(List<Reservation> reservations) {
        this.reservations = reservations;
        this.isStaff = false;
    }

    public ReservationAdapter(List<Reservation> reservations, boolean isStaff, DatabaseHelper dbHelper) {
        this.reservations = reservations;
        this.isStaff = isStaff;
        this.dbHelper = dbHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_staff_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reservation r = reservations.get(position);

        holder.tvDetails.setText(
                r.getName() + "\n" +
                        r.getDate() + " " + r.getTime() +
                        "\nGuests: " + r.getGuests()
        );

        if (isStaff) {
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnCancel.setOnClickListener(v -> {
                dbHelper.deleteReservation(r.getId());
                reservations.remove(position);
                notifyItemRemoved(position);
            });
        } else {
            holder.btnCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetails;
        Button btnCancel;

        ViewHolder(View itemView) {
            super(itemView);
            tvDetails = itemView.findViewById(R.id.tvReservationDetails);
            btnCancel = itemView.findViewById(R.id.btnCancelReservation);
        }
    }
}
