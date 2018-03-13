package com.raccoon.peruvende.ecomerce.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.raccoon.peruvende.R;
import com.raccoon.peruvende.ecomerce.fragments.ImageListFragment;
import com.raccoon.peruvende.ecomerce.fragments.ViewPagerActivity;
import com.raccoon.peruvende.ecomerce.log.ProductModel;
import com.raccoon.peruvende.ecomerce.notification.NotificationCountSetClass;
import com.raccoon.peruvende.ecomerce.options.CartListActivity;
import com.raccoon.peruvende.ecomerce.startup.MainActivity;
import com.raccoon.peruvende.ecomerce.utility.ImageUrlUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;

public class ItemDetailsActivity extends AppCompatActivity {
    int imagePosition;
    String stringImageUri;
    @BindView(R.id.image1)
    SimpleDraweeView image1;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.as_vp_promotions)
    AutoScrollViewPager asVpPromotions;
    @BindView(R.id.ci_promotions)
    CircleIndicator ciPromotions;

    private ProductModel productModel;
    private PromotionsAdapter mPromotionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ButterKnife.bind(this);

        SimpleDraweeView mImageView = (SimpleDraweeView) findViewById(R.id.image1);
        TextView textViewAddToCart = (TextView) findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = (TextView) findViewById(R.id.text_action_bottom2);
        if (getIntent() != null) {
            stringImageUri = getIntent().getStringExtra(ImageListFragment.STRING_IMAGE_URI);
            imagePosition = getIntent().getIntExtra(ImageListFragment.STRING_IMAGE_URI, 0);
            productModel = (ProductModel) getIntent().getSerializableExtra("product");

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(productModel.getName());

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        //Getting image uri from previous screen


        name.setText(productModel.getName());
        description.setText(productModel.getDescription());

        double total = (productModel.getPrice() - productModel.getPrice()*(productModel.getDiscount()/100));
        price.setText("S/ " + total);

        Uri uri = Uri.parse(productModel.getPhotos().get(0).getImageUrl());
        mImageView.setImageURI(uri);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailsActivity.this, ViewPagerActivity.class);
                intent.putExtra("position", imagePosition);
                startActivity(intent);

            }
        });


        mPromotionsAdapter = new PromotionsAdapter(this, productModel.getPhotos());
        asVpPromotions.setAdapter(mPromotionsAdapter);
        ciPromotions.setViewPager(asVpPromotions);
        asVpPromotions.startAutoScroll(2000);
        asVpPromotions.setInterval(2000);
        asVpPromotions.setScrollDurationFactor(1);
        asVpPromotions.setBorderAnimation(true);


        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addCartListImageUri(productModel);
                Toast.makeText(ItemDetailsActivity.this, "Se añadió al carrito", Toast.LENGTH_SHORT).show();
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addCartListImageUri(stringImageUri);
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));*/

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
