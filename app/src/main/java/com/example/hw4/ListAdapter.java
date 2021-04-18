package com.example.hw4;

import android.content.Context;
// import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * A custom adapter implementation that binds views to items of a list
 * @param <I> type of list item (data)
 */
public class ListAdapter<I> extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
  
  public interface ItemBinder<I> {
    /**
     * apply the data in item to itemView
     * @param itemView the view to bind to data
     * @param item the data to apply to itemView
     * @param position the position in the list
     */
    void bind(View itemView, I item, int position);
  }
  
  
  private final Context mCtx;
  
  // treated as a re-assignable reference to immutable lists
  private List<I> mList;
  private final int mItemResId;
  private final ItemBinder<I> mBind;
  
  /**
   * 
   * @param ctx parent context
   * @param list immutable initial list of I items
   * @param itemResId resource id of item's layout
   * @param bind function that takes the itemView to bind, and the I item (data) to bind to;
   *             and binds itemView to reflect the I item's data
   */
  public ListAdapter(Context ctx, List<I> list, int itemResId, ItemBinder<I> bind) {
    mCtx = ctx;
    mList = list;
    mItemResId = itemResId;
    mBind = bind;
  }
  
  /**
   * update with new data; this lets us use immutable lists
   */
  public void setData(List<I> list) {
    mList = list;
    notifyDataSetChanged();
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // Log.d("ListAdapter", "createViewHolder");
    View itemView = LayoutInflater.from(mCtx).inflate(mItemResId, parent, false);
    return new ViewHolder(itemView);
  }
  
  @Override
  public int getItemCount() {
    return mList.size();
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // Log.d("ListAdapter", "bindViewHolder " + position);
    I item = mList.get(position);
    mBind.bind(holder.mItemView, item, position);
  }
  
  
  public static class ViewHolder extends RecyclerView.ViewHolder {
    
    private final View mItemView;
  
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      mItemView = itemView;
    }
    
  }
  
  
}
