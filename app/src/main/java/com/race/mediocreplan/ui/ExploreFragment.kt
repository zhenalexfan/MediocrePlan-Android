package com.race.mediocreplan.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.race.mediocreplan.R
import com.race.mediocreplan.data.model.Task
import com.race.mediocreplan.presenter.ExplorePresenter


/**
 * A fragment representing a list of Items.
 */
class ExploreFragment : Fragment(), IExploreView {

    private val iPresenter = ExplorePresenter(this)

    // TODO: Customize parameters
    private var columnCount = 1

    private var taskAdapter: TaskRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                taskAdapter = TaskRecyclerViewAdapter()
                adapter = taskAdapter
                // TODO: Read items from local data
            }
        }
        iPresenter.getAllTasks()
        return view
    }

    override fun onTasksLoad(tasks: List<Task>?) {
        taskAdapter?.setItems(tasks)
        taskAdapter?.notifyDataSetChanged()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val TAG = "ExploreFragment"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                ExploreFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}