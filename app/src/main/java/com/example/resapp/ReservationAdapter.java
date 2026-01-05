package com.example.resapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private final Context context;
    private final List<Reservation> reservations;
    private final boolean isStaff;
    private final DatabaseHelper dbHelper;

    // For guest mode, this is the logged-in guest email.
    // For staff mode, you can pass null (not needed).
    private final String loggedInUserEmail;

    public ReservationAdapter(Context context,
                              List<Reservation> reservations,
                              boolean isStaff,
                              DatabaseHelper dbHelper,
                              String loggedInUserEmail) {
        this.context = context;
        this.reservations = reservations;
        this.isStaff = isStaff;
        this.dbHelper = dbHelper;
        this.loggedInUserEmail = loggedInUserEmail;
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
                        "\nGuests: " + r.getGuests() +
                        (r.getRequests() != null && !r.getRequests().isEmpty()
                                ? "\nRequests: " + r.getRequests()
                                : "")
        );

        if (isStaff) {

            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnDelete.setOnClickListener(v -> {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos == RecyclerView.NO_POSITION) return;

                int rows = dbHelper.deleteReservationStaff(r.getId(), r.getEmail());

                if (rows > 0) {
                    reservations.remove(adapterPos);
                    notifyItemRemoved(adapterPos);
                }
            });

        } else {
            // GUEST MODE
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnEdit.setOnClickListener(v -> {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos == RecyclerView.NO_POSITION) return;

                Intent intent = new Intent(context, EditReservationActivity.class);
                intent.putExtra("reservation_id", r.getId());
                intent.putExtra("user_email", loggedInUserEmail); // REQUIRED
                context.startActivity(intent);
            });

            holder.btnDelete.setOnClickListener(v -> {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos == RecyclerView.NO_POSITION) return;

                int rows = dbHelper.deleteReservationForUser(r.getId(), loggedInUserEmail);

                if (rows > 0) {
                    reservations.remove(adapterPos);
                    notifyItemRemoved(adapterPos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetails;
        Button btnEdit, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvDetails = itemView.findViewById(R.id.tvReservationDetails);
            btnEdit = itemView.findViewById(R.id.btnEditReservation);
            btnDelete = itemView.findViewById(R.id.btnCancelReservation);
        }
    }
}
