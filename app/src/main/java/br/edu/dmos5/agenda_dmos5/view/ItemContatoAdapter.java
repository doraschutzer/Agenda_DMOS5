package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;

public class ItemContatoAdapter extends RecyclerView.Adapter<ItemContatoAdapter.ContatosViewHolder> {

    private ContatoDao contatoDao;
    private List<Contato> contatoList;
    private static RecyclerItemClickListener clickListener;

    public ItemContatoAdapter(List<Contato> siteList, Context context) {
        this.contatoList = siteList;
        contatoDao = new ContatoDao(context);
    }

    public void setClickListener(RecyclerItemClickListener clickListener) {
        ItemContatoAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ContatosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_contato, parent,
                false);
        ContatosViewHolder viewHolder = new ContatosViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContatosViewHolder holder, int position) {
        holder.nomeTextView.setText(contatoList.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return contatoList.size();
    }

    public static class ContatosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nomeTextView;

        /*
        O Construtor recupera os elementos de layout
         */
        public ContatosViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.text_nome);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }

    public void updateDataSet(){
        notifyDataSetChanged();
    }
}