package br.edu.dmos5.agenda_dmos5.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.model.Contato;

public class ItemContatoAdapter extends ArrayAdapter {
    private LayoutInflater inflater;

    public ItemContatoAdapter(Context context, List<Contato> contatoList) {
        super(context, R.layout.item_lista_contato, contatoList);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lista_contato, null);
            holder = new ViewHolder();
            holder.tituloTextView = convertView.findViewById(R.id.text_nome);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contato obj = (Contato) getItem(position);
        holder.tituloTextView.setText(obj.getNome());
        return convertView;
    }

    private static class ViewHolder {
        public TextView tituloTextView;
    }
}
