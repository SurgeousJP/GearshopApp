package com.example.gearshop.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;

import com.example.gearshop.R;
import com.example.gearshop.activity.ProductDetailActivity;
import com.example.gearshop.model.Product;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import com.squareup.picasso.Picasso;

public class ProductGridAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;

    public ProductGridAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }
    public void setData(List<Product> products){
        this.products = products;
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        // Get UI components in xml file
        ImageView productImageView = convertView.findViewById(R.id.item_image_list_item);
        TextView productSellingPriceTextView = convertView.findViewById(R.id.selling_price);
        TextView productNameTextView = convertView.findViewById(R.id.label_product);

        // Set data to UI components
        Product product = products.get(position);

        productNameTextView.setText(product.getName());

        double price = product.getPrice();
        Locale locale = new Locale("vi", "VN");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setCurrencySymbol("đ");
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00 ¤", symbols);
        String formattedPrice  = decimalFormatter.format(price);
        productSellingPriceTextView.setText(String.valueOf(formattedPrice));

        String imageURL = product.getImageURL();
        Picasso.get()
                .load(imageURL)
                .into(productImageView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("clickedProduct", product);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

