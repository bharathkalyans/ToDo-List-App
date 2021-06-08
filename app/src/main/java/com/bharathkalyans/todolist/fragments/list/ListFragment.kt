package com.bharathkalyans.todolist.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bharathkalyans.todolist.R
import com.bharathkalyans.todolist.data.viewmodel.ToDoViewModel
import com.bharathkalyans.todolist.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, { listOfToDoData ->
            mSharedViewModel.checkIfDatabaseIsEmpty(listOfToDoData)
            adapter.setData(listOfToDoData)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, {
            showEmptyDatabaseViews(it)
        })

        view.floatingActionButton2.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }


        //Setting Menu
        setHasOptionsMenu(true)
        return view
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {

        if (emptyDatabase) {
            view?.no_data_image_view?.visibility = View.VISIBLE
            view?.no_data_text_view?.visibility = View.VISIBLE
        } else {
            view?.no_data_image_view?.visibility = View.INVISIBLE
            view?.no_data_text_view?.visibility = View.INVISIBLE
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.menu_about -> findNavController().navigate(R.id.aboutFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    //Confirming to remove All the Data!!
    private fun confirmRemoval() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAllData()
            Toast.makeText(
                requireContext(),
                "Successfully Removed  Everything!",
                Toast.LENGTH_SHORT
            ).show()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to delete all the Data?")
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}