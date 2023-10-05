package com.sparkles.sparkles_landing.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.sparkles.sparkles_landing.DetailsDataActivity;
import com.sparkles.sparkles_landing.R;
import com.sparkles.sparkles_landing.adapters.HomeViewAdapter;
import com.sparkles.sparkles_landing.adapters.ImagePagerAdapter;
import com.sparkles.sparkles_landing.databinding.FragmentHomeBinding;
import com.sparkles.sparkles_landing.models.ApiResponseModel;
import com.sparkles.sparkles_landing.models.HomeModel;
import com.sparkles.sparkles_landing.retrofit.ApiService;
import com.sparkles.sparkles_landing.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements HomeViewAdapter.OnItemClickListener{

    private final String TAG_NAME = "HomeFragment";
    private FragmentHomeBinding binding;

    private RecyclerView recyclerView;
    private HomeViewAdapter homeViewAdapter;
    private ViewPager viewPager;
    GridLayoutManager gridLayoutManager;
    private ImagePagerAdapter imagePagerAdapter;
    private int currentPage = 0;
    private static final long AUTO_SLIDE_DELAY = 3000; // Auto slide delay in milliseconds
    private final Handler autoSlideHandler = new Handler(Looper.getMainLooper());

    private final Runnable autoSlideRunnable = new Runnable() {
        @Override
        public void run() {
            if (imagePagerAdapter != null) {
                if (currentPage == imagePagerAdapter.getCount() - 1) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                viewPager.setCurrentItem(currentPage, true);
                autoSlideHandler.postDelayed(this, AUTO_SLIDE_DELAY);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager = root.findViewById(R.id.viewPager2);
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.slider_image);
        imageList.add(R.drawable.slide0);
        imageList.add(R.drawable.slide1);
        imageList.add(R.drawable.slide2);


        recyclerView = root.findViewById(R.id.recyclerView);
        // Initialize the adapter with your data
        gridLayoutManager = new GridLayoutManager(getActivity(),3, RecyclerView.VERTICAL,false);
        homeViewAdapter = new HomeViewAdapter(getActivity(),new ArrayList<>());
        homeViewAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(homeViewAdapter);

        imagePagerAdapter = new ImagePagerAdapter(getActivity(),imageList);
        viewPager.setAdapter(imagePagerAdapter);

        // Start auto-sliding when the fragment is created
        autoSlideHandler.postDelayed(autoSlideRunnable, AUTO_SLIDE_DELAY);



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Make the API request and populate the adapter with data
        fetchServiceData();
    }

    private void fetchServiceData() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ApiResponseModel> call = apiService.getData();

        call.enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                if (response.isSuccessful()) {
                    ApiResponseModel apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("Success")) {
                        List<HomeModel> apiHomeItems = apiResponse.getData();
                        Log.d(TAG_NAME, "list List: " + apiHomeItems);
                        if (apiHomeItems != null && !apiHomeItems.isEmpty()) {
                            // Update the list with the API data
                            homeViewAdapter.setHomeViewList(apiHomeItems);
                        }
                    } else {
                        Log.e(TAG_NAME, "API Response Error: " + apiResponse.getMessage());
                    }
                } else {
                    Log.e(TAG_NAME, "API Request Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                Log.e(TAG_NAME, "API Request Failure: " + t.getMessage());
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(HomeModel homeModel) {
        // Handle item click here, e.g., navigate to a new activity/fragment and pass the clicked item's details
        Intent intent = new Intent(getActivity(), DetailsDataActivity.class);
        intent.putExtra("homeModel", homeModel); // Pass the HomeModel object to the next activity
        startActivity(intent);
    }
}