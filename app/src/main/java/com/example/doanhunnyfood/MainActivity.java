package com.example.doanhunnyfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanhunnyfood.entydi.Table;
import com.example.doanhunnyfood.ui.DinnerTable.DinnerTableFragment;
import com.example.doanhunnyfood.ui.DinnerTable.OnTableSelectedListener;
import com.example.doanhunnyfood.ui.Dish.DishFragment;
import com.example.doanhunnyfood.ui.gallery.GalleryFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doanhunnyfood.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements OnTableSelectedListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        TabLayout tabLayout = binding.appBarMain.navViewTab;


        // Thêm các tab với custom view
        for (int i = 0; i < 2; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(getTabView(i));
            tabLayout.addTab(tab);
        }

        // Đặt listener cho sự kiện chọn tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                // Thay đổi nội dung hiển thị dựa trên tab được chọn
                setTabContent(position);
                // Cập nhật trạng thái của tab để áp dụng màu sắc
                updateTabStates(tabLayout);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Không cần xử lý khi tab không được chọn
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                // Thay đổi nội dung hiển thị dựa trên tab được chọn
                setTabContent(position);
                // Cập nhật trạng thái của tab để áp dụng màu sắc
                updateTabStates(tabLayout);
            }
        });

        // Hiển thị nội dung của tab đầu tiên mặc định
        setTabContent(0);


        // Thiết lập NavigationView để điều hướng các Fragment tùy chỉnh
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment = null;
            if (id == R.id.nav_home) {
                fragment = new DinnerTableFragment();
                binding.appBarMain.toolbar.setTitle("Home");
            } else if (id == R.id.nav_gallery) {
                fragment = new GalleryFragment(); // Thay thế bằng Fragment của bạn
                binding.appBarMain.toolbar.setTitle("Gallery");
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
            drawer.closeDrawer(GravityCompat.END);
            return true;
        });
    }
    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView textView = view.findViewById(R.id.tab_title);
        ImageView imageView = view.findViewById(R.id.tab_icon);
        switch (position) {
            case 0:
                textView.setText("Chọn bàn");
                imageView.setImageResource(R.drawable.ic_launcher_background); // Thiết lập icon của bạn ở đây
                break;
            case 1:
                textView.setText("Nhà hàng");
                imageView.setImageResource(R.drawable.ic_launcher_background); // Thiết lập icon của bạn ở đây
                break;
        }
        return view;
    }

    private void setTabContent(int position) {
        Fragment fragment = new DinnerTableFragment();
        switch (position) {
            case 0:
                fragment = new DinnerTableFragment();
                break;
            case 1:
                DrawerLayout drawer = binding.drawerLayout;
                drawer.openDrawer(GravityCompat.END);
                return;
            default:
                fragment = new DinnerTableFragment();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void updateTabStates(TabLayout tabLayout) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null && tab.getCustomView() != null) {
                View view = tab.getCustomView();
                ImageView imageView = view.findViewById(R.id.tab_icon);
                TextView textView = view.findViewById(R.id.tab_title);
                boolean isSelected = tab.isSelected();
                imageView.setSelected(isSelected);
                textView.setSelected(isSelected);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        DrawerLayout drawer = binding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return super.onSupportNavigateUp();
        }
    }

    @Override
    public void onTableSelected(Table table) {
        DishFragment dishFragment = DishFragment.newInstance(table);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, dishFragment)
                .addToBackStack(null)
                .commit();

    }
}