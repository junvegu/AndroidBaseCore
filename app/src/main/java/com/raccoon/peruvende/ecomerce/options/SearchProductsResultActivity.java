package com.raccoon.peruvende.ecomerce.options;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raccoon.peruvende.R;
import com.raccoon.peruvende.core.ScrollChildSwipeRefreshLayout;
import com.raccoon.peruvende.ecomerce.fragments.ImageListFragment;
import com.raccoon.peruvende.ecomerce.log.BaseModel;
import com.raccoon.peruvende.ecomerce.log.BussinesModel;
import com.raccoon.peruvende.ecomerce.log.ProductModel;
import com.raccoon.peruvende.ecomerce.log.ServiceFactory;
import com.raccoon.peruvende.ecomerce.log.UserRequest;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchProductsResultActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_product)
    Button btnProduct;
    @BindView(R.id.btn_company)
    Button btnCompany;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiped)
    ScrollChildSwipeRefreshLayout swiped;
    @BindView(R.id.body)
    FrameLayout body;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    boolean typeSearch = false;
    String query;

    ImageListFragment.SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;

    SimpleBussinesRecyclerViewAdapter simpleBussinesRecyclerViewAdapter;
    @BindView(R.id.recyclerviewOff)
    RecyclerView recyclerviewOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        ButterKnife.bind(this);

        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        swiped.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.black),
                ContextCompat.getColor(this, R.color.dark_gray),
                ContextCompat.getColor(this, R.color.black)
        );

        swiped.setScrollUpChild(recyclerview);
        swiped.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadProducts(query);
            }
        });

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerviewOff.setLayoutManager(new LinearLayoutManager(this));


        simpleStringRecyclerViewAdapter = new ImageListFragment.SimpleStringRecyclerViewAdapter(recyclerview, new ArrayList<ProductModel>());
        recyclerview.setAdapter(simpleStringRecyclerViewAdapter);


        simpleBussinesRecyclerViewAdapter = new SimpleBussinesRecyclerViewAdapter(recyclerview, new ArrayList<BussinesModel>());
        recyclerviewOff.setAdapter(simpleBussinesRecyclerViewAdapter);
        handleIntent(getIntent());
    }


    public void loadProducts(String search) {
        UserRequest loginService =
                ServiceFactory.createService(UserRequest.class);
        Call<BaseModel<ArrayList<ProductModel>>> call = loginService.searchProducts(BussinesModel.token,BussinesModel.id,
                search);

        setLoadingIndicator(true);
        call.enqueue(new Callback<BaseModel<ArrayList<ProductModel>>>() {
            @Override
            public void onResponse(@NonNull Call<BaseModel<ArrayList<ProductModel>>> call, @NonNull Response<BaseModel<ArrayList<ProductModel>>> response) {


                setLoadingIndicator(false);
                if (response.isSuccessful()) {


                    simpleStringRecyclerViewAdapter.setItems(response.body().getData());

                    Log.e("asdasdasds", response.body().toString());
                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseModel<ArrayList<ProductModel>>> call, @NonNull Throwable t) {
                setLoadingIndicator(false);
            }
        });
    }


    public void loadCompany(String search) {
        UserRequest loginService =
                ServiceFactory.createService(UserRequest.class);
        Call<BaseModel<ArrayList<BussinesModel>>> call = loginService.searchCompany(BussinesModel.token,BussinesModel.id,
                search);

        setLoadingIndicator(true);
        call.enqueue(new Callback<BaseModel<ArrayList<BussinesModel>>>() {
            @Override
            public void onResponse(@NonNull Call<BaseModel<ArrayList<BussinesModel>>> call, @NonNull Response<BaseModel<ArrayList<BussinesModel>>> response) {


                setLoadingIndicator(false);
                if (response.isSuccessful()) {


                    simpleBussinesRecyclerViewAdapter.setItems(response.body().getData());

                    Log.e("asdasdasds", response.body().toString());
                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseModel<ArrayList<BussinesModel>>> call, @NonNull Throwable t) {
                setLoadingIndicator(false);
            }
        });
    }


    public void setLoadingIndicator(final boolean active) {


        // Make sure setRefreshing() is called after the layout is done with everything else.
        swiped.post(new Runnable() {
            @Override
            public void run() {
                swiped.setRefreshing(active);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.getItem(0);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setFocusable(true);
        searchItem.expandActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {

                    simpleStringRecyclerViewAdapter.clear();
                } else {

                    query = newText;
                    if (!typeSearch) {
                        query = newText;
                        loadProducts(newText);
                    } else {
                        loadCompany(newText);
                    }

                }
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            // this.query = query;
            //loadProducts(query);
            //use the query to search your data somehow
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick({R.id.btn_product, R.id.btn_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_product:

                typeSearch = false;
                simpleBussinesRecyclerViewAdapter.clear();
                btnProduct.setBackgroundResource(R.color.second_step_font_color);
                btnCompany.setBackgroundResource(R.color.white);
                break;
            case R.id.btn_company:
                simpleStringRecyclerViewAdapter.clear();
                typeSearch = true;
                btnCompany.setBackgroundResource(R.color.second_step_font_color);
                btnProduct.setBackgroundResource(R.color.white);
                break;
        }
    }


    public static class SimpleBussinesRecyclerViewAdapter
            extends RecyclerView.Adapter<ImageListFragment.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private String[] mValues;
        private RecyclerView mRecyclerView;
        private ArrayList<BussinesModel> productModels;

        public void setItems(ArrayList<BussinesModel> productModels) {

            this.productModels = productModels;
            notifyDataSetChanged();
        }

        public void clear() {
            productModels = new ArrayList<>();
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public final TextView name;
            public final TextView description;
            public final TextView price;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.image1);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
                name = (TextView) view.findViewById(R.id.name);
                description = (TextView) view.findViewById(R.id.description);
                price = (TextView) view.findViewById(R.id.price);
            }
        }

        public SimpleBussinesRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<BussinesModel> productModels) {
            this.productModels = productModels;
            this.mRecyclerView = recyclerView;
        }

        public SimpleBussinesRecyclerViewAdapter(RecyclerView recyclerView, String[] items) {
            mValues = items;
            mRecyclerView = recyclerView;
        }

        @Override
        public ImageListFragment.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ImageListFragment.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ImageListFragment.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            /*if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }*/
        }

        @Override
        public void onBindViewHolder(final ImageListFragment.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final BussinesModel productModel = productModels.get(position);


            holder.mImageView.setVisibility(View.GONE);
            holder.name.setText(productModel.getBussinessName());
            holder.description.setText(productModel.getAddress());
            holder.price.setVisibility(View.GONE);


        }

        @Override
        public int getItemCount() {
            return productModels.size();
        }
    }
}
