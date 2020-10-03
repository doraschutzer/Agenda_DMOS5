package br.edu.dmos5.agenda_dmos5.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.model.Email;

public class ItemEmailAdapter extends RecyclerView.Adapter<ItemEmailAdapter.EmailViewHolder>{
    private static AdapterClickListener clickListener;
    private List<Email> emails;

    public void setClickListener(AdapterClickListener clickListener) {
        ItemEmailAdapter.clickListener = clickListener;
    }

    public ItemEmailAdapter(List<Email> emails) {
        this.emails = emails;
    }

    @NonNull
    @Override
    public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email, parent, false);
        EmailViewHolder viewHolder = new EmailViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmailViewHolder holder, int position) {
        holder.textEmail.setText(emails.get(position).getDominio());
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    public class EmailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textEmail;

        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);

            textEmail = itemView.findViewById(R.id.text_email);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onContactClick(getAdapterPosition());
            }
        }
    }
}
