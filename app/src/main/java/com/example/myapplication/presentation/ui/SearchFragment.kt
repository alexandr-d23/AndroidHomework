package com.example.myapplication.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.domain.FindCityUseCase
import com.example.myapplication.domain.LocationUseCase
import com.example.myapplication.presentation.recyclerview.City
import com.example.myapplication.presentation.recyclerview.CityAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : MvpAppCompatFragment(), SearchView {

    @Inject
    lateinit var findCityUseCase: FindCityUseCase

    @Inject
    lateinit var locationUseCase: LocationUseCase

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    fun providePresenter() =
        SearchPresenter(findCityUseCase, locationUseCase, requireContext().applicationContext)


    private var _binding: FragmentSearchBinding? = null
    private val binding get(): FragmentSearchBinding = _binding!!
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var adapter: CityAdapter
    private lateinit var startingCityFragment: StartingCityFragment

    interface StartingCityFragment {
        fun userSearchedCity(cityId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startingCityFragment = context as StartingCityFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = CityAdapter {
            presenter.onRecyclerItemClick(it)
        }
        binding.rvNearestCities.adapter = adapter
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            rvNearestCities.adapter = adapter
            rvNearestCities.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            swipeRefreshLayout.setOnRefreshListener {
                presenter.onSwipeRefreshListener()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        searchView =
            menu.findItem(R.id.action_search)?.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                presenter.searchTextSubmitted(p0 ?: "")
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_GEO) {
            grantResults.forEach {
                var areGranted = true
                if (it == PackageManager.PERMISSION_DENIED) {
                    areGranted = false
                }
                if (areGranted) presenter.permissionsGranted()
                else presenter.permissionsDenied()
            }
        }
    }

    override fun checkLocationPermission() {
        presenter.onCheckPermissionResult(
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    override fun startDetailInformationFragment(id: Int) {
        startingCityFragment.userSearchedCity(id)
    }

    override fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
        (activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            binding.root.windowToken,
            0
        )
    }

    override fun showCityList(list: List<City>) {
        adapter.submitList(list.toMutableList())
    }

    override fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_GEO
        )
    }

    companion object {
        private const val PERMISSION_GEO = 999
        const val CITY_ID = "CITY_ID_KEY"

        fun newInstance(): SearchFragment {
            val args = Bundle()
            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
