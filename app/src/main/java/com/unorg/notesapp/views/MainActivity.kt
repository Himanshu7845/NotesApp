package com.unorg.notesapp.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.unorg.notesapp.R
import com.unorg.notesapp.adapter.NotesAdapter
import com.unorg.notesapp.databinding.ActivityMainBinding
import com.unorg.notesapp.reponsehandeler.NoteHandler
import com.unorg.notesapp.roomdatabase.NoteTable
import com.unorg.notesapp.utils.showLogs
import com.unorg.notesapp.utils.showToast
import com.unorg.notesapp.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val viewModel: ViewModel by viewModels()
    lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setData = viewModel
        notesAdapter = NotesAdapter(this, {
            deleteNotes(it)
        }, {
            updateNotes(it)
        })
        binding.notesRecyclerView.adapter = notesAdapter
        binding.search.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().trim()!=null) {
                    showLogs("oinpout", s.toString().trim())
                    getSearchNotes(s.toString().trim().lowercase())
                } else {
                    showLogs("oinpout", s.toString().trim())
                    getNotes()
                }
            }

        })
    }
    fun updateNotes(it: NoteTable) {
        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("id", it.id)
        intent.putExtra("title", it.title)
        intent.putExtra("mainNote", it.mainNote)
        intent.putExtra("color", it.color)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        getNotes()
    }

    fun getNotes() {
        lifecycleScope.launch {
            viewModel.result.collect {
                when (it) {
                    is NoteHandler.Error -> showToast("${it.getMsg}")
                    is NoteHandler.Success -> {
                        val list = ArrayList<NoteTable>()
                        if (it.getData.isNotEmpty()) {
                            binding.notesRecyclerView.visibility = View.VISIBLE
                            binding.empty.visibility = View.GONE
                            binding.noData.visibility = View.GONE
                            for (i in 0 until it.getData.size) {
                                list.add(
                                    NoteTable(
                                        id = it.getData[i].id,
                                        mainNote = it.getData[i].mainNote,
                                        color = it.getData[i].color,
                                        title = it.getData[i].title
                                    )
                                )
                            }
                            notesAdapter.submitList(list)
                        } else {
                            binding.notesRecyclerView.visibility = View.GONE
                            binding.empty.visibility = View.VISIBLE
                            binding.noData.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    fun getSearchNotes(string: String) {
        lifecycleScope.launch {
            viewModel.result.collect {
                when (it) {
                    is NoteHandler.Error -> showToast("${it.getMsg}")
                    is NoteHandler.Success -> {
                        val list = ArrayList<NoteTable>()
                        if (it.getData.isNotEmpty()) {
                            binding.notesRecyclerView.visibility = View.VISIBLE
                            binding.empty.visibility = View.GONE
                            binding.noData.visibility = View.GONE
                            val listData= it.getData.filter {
                               it.title?.lowercase()!!.contains(string)
                            }
                            list.addAll(listData)
                            notesAdapter.submitList(list)
                        } else {
                            binding.notesRecyclerView.visibility = View.GONE
                            binding.empty.visibility = View.VISIBLE
                            binding.noData.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    fun deleteNotes(it: NoteTable) {
        lifecycleScope.launch {
            val result = viewModel.deleteNotes(
                NoteTable(
                    id = it.id,
                    title = it.title,
                    mainNote = it.mainNote,
                    color = it.color
                )
            )
            result.flowOn(Dispatchers.IO).collect {
                when (it) {
                    is NoteHandler.Error -> showToast("${it.getMsg}")
                    is NoteHandler.Success -> {
                        if (it.getData > -1) {
                            getNotes()
                        }
                    }
                }
            }
        }
    }
}