/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.raccoon.peruvende.ecomerce.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.raccoon.peruvende.R;
import com.raccoon.peruvende.core.CategoryModels;
import com.raccoon.peruvende.core.ColorModel;
import com.raccoon.peruvende.core.ScrollChildSwipeRefreshLayout;
import com.raccoon.peruvende.ecomerce.log.BaseModel;
import com.raccoon.peruvende.ecomerce.log.BussinesModel;
import com.raccoon.peruvende.ecomerce.log.ProductModel;
import com.raccoon.peruvende.ecomerce.log.ServiceFactory;
import com.raccoon.peruvende.ecomerce.log.UserRequest;
import com.raccoon.peruvende.ecomerce.product.ItemDetailsActivity;
import com.raccoon.peruvende.ecomerce.startup.MainActivity;
import com.raccoon.peruvende.ecomerce.utility.ImageUrlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImageListFragment extends Fragment {

    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    private static MainActivity mActivity;
    @BindView(R.id.swiped)
    ScrollChildSwipeRefreshLayout swiped;
    Unbinder unbinder;
    @BindView(R.id.filter1)
    MaterialSpinner filter1;
    @BindView(R.id.filter2)
    MaterialSpinner filter2;
    @BindView(R.id.filter3)
    MaterialSpinner filter3;
    @BindView(R.id.filter4)
    MaterialSpinner filter4;

    private ProgressDialog progressDialog;

    private ArrayList<CategoryModels> categoryModels;
    private ArrayList<ColorModel> colorModel;
    private ArrayList<CategoryModels> colorModelee;



    private String colorSelected = "";
    private String categorySelected = "";
    private String tall = "";
    private Map<String, String> filters ;
    SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Cargando..");
        filters =  new HashMap<>();
        filters.put("category","RSvqMJmGWd3XGwqXa");
    }


    @Override
    public void onResume() {
        super.onResume();

        loadProducts();
        loadCategories();
        loadColors();
    }

    public void loadProducts() {
        UserRequest loginService =
                ServiceFactory.createService(UserRequest.class);
        Call<BaseModel<ArrayList<ProductModel>>> call = loginService.getGarments(BussinesModel.token, BussinesModel.id,filters);

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


    public void loadCategories() {
        UserRequest loginService =
                ServiceFactory.createService(UserRequest.class);
        Call<BaseModel<ArrayList<CategoryModels>>> call = loginService.getCategories(BussinesModel.token, BussinesModel.id);

        call.enqueue(new Callback<BaseModel<ArrayList<CategoryModels>>>() {
            @Override
            public void onResponse(@NonNull Call<BaseModel<ArrayList<CategoryModels>>> call, @NonNull Response<BaseModel<ArrayList<CategoryModels>>> response) {

                if (response.isSuccessful()) {


                    categoryModels = response.body().getData();
                    colorModelee = new ArrayList<>();
                    ArrayList<String> strings = new ArrayList<>();
                    strings.add("Categorías");
                    for (int i = 0; i < categoryModels.size(); i++) {

                        if (categoryModels.get(i).getParent() != null) {

                            if (categoryModels.get(i).getParent().get_id()!= null){
                                if (categoryModels.get(i).getParent().get_id().equals("RSvqMJmGWd3XGwqXa")) {
                                    colorModelee.add(categoryModels.get(i));
                                    strings.add(categoryModels.get(i).getName());

                                }

                            }

                        }

                    }
                    filter2.setItems(strings);
                    Log.e("asdasdasds", response.body().toString());
                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseModel<ArrayList<CategoryModels>>> call, @NonNull Throwable t) {

            }
        });
    }

    public void loadColors() {
        UserRequest loginService =
                ServiceFactory.createService(UserRequest.class);
        Call<BaseModel<ArrayList<ColorModel>>> call = loginService.getColors(BussinesModel.token, BussinesModel.id);

        call.enqueue(new Callback<BaseModel<ArrayList<ColorModel>>>() {
            @Override
            public void onResponse(@NonNull Call<BaseModel<ArrayList<ColorModel>>> call, @NonNull Response<BaseModel<ArrayList<ColorModel>>> response) {

                if (response.isSuccessful()) {


                    colorModel = response.body().getData();
                    ArrayList<String> strings = new ArrayList<>();
                    strings.add("Color");
                    for (int i = 0; i < colorModel.size(); i++) {

                            strings.add(colorModel.get(i).getName());



                    }
                    filter3.setItems(strings);
                    Log.e("asdasdasds", response.body().toString());
                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseModel<ArrayList<ColorModel>>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.layout_mode_list, container, false);


        unbinder = ButterKnife.bind(this, rv);

        swiped.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.black),
                ContextCompat.getColor(getActivity(), R.color.dark_gray),
                ContextCompat.getColor(getActivity(), R.color.black)
        );
        RecyclerView recyclerView = rv.findViewById(R.id.recyclerview);
        setupRecyclerView(recyclerView);
        swiped.setScrollUpChild(recyclerView);
        swiped.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadProducts();
            }
        });
        filter1.setItems("Talla", "S", "M", "L", "XL", "XXL");
        filter1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {


                if (position!=0){
                    filters.put("size",item.toString());
                }else{
                    filters.put("size","");
                }

                loadProducts();

            }
        });

       filter2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if (position!=0){
                    filters.put("category",colorModelee.get(position-1).get_id());
                }else{
                    filters.put("category","RSvqMJmGWd3XGwqXa");
                }
                loadProducts();
            }
        });
        filter3.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if (position!=0){
                    filters.put("color",colorModel.get(position-1).getValue().substring(1));
                }else{
                    filters.put("color","");
                }

                loadProducts();
            }
        });

        return rv;
    }


    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }


        // Make sure setRefreshing() is called after the layout is done with everything else.
        swiped.post(new Runnable() {
            @Override
            public void run() {
                swiped.setRefreshing(active);
            }
        });


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ;

    }

    private void setupRecyclerView(RecyclerView recyclerView) {

        String[] items = null;
        if (ImageListFragment.this.getArguments().getInt("type") == 1) {
            items = ImageUrlUtils.getOffersUrls();
        } else if (ImageListFragment.this.getArguments().getInt("type") == 2) {
            items = ImageUrlUtils.getElectronicsUrls();
        } else if (ImageListFragment.this.getArguments().getInt("type") == 3) {
            items = ImageUrlUtils.getLifeStyleUrls();
        } else if (ImageListFragment.this.getArguments().getInt("type") == 4) {
            items = ImageUrlUtils.getHomeApplianceUrls();
        } else if (ImageListFragment.this.getArguments().getInt("type") == 5) {
            items = ImageUrlUtils.getBooksUrls();
        } else {
            items = ImageUrlUtils.getImageUrls();
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(recyclerView, new ArrayList<ProductModel>());
        recyclerView.setAdapter(simpleStringRecyclerViewAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private String[] mValues;
        private RecyclerView mRecyclerView;
        private ArrayList<ProductModel> productModels;

        public void setItems(ArrayList<ProductModel> productModels) {

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
            public final TextView desc;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.image1);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
                name = (TextView) view.findViewById(R.id.name);
                description = (TextView) view.findViewById(R.id.description);
                price = (TextView) view.findViewById(R.id.price);
                desc = (TextView) view.findViewById(R.id.desc);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<ProductModel> productModels) {
            this.productModels = productModels;
            this.mRecyclerView = recyclerView;
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, String[] items) {
            mValues = items;
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            /*if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }*/
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final ProductModel productModel = productModels.get(position);


           /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                layoutParams.height = 200;
            } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                layoutParams.height = 600;
            } else {
                layoutParams.height = 800;
            }*/

            if (productModel.getPhotos() != null) {
                if (productModel.getPhotos().size() > 0) {
                    // final Uri uri = Uri.parse(productModel.getPhotos().get(0).getImageUrl());
                    Glide.with(holder.mImageView.getContext()).load(productModel.getPhotos().get(0).getImageUrl()).into(holder.mImageView);
                }

            }


            holder.name.setText(productModel.getName());
            holder.description.setText(productModel.getDescription());
            holder.price.setText("S/. " + productModel.getPrice());
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ItemDetailsActivity.class);
                    intent.putExtra("product", productModel);
                    //intent.putExtra(STRING_IMAGE_POSITION, position);
                    mActivity.startActivity(intent);

                }
            });

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    /// imageUrlUtils.addWishlistImageUri(mValues[position]);
                    holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp);
                    notifyDataSetChanged();
                    Toast.makeText(mActivity, "Item added to wishlist.", Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return productModels.size();
        }
    }
}
