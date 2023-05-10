package com.example.dailynews.presentation.fragments.newslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailynews.R
import com.example.dailynews.databinding.FragmentNewsListBinding
import com.example.dailynews.presentation.models.CategoryItem
import com.example.dailynews.presentation.models.NewsItem
import com.xwray.groupie.GroupieAdapter


class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private lateinit var binding: FragmentNewsListBinding
    private lateinit var newsListViewModel: NewsListViewModel

    // adapters for recyclerViews
    private var newsListAdapter = GroupieAdapter()
    private var newsCategoryAdapter = GroupieAdapter()

    //setting start category
    private var currentCategory = "technology"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // creating binding + VM instance
        binding = FragmentNewsListBinding.bind(view)
        newsListViewModel = ViewModelProvider(this)[NewsListViewModel::class.java]

        //setting up recycler views for news and categories
        setupNewsRecyclerView()
        setupCategoryRecyclerView()

        //refresh listener
        binding.swipe.setOnRefreshListener {
            newsListAdapter.clear()
            newsListViewModel.getNewsData(currentCategory)
        }

        //observing loading liveData
        newsListViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = it
            if (binding.swipe.isRefreshing) {
                binding.logoIv.visibility = View.VISIBLE
            }
            else {
                binding.logoIv.visibility = View.GONE
            }
        }
        //observing news liveData
        newsListViewModel.newsLiveData.observe(viewLifecycleOwner) { news ->
            news.forEach {
                newsListAdapter.add(NewsItem(it))
            }
            binding.rvCategoryList.isClickable = true
        }
        //observing categories liveData
        newsListViewModel.categoryLiveData.observe(viewLifecycleOwner) { list ->
            list.forEach { category ->
                newsCategoryAdapter.add(CategoryItem(category))
            }
        }
    }

    private fun setupNewsRecyclerView() {
        binding.rvNewsList.adapter = newsListAdapter
        binding.rvNewsList.layoutManager = LinearLayoutManager(requireContext())

        newsListAdapter.setOnItemClickListener { item, _ ->
            val newsItem = item as NewsItem
            val direction =
                NewsListFragmentDirections.actionNewsListFragmentToNewsPageFragment(newsItem.news)

            findNavController().navigate(direction, navOptions {
                anim {
                    enter = R.anim.from_right
                    exit = R.anim.to_left
                    popEnter = R.anim.from_left
                    popExit = R.anim.to_right
                }
            })
            newsListAdapter.clear()
            newsCategoryAdapter.clear()
        }
    }

    private fun setupCategoryRecyclerView() {
        binding.rvCategoryList.adapter = newsCategoryAdapter
        binding.rvCategoryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        newsCategoryAdapter.setOnItemClickListener { item, _ ->
            if(binding.rvCategoryList.isClickable) {
                binding.rvCategoryList.isClickable = false
                newsListAdapter.clear()
                val categoryItem = item as CategoryItem
                currentCategory = categoryItem.category
                newsListViewModel.getNewsData(currentCategory)
            }
        }
    }
}