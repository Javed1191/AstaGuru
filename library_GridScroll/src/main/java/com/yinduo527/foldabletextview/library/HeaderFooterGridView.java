package com.yinduo527.foldabletextview.library;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import java.util.ArrayList;

/**
 * Created by yinduo on 14-9-28.
 */
public class HeaderFooterGridView extends GridView implements AdapterView.OnItemClickListener {
    private static final String TAG = "HeaderGridView";

    /**
     * A class that represents a fixed view in a list, for example a header at the top
     * or a footer at the bottom.
     */
    private static class FixedViewInfo {
        /**
         * The view to add to the grid
         */
        public View view;
        public ViewGroup viewContainer;
        /**
         * The data backing the view. This is returned from {@link android.widget.ListAdapter#getItem(int)}.
         */
        public Object data;
        /**
         * <code>true</code> if the fixed view should be selectable in the grid
         */
        public boolean isSelectable;
    }

    private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<FixedViewInfo>();

    private ArrayList<FixedViewInfo> mFooterViewInfos = new ArrayList<FixedViewInfo>();

    private OnItemClickListener onItemClickListener = null;

    private int mNumColumns = 1;

    private void initHeaderGridView() {
        super.setClipChildren(false);
    }

    public HeaderFooterGridView(Context context) {
        super(context);
        initHeaderGridView();
    }

    public HeaderFooterGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderGridView();
    }

    public HeaderFooterGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHeaderGridView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ListAdapter adapter = getAdapter();
        if (adapter != null && adapter instanceof HeaderViewGridAdapter) {
            ((HeaderViewGridAdapter) adapter).setNumColumns(getNumColumns());
        }
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
        this.mNumColumns = numColumns;
    }

    public int getNumColumns() {
        return mNumColumns;
    }

    @Override
    public void setClipChildren(boolean clipChildren) {
        // Ignore, since the header rows depend on not being clipped
    }

    /**
     * Add a fixed view to appear at the top of the grid. If addHeaderView is
     * called more than once, the views will appear in the order they were
     * added. Views added using this call can take focus if they want.
     * <p/>
     * NOTE: Call this before calling setAdapter. This is so HeaderGridView can wrap
     * the supplied cursor with one that will also account for header views.
     *
     * @param v            The view to add.
     * @param data         Data to associate with this view
     * @param isSelectable whether the item is selectable
     */
    public void addHeaderView(View v, Object data, boolean isSelectable) {
        FixedViewInfo info = new FixedViewInfo();
        FrameLayout fl = new FullWidthFixedViewLayout(getContext());
        fl.addView(v);
        info.view = v;
        info.viewContainer = fl;
        info.data = data;
        info.isSelectable = isSelectable;
        mHeaderViewInfos.add(info);

        ListAdapter adapter = getAdapter();
        if (adapter != null) {
            if (!(adapter instanceof HeaderViewGridAdapter)) {
                setAdapter(adapter);
            }
            // in the case of re-adding a header view, or adding one later on,
            // we need to notify the observer
            ((HeaderViewGridAdapter) getAdapter()).notifyDataSetChanged();
        }
    }

    /**
     * Add a fixed view to appear at the top of the grid. If addHeaderView is
     * called more than once, the views will appear in the order they were
     * added. Views added using this call can take focus if they want.
     * <p/>
     * NOTE: Call this before calling setAdapter. This is so HeaderGridView can wrap
     * the supplied cursor with one that will also account for header views.
     *
     * @param v The view to add.
     */
    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    public int getHeaderViewCount() {
        return mHeaderViewInfos.size();
    }

    /**
     * Removes a previously-added header view.
     *
     * @param v The view to remove
     * @return true if the view was removed, false if the view was not a header
     * view
     */
    public boolean removeHeaderView(View v) {
        if (mHeaderViewInfos.size() > 0) {
            boolean result = false;
            ListAdapter adapter = getAdapter();
            if (adapter != null && ((HeaderViewGridAdapter) adapter).removeHeader(v)) {
                result = true;
            }
            removeFixedViewInfo(v, mHeaderViewInfos);
            return result;
        }
        return false;
    }

    private void removeFixedViewInfo(View v, ArrayList<FixedViewInfo> where) {
        int len = where.size();
        for (int i = 0; i < len; ++i) {
            FixedViewInfo info = where.get(i);
            if (info.view == v) {
                where.remove(i);
                break;
            }
        }
    }


    /**
     * Add a fixed view to appear at the bottom of the list. If addFooterView is
     * called more than once, the views will appear in the order they were
     * added. Views added using this call can take focus if they want.
     * <p/>
     * Note: When first introduced, this method could only be called before
     * setting the adapter with {@link #setAdapter(ListAdapter)}. Starting with
     * {@link android.os.Build.VERSION_CODES#KITKAT}, this method may be
     * called at any time. If the ListView's adapter does not extend
     * {@link android.widget.HeaderViewListAdapter}, it will be wrapped with a supporting
     * instance of {@link android.widget.WrapperListAdapter}.
     *
     * @param v            The view to add.
     * @param data         Data to associate with this view
     * @param isSelectable true if the footer view can be selected
     */
    public void addFooterView(View v, Object data, boolean isSelectable) {
        final FixedViewInfo info = new FixedViewInfo();
        FrameLayout fl = new FullWidthFixedViewLayout(getContext());
        fl.addView(v);
        info.view = v;
        info.viewContainer = fl;
        info.data = data;
        info.isSelectable = isSelectable;
        mFooterViewInfos.add(info);

        ListAdapter adapter = getAdapter();
        if (adapter != null) {
            if (!(adapter instanceof HeaderViewGridAdapter)) {
                setAdapter(adapter);
            }
            // in the case of re-adding a header view, or adding one later on,
            // we need to notify the observer
            ((HeaderViewGridAdapter) getAdapter()).notifyDataSetChanged();
        }
    }

    /**
     * Add a fixed view to appear at the bottom of the list. If addFooterView is
     * called more than once, the views will appear in the order they were
     * added. Views added using this call can take focus if they want.
     * <p/>
     * Note: When first introduced, this method could only be called before
     * setting the adapter with {@link #setAdapter(ListAdapter)}. Starting with
     * {@link android.os.Build.VERSION_CODES#KITKAT}, this method may be
     * called at any time. If the ListView's adapter does not extend
     * {@link android.widget.HeaderViewListAdapter}, it will be wrapped with a supporting
     * instance of {@link android.widget.WrapperListAdapter}.
     *
     * @param v The view to add.
     */
    public void addFooterView(View v) {
        addFooterView(v, null, true);
    }

    public int getFooterViewCount() {
        return mFooterViewInfos.size();
    }

    /**
     * Removes a previously-added footer view.
     *
     * @param v The view to remove
     * @return true if the view was removed, false if the view was not a footer view
     */
    public boolean removeFooterView(View v) {
        if (mFooterViewInfos.size() > 0) {
            boolean result = false;
            ListAdapter adapter = getAdapter();
            if (adapter != null && ((HeaderViewGridAdapter) adapter).removeFooter(v)) {
                result = true;
            }
            removeFixedViewInfo(v, mFooterViewInfos);
            return result;
        }
        return false;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (mHeaderViewInfos.size() + mFooterViewInfos.size() > 0) {
            HeaderViewGridAdapter headerViewGridAdapter = new HeaderViewGridAdapter(mHeaderViewInfos, mFooterViewInfos, adapter);
            int numColumns = getNumColumns();
            if (numColumns > 1) {
                headerViewGridAdapter.setNumColumns(numColumns);
            }
            super.setAdapter(headerViewGridAdapter);
        } else {
            super.setAdapter(adapter);
        }
    }

    private class FullWidthFixedViewLayout extends FrameLayout {
        public FullWidthFixedViewLayout(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int targetWidth = HeaderFooterGridView.this.getMeasuredWidth();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(targetWidth,
                    MeasureSpec.getMode(widthMeasureSpec));
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(this);
        onItemClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onItemClickListener != null) {
            position = position - mHeaderViewInfos.size() * mNumColumns;
            onItemClickListener.onItemClick(parent, view, position, id);
        }
    }

    /**
     * ListAdapter used when a HeaderGridView has header views. This ListAdapter
     * wraps another one and also keeps track of the header views and their
     * associated data objects.
     * <p>This is intended as a base class; you will probably not need to
     * use this class directly in your own code.
     */
    private static class HeaderViewGridAdapter implements WrapperListAdapter, Filterable {
        // This is used to notify the container of updates relating to number of columns
        // or headers changing, which changes the number of placeholders needed
        private final DataSetObservable mDataSetObservable = new DataSetObservable();
        private final ListAdapter mAdapter;
        private int mNumColumns = 1;
        // This ArrayList is assumed to NOT be null.
        ArrayList<FixedViewInfo> mHeaderViewInfos;
        ArrayList<FixedViewInfo> mFooterViewInfos;
        boolean mAreAllFixedViewsSelectable;
        private final boolean mIsFilterable;

        public HeaderViewGridAdapter(ArrayList<FixedViewInfo> headerViewInfos,
                                     ArrayList<FixedViewInfo> footerViewInfos,
                                     ListAdapter adapter) {
            mAdapter = adapter;
            mIsFilterable = adapter instanceof Filterable;
            if (headerViewInfos == null) {
                throw new IllegalArgumentException("headerViewInfos cannot be null");
            }
            mHeaderViewInfos = headerViewInfos;

            if (footerViewInfos == null) {
                throw new IllegalArgumentException("footerViewInfos cannot be null");
            }
            mFooterViewInfos = footerViewInfos;

            mAreAllFixedViewsSelectable =
                    areAllListInfosSelectable(mHeaderViewInfos)
                            && areAllListInfosSelectable(mFooterViewInfos);
        }

        public int getHeadersCount() {
            return mHeaderViewInfos.size();
        }

        public int getFootersCount() {
            return mFooterViewInfos.size();
        }

        @Override
        public boolean isEmpty() {
            return mAdapter == null || mAdapter.isEmpty();
        }

        public void setNumColumns(int numColumns) {
            if (numColumns < 1) {
                throw new IllegalArgumentException("Number of columns must be 1 or more");
            }
            if (mNumColumns != numColumns) {
                mNumColumns = numColumns;
                notifyDataSetChanged();
            }
        }

        private boolean areAllListInfosSelectable(ArrayList<FixedViewInfo> infos) {
            if (infos != null) {
                for (FixedViewInfo info : infos) {
                    if (!info.isSelectable) {
                        return false;
                    }
                }
            }
            return true;
        }

        public boolean removeHeader(View v) {
            for (int i = 0; i < mHeaderViewInfos.size(); i++) {
                FixedViewInfo info = mHeaderViewInfos.get(i);
                if (info.view == v) {
                    mHeaderViewInfos.remove(i);
                    mAreAllFixedViewsSelectable =
                            areAllListInfosSelectable(mHeaderViewInfos)
                                    && areAllListInfosSelectable(mFooterViewInfos);
                    mDataSetObservable.notifyChanged();
                    return true;
                }
            }
            return false;
        }

        public boolean removeFooter(View v) {
            for (int i = 0; i < mFooterViewInfos.size(); i++) {
                FixedViewInfo info = mFooterViewInfos.get(i);
                if (info.view == v) {
                    mFooterViewInfos.remove(i);
                    mAreAllFixedViewsSelectable =
                            areAllListInfosSelectable(mHeaderViewInfos)
                                    && areAllListInfosSelectable(mFooterViewInfos);
                    mDataSetObservable.notifyChanged();
                    return true;
                }
            }
            return false;
        }

        @Override
        public int getCount() {
            if (mAdapter != null) {
                if (mAdapter.getCount() % mNumColumns == 0) {
                    return getHeadersCount() * mNumColumns + getFootersCount() * mNumColumns + mAdapter.getCount();
                }
                return (getHeadersCount() + getFootersCount() + mAdapter.getCount() / mNumColumns + 1) * mNumColumns;
            } else {
                return getHeadersCount() * mNumColumns + getFootersCount() * mNumColumns;
            }
        }

        @Override
        public boolean areAllItemsEnabled() {
            if (mAdapter != null) {
                return mAreAllFixedViewsSelectable && mAdapter.areAllItemsEnabled();
            } else {
                return true;
            }
        }

        @Override
        public boolean isEnabled(int position) {
            // Header (negative positions will throw an ArrayIndexOutOfBoundsException)
            int numHeadersAndPlaceholders = getHeadersCount() * mNumColumns;
            if (position < numHeadersAndPlaceholders) {
                return (position % mNumColumns == 0)
                        && mHeaderViewInfos.get(position / mNumColumns).isSelectable;
            }
            // Adapter
            final int adjPosition = position - numHeadersAndPlaceholders;
            int adapterCount = 0;
            if (mAdapter != null) {
                adapterCount = mAdapter.getCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.isEnabled(adjPosition);
                }
            }


            int footerPosition = 0;
            if (adapterCount % mNumColumns == 0) {
                footerPosition = adjPosition - adapterCount;
            } else {
                footerPosition = adjPosition - (adapterCount / mNumColumns + 1) * mNumColumns;
            }

            if (position < getCount()) {
                boolean isSelectable = mFooterViewInfos
                        .get(footerPosition / mNumColumns).isSelectable;
                return isSelectable;
            }

            // Footer (off-limits positions will throw an IndexOutOfBoundsException)
            throw new ArrayIndexOutOfBoundsException(position);
        }

        @Override
        public Object getItem(int position) {
            // Header (negative positions will throw an ArrayIndexOutOfBoundsException)
            int numHeadersAndPlaceholders = getHeadersCount() * mNumColumns;
            if (position < numHeadersAndPlaceholders) {
                if (position % mNumColumns == 0) {
                    return mHeaderViewInfos.get(position / mNumColumns).data;
                }
                return null;
            }
            // Adapter
            final int adjPosition = position - numHeadersAndPlaceholders;
            int adapterCount = 0;
            if (mAdapter != null) {
                adapterCount = mAdapter.getCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItem(adjPosition);
                }
            }

            int footerPosition = 0;
            if (adapterCount % mNumColumns == 0) {
                footerPosition = adjPosition - adapterCount;
            } else {
                footerPosition = adjPosition - (adapterCount / mNumColumns + 1) * mNumColumns;
            }

            if (position < getCount()) {
                Object object = mFooterViewInfos
                        .get(footerPosition / mNumColumns).data;

                if (position % mNumColumns == 0) {
                    return object;
                } else {
                    return new Object();
                }
            }

            // Footer (off-limits positions will throw an IndexOutOfBoundsException)
            throw new ArrayIndexOutOfBoundsException(position);
        }

        @Override
        public long getItemId(int position) {
            int numHeadersAndPlaceholders = getHeadersCount() * mNumColumns;
            if (mAdapter != null && position >= numHeadersAndPlaceholders) {
                int adjPosition = position - numHeadersAndPlaceholders;
                int adapterCount = mAdapter.getCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public boolean hasStableIds() {
            if (mAdapter != null) {
                return mAdapter.hasStableIds();
            }
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Header (negative positions will throw an ArrayIndexOutOfBoundsException)
            int numHeadersAndPlaceholders = getHeadersCount() * mNumColumns;
            if (position < numHeadersAndPlaceholders) {
                View headerViewContainer = mHeaderViewInfos
                        .get(position / mNumColumns).viewContainer;
                if (position % mNumColumns == 0) {
                    return headerViewContainer;
                } else {
                    if (convertView == null) {
                        convertView = new View(parent.getContext());
                    }
                    // We need to do this because GridView uses the height of the last item
                    // in a row to determine the height for the entire row.
                    convertView.setVisibility(View.INVISIBLE);
                    convertView.setMinimumHeight(headerViewContainer.getHeight());
                    return convertView;
                }
            }

            // Adapter
            final int adjPosition = position - numHeadersAndPlaceholders;
            int adapterCount = 0;
            if (mAdapter != null) {
                adapterCount = mAdapter.getCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getView(adjPosition, convertView, parent);
                } else if (adapterCount % mNumColumns != 0 && adjPosition < (adapterCount / mNumColumns + 1) * mNumColumns) {

                    if (convertView == null) {
                        convertView = mAdapter.getView((adjPosition / mNumColumns) * mNumColumns, convertView, parent);
                    }
                    convertView.setVisibility(View.INVISIBLE);
                    return convertView;
                }
            }

            int footerPosition = 0;
            if (adapterCount % mNumColumns == 0) {
                footerPosition = adjPosition - adapterCount;
            } else {
                footerPosition = adjPosition - (adapterCount / mNumColumns + 1) * mNumColumns;
            }

            if (position < getCount()) {
                View footerViewContainer = mFooterViewInfos
                        .get(footerPosition / mNumColumns).viewContainer;

                if (position % mNumColumns == 0) {

                    return footerViewContainer;
                } else {
                    if (convertView == null) {
                        convertView = new View(parent.getContext());
                    }
                    // We need to do this because GridView uses the height of the last item
                    // in a row to determine the height for the entire row.
                    convertView.setVisibility(View.INVISIBLE);
                    convertView.setMinimumHeight(footerViewContainer.getHeight());
                    return convertView;
                }
            }
            // Footer (off-limits positions will throw an IndexOutOfBoundsException)
            throw new ArrayIndexOutOfBoundsException(position);
        }

        @Override
        public int getItemViewType(int position) {
            int numHeadersAndPlaceholders = getHeadersCount() * mNumColumns;
            if (position < numHeadersAndPlaceholders && (position % mNumColumns != 0)) {
                // Placeholders get the last view type number
                return mAdapter != null ? mAdapter.getViewTypeCount() : 1;
            }
            if (mAdapter != null && position >= numHeadersAndPlaceholders) {
                int adjPosition = position - numHeadersAndPlaceholders;
                int adapterCount = mAdapter.getCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemViewType(adjPosition);
                }
            }
            return AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
        }

        @Override
        public int getViewTypeCount() {
            if (mAdapter != null) {
                return mAdapter.getViewTypeCount() + 1;
            }
            return 2;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            mDataSetObservable.registerObserver(observer);
            if (mAdapter != null) {
                mAdapter.registerDataSetObserver(observer);
            }
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            mDataSetObservable.unregisterObserver(observer);
            if (mAdapter != null) {
                mAdapter.unregisterDataSetObserver(observer);
            }
        }

        @Override
        public Filter getFilter() {
            if (mIsFilterable) {
                return ((Filterable) mAdapter).getFilter();
            }
            return null;
        }

        @Override
        public ListAdapter getWrappedAdapter() {
            return mAdapter;
        }

        public void notifyDataSetChanged() {
            mDataSetObservable.notifyChanged();
        }
    }
}
