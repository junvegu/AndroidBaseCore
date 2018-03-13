package com.raccoon.peruvende.ecomerce.product;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.raccoon.peruvende.R;
import com.raccoon.peruvende.ecomerce.log.ImageModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromotionsAdapter extends PagerAdapter {


    private ArrayList<ImageModel> mPromotionEntities;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    @BindView(R.id.iv_slide)
    ImageView ivSlide;
    public  PromotionsAdapter(Context context, ArrayList<ImageModel> promotionEntities) {
        this.mContext = context;
        this.mPromotionEntities = promotionEntities;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public Object instantiateItem(final ViewGroup collection, final int position) {
       // String stepTitle = mPromotionEntities.get(position).getTitle();
        //String stepDescription = mPromotionEntities.get(position).getSubtitle();
        //int stepImage = mPromotionEntities.get(position).getPicture();

        final View view = mLayoutInflater.inflate(R.layout.item_promotions_step_pager, collection, false);
        ButterKnife.bind(this, view);

        Glide.with(mContext).load(mPromotionEntities.get(position).getImageUrl()).into(ivSlide);

        //ivSlide.setImageResource(stepImage);

        collection.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mPromotionEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void destroyItem(ViewGroup collection, int position,
                            Object view) {
        collection.removeView((View) view);
    }

}
