package com.mikepenz.aboutlibraries.ui.item;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.mikepenz.aboutlibraries.R;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;


/**
 * Created by mikepenz on 28.12.15.
 */
public class LoaderItem extends AbstractItem<LoaderItem, LoaderItem.ViewHolder> {

    @Override
    public boolean isSelectable() {
        return false;
    }

    /**
     * defines the type defining this item. must be unique. preferably an id
     *
     * @return the type
     */
    @Override
    public int getType() {
        return R.id.loader_item_id;
    }

    /**
     * defines the layout which will be used for this item in the list
     *
     * @return the layout for this item
     */
    @Override
    public int getLayoutRes() {
        return R.layout.listloader_opensource;
    }

    /**
     * binds the data of this item onto the viewHolder
     *
     * @param holder the viewHolder of this item
     */
    @Override
    public void bindView(final ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    /**
     * our ViewHolder
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public ViewHolder(View headerView) {
            super(headerView);

            //get the about this app views
            progressBar = headerView.findViewById(R.id.progressBar);
        }
    }
}
