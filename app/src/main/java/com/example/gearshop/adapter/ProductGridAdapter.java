package com.example.gearshop.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.model.Product;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

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

        // Lấy các thành phần UI trong list_item.xml
        ImageView productImageView = convertView.findViewById(R.id.product_image);
        TextView productSellingPriceTextView = convertView.findViewById(R.id.selling_price);
        TextView productNameTextView = convertView.findViewById(R.id.label_product);

        // Thiết lập dữ liệu cho các thành phần UI
        Product product = products.get(position);
        productNameTextView.setText(product.getName());
        double price = product.getPrice();

        Locale locale = new Locale("vi", "VN"); // Locale của định dạng (Ví dụ: en-US cho tiền tệ Mỹ)
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setCurrencySymbol("đ"); // Ký hiệu tiền tệ
        symbols.setGroupingSeparator('.'); // Dấu ngăn cách hàng nghìn
        symbols.setDecimalSeparator(','); // Dấu thập phân

        DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00 ¤", symbols);

        String formattedPrice  = decimalFormatter.format(price);
        productSellingPriceTextView.setText(String.valueOf(formattedPrice));

        return convertView;
    }
}

