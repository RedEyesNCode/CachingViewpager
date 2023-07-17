package com.redeyesncode.cachingviewpager.ui.activity

import android.media.metrics.Event
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.jakewharton.disklrucache.DiskLruCache
import com.redeyesncode.cachingviewpager.R
import com.redeyesncode.cachingviewpager.base.Constants
import com.redeyesncode.cachingviewpager.base.LoadingProgressDialog
import com.redeyesncode.cachingviewpager.base.getNetworkResponse
import com.redeyesncode.cachingviewpager.base.putNetworkResponse
import com.redeyesncode.cachingviewpager.caching.AndroidDiskCacheManager
import com.redeyesncode.cachingviewpager.caching.CacheKey
import com.redeyesncode.cachingviewpager.caching.CacheKeyGenerator
import com.redeyesncode.cachingviewpager.data.ResponseMarsPhotos
import com.redeyesncode.cachingviewpager.databinding.ActivityMainBinding
import com.redeyesncode.cachingviewpager.retrofit.AppEndPoint
import com.redeyesncode.cachingviewpager.retrofit.NetworkInstance
import com.redeyesncode.cachingviewpager.ui.adapters.PhotoPager
import com.redeyesncode.cachingviewpager.ui.repo.DashboardCalls
import com.redeyesncode.cachingviewpager.ui.repo.DashboardRepo
import com.redeyesncode.cachingviewpager.ui.viewmodel.DashboardViewModel
import com.redeyesncode.cachingviewpager.ui.viewmodel.ViewmodelProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel:DashboardViewModel
    private var loadingDialog: LoadingProgressDialog? = null
    private var isOnFirstPage: Boolean = true


    // Caching Implementation.
    lateinit var diskLruCacheInstance: DiskLruCache




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        attachObservers()
        refersherAtFirstPage()

        checkCachingOrCall()


    }

    private fun refersherAtFirstPage(){
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Update the flag indicating whether the user is on the first page
                isOnFirstPage = position == 0

                // Enable/disable SwipeRefreshLayout based on the page position
                binding.swipeRefresh.isEnabled = isOnFirstPage
            }
        })
        binding.swipeRefresh.setOnRefreshListener {

            viewModel.callMarsPhotoApi()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun checkCachingOrCall() {
        val androidCache = AndroidDiskCacheManager
        androidCache.initialize(this)
        diskLruCacheInstance = androidCache.getDiskLruCache()
        val cacheData = diskLruCacheInstance.getNetworkResponse<ArrayList<ResponseMarsPhotos>>(CacheKeyGenerator.generateCacheKey(CacheKey.CACHE_MARS_PHOTOS))
        if(cacheData==null){
            viewModel.callMarsPhotoApi()

        } else{
        // else we can dependent on user to use swipe-refresh only for getting the new data else it will be. used from cache.

            binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
            // Set up your ViewPager2 adapter and data here
            val adapter = PhotoPager(this@MainActivity,cacheData)
            binding.viewPager.adapter = adapter

        }

    }

    private fun attachObservers() {
        viewModel.marsPhotos.observe((this),com.redeyesncode.cachingviewpager.base.Event.EventObserver(
            onError = {
                      dismissLoadingDialog()
                showToast(it)
                Log.i(Constants.LOG_ASHU,it)
                Log.i(Constants.LOG_ASHU,it)
                Log.i(Constants.LOG_ASHU,it)
                Log.i(Constants.LOG_ASHU,it)
            },
        onLoading ={
                   showLoadingDialog()

            },
            onSuccess = {
                dismissLoadingDialog()
                binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
                // Set up your ViewPager2 adapter and data here
                val adapter = PhotoPager(this@MainActivity,it)
                binding.viewPager.adapter = adapter


                diskLruCacheInstance.putNetworkResponse(CacheKeyGenerator.generateCacheKey(CacheKey.CACHE_MARS_PHOTOS),it)




            }


        ))
    }

    private fun setupViewModel() {
        val dispatchers: CoroutineDispatcher = Dispatchers.Main
        val mainRepos = DashboardCalls() as DashboardRepo
        val appApi: AppEndPoint = NetworkInstance.api
        val viewModelProviderfactory =
            ViewmodelProvider(application, mainRepos, dispatchers, appApi)
        viewModel = ViewModelProvider(this,viewModelProviderfactory)[DashboardViewModel::class.java]


    }
    fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = LoadingProgressDialog(this)
        }
        loadingDialog?.show()
    }

    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
    fun showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()


    }
}