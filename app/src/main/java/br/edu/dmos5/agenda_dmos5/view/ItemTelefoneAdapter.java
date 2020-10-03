package br.edu.dmos5.agenda_dmos5.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.enums.TelefoneEnum;
import br.edu.dmos5.agenda_dmos5.model.Telefone;

public class ItemTelefoneAdapter extends RecyclerView.Adapter<ItemTelefoneAdapter.TelefoneViewHolder> {
    private static AdapterClickListener clickListener;
    private List<Telefone> telefones;

    public void setClickListener(AdapterClickListener clickListener) {
        ItemTelefoneAdapter.clickListener = clickListener;
    }

    public ItemTelefoneAdapter(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    @NonNull
    @Override
    public TelefoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_telefone, parent, false);
        TelefoneViewHolder viewHolder = new TelefoneViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TelefoneViewHolder holder, int position) {
        holder.contatoTel.setText(telefones.get(position).getNumero());
        if (telefones.get(position).getTelefoneEnum().equals(TelefoneEnum.celular))
            holder.imagem.setImageResource(R.drawable.ic_celular);
        else
            holder.imagem.setImageResource(R.drawable.ic_telefone);
    }

    @Override
    public int getItemCount() {
        if (telefones != null) return telefones.size();
        else return 0;
    }

    public class TelefoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView contatoTel;

        public ImageView imagem;

        public TelefoneViewHolder(@NonNull View itemView) {
            super(itemView);

            contatoTel = itemView.findViewById(R.id.text_telefone);

            imagem = itemView.findViewById(R.id.imagem_telefone);

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
