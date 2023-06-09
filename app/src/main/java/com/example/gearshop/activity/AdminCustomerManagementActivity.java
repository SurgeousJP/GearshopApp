package com.example.gearshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.adapter.CustomerListAdapter;
import com.example.gearshop.fragment.CustomerSortBottomSheetDialogFragment;
import com.example.gearshop.model.Customer;
import com.example.gearshop.utility.ActivityStartManager;
import com.example.gearshop.utility.DatabaseHelper;
import com.example.gearshop.utility.VietnameseStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdminCustomerManagementActivity extends AppCompatActivity {
    private View CustomerSearchIcon;
    private View CustomerFilterIcon;
    private View CustomerSortIcon;
    private EditText CustomerSearchText;
    private TextView CustomerFilterText;
    private TextView CustomerSortText;
    private View TransitionToProductManagementView;
    private View TransitionToOrderManagementView;
    private View TransitionToAccountManagementView;
    private ListView CustomerManagementListView;
    private List<Customer> CustomerList;
    private CustomerListAdapter CustomerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_customer_mainsceen_management);

        CustomerManagementListView = findViewById(R.id.listview_customer_management);
        CustomerList = DatabaseHelper.getCustomerList("ALL");
        CustomerAdapter = new CustomerListAdapter(
                getBaseContext(),
                R.layout.admin_customer_list_item,
                CustomerList);
        CustomerManagementListView.setAdapter(CustomerAdapter);
        CustomerAdapter.UpdateDataToAdapter(CustomerList);

        CustomerSearchText = findViewById(R.id.customer_management_search_text);
        CustomerSearchIcon = findViewById(R.id.customer_management_search_icon);
        View.OnClickListener searchOnClickListener = view -> {
            String searchText = CustomerSearchText.getText().toString();
            List<Customer> searchResults = searchForCustomers(searchText);
            CustomerAdapter.UpdateDataToAdapter(searchResults);
        };
        CustomerSearchIcon.setOnClickListener(searchOnClickListener);

        CustomerFilterText = findViewById(R.id.customer_management_label_filter);
        CustomerFilterIcon = findViewById(R.id.customer_management_filter_icon);

        View.OnClickListener sortCustomerListener = view -> {
            CustomerSortBottomSheetDialogFragment dialogFragment = new CustomerSortBottomSheetDialogFragment(
                    CustomerAdapter, CustomerList);
            dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
        };
        CustomerSortIcon = findViewById(R.id.customer_management_sort_icon);
        CustomerSortIcon.setOnClickListener(sortCustomerListener);
        CustomerSortText = findViewById(R.id.customer_management_sort_text);
        CustomerSortText.setOnClickListener(sortCustomerListener);
        TransitionToProductManagementView = findViewById(R.id.product_manage_customer);
        TransitionToProductManagementView.setOnClickListener(view -> {

        });
        TransitionToAccountManagementView = findViewById(R.id.account_manage_customer);
        TransitionToAccountManagementView.setOnClickListener(view -> {

        });
        TransitionToOrderManagementView = findViewById(R.id.order_manage_customer);
        TransitionToOrderManagementView.setOnClickListener(view -> {
            ActivityStartManager.startTargetActivity(getBaseContext(), AdminOrderManagementActivity.class);
        });

    }

    protected List<Customer> searchForCustomers(String searchText){
        List<Customer> result = new ArrayList<>();
        for (int i = 0; i < CustomerList.size(); i++){
            if (checkCustomerContainsInformation(CustomerList.get(i), searchText)){
                result.add(CustomerList.get(i));
            }
        }
        return result;
    }
    protected boolean checkCustomerContainsInformation(Customer customer, String info){
        if (info.isEmpty())
            return false;
        String customerFirstNamePlainString = VietnameseStringConverter.
                convertToPlainString(customer.getFirstName().toLowerCase(Locale.ROOT));
        String customerLastNamePlainString = VietnameseStringConverter.
                convertToPlainString(customer.getLastName().toLowerCase(Locale.ROOT));
        if (VietnameseStringConverter.convertToPlainString(info).equals(info.toLowerCase(Locale.ROOT))){
            System.out.println("1");
            return customerFirstNamePlainString.contains(info.toLowerCase(Locale.ROOT))
                    || customerLastNamePlainString.contains(info.toLowerCase(Locale.ROOT));

        }
        System.out.println("2");
        return customer.getFirstName().toLowerCase(Locale.ROOT).contains(info.toLowerCase(Locale.ROOT))
                || customer.getLastName().toLowerCase(Locale.ROOT).contains(info.toLowerCase(Locale.ROOT));
    }
}