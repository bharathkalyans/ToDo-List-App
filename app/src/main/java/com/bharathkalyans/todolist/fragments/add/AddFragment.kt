package com.bharathkalyans.todolist.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bharathkalyans.todolist.R
import com.bharathkalyans.todolist.data.models.Priority
import com.bharathkalyans.todolist.data.models.ToDoData
import com.bharathkalyans.todolist.data.viewmodel.ToDoViewModel
import com.bharathkalyans.todolist.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*


class AddFragment : Fragment() {

    //View Model
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    //This method will be responsible inserting data into the Database!
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_add) {
            insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {

        val mTitle = title_et.text.toString()
        val mPriority = priorities_spinner.selectedItem.toString()
        val mDescription = description_et.text.toString()

        val validation = mSharedViewModel.inputVerifyFromUser(mTitle, mDescription)

        if (validation) {
            //Inserting data to the Database as Input  user passes the safe check!
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Added!", Toast.LENGTH_SHORT).show()
            //Navigating back to the List Fragment
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Fill Out All Fields!", Toast.LENGTH_SHORT).show()
        }

    }


}