package com.unorg.notesapp.views

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.unorg.notesapp.R
import com.unorg.notesapp.adapter.ColorsAdapter
import com.unorg.notesapp.databinding.ActivityNotesBinding
import com.unorg.notesapp.reponsehandeler.NoteHandler
import com.unorg.notesapp.roomdatabase.NoteTable
import com.unorg.notesapp.utils.getColorList
import com.unorg.notesapp.utils.showLogs
import com.unorg.notesapp.utils.showToast
import com.unorg.notesapp.viewmodel.MyNotesViewModel
import com.unorg.notesapp.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotesBinding

    lateinit var adapter: ColorsAdapter
    val viewModel: ViewModel by viewModels()
    val myNotesViewModel: MyNotesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes)

        val id = intent?.getIntExtra("id", 0)
        val title = intent?.getStringExtra("title")
        val mainNote = intent?.getStringExtra("mainNote")
        val colors = intent?.getStringExtra("color")

        if (title != null && mainNote != null && colors != null) {
            insert = false
            update = true
            notesId = id!!
            setColor = colors!!
            binding.title.setText(title)
            binding.notes.setText(mainNote)
            val colorString = Color.parseColor(colors)
            binding.mainLayout.setBackgroundColor(colorString)
        } else {
            insert = true
            update = false
            notesId = -1
            setColor = null
        }
        adapter = ColorsAdapter(this, -1) { it ->
            showLogs("color", "$it")
            val colorString = Color.parseColor(it)
            binding.mainLayout.setBackgroundColor(colorString)
            color = it
        }
        binding.saveNotes.setOnClickListener {
            lifecycleScope.launch {



                if (insert) {
                    if (binding.title.text.isNullOrEmpty()) {
                        showToast("Please Add Notes Title")
                    } else if (binding.notes.text.isNullOrEmpty()) {
                        showToast("Please Add Notes Content")
                    } else if (color.isNullOrEmpty()) {
                        showToast("Please Choose Color")
                    }
                    else{
                        val result = viewModel.insertNotes(
                            NoteTable(
                                title = binding.title.text.toString(),
                                mainNote = binding.notes.text.toString(),
                                color = color
                            )
                        )
                        result.flowOn(Dispatchers.IO).collect {
                            when (it) {
                                is NoteHandler.Error -> showToast("${it.getMsg}")
                                is NoteHandler.Success -> {
                                    if (it.getData > -1) {
                                        showToast("Notes Saved Successfully")
                                        finish()
                                    }
                                }
                            }
                        }
                    }

                }
                else {

                    if (notesId != -1) {
                        if (!binding.title.text.toString().trim().isNullOrEmpty() &&
                            !binding.notes.text.toString().trim().isNullOrEmpty() &&
                            !setColor.isNullOrEmpty()
                        ) {
                            val updateResult = myNotesViewModel.updateNotes(
                                NoteTable(
                                    id = notesId,
                                    title = binding.title.text.toString(),
                                    mainNote = binding.notes.text.toString(),
                                    color = setColor
                                )
                            )
                            updateResult.flowOn(Dispatchers.IO).collect {
                                when (it) {
                                    is NoteHandler.Error -> showToast("${it.getMsg}")
                                    is NoteHandler.Success -> {
                                        if (it.getData > -1) {
                                            finish()
                                        }
                                    }
                                }
                            }
                        }
                        else if (!binding.title.text.toString().trim().isNullOrEmpty() &&
                            !binding.notes.text.toString().trim().isNullOrEmpty() &&
                            setColor.isNullOrEmpty()) {
                            val updateResult = myNotesViewModel.updateNotes(
                                NoteTable(
                                    id = notesId,
                                    title = binding.title.text.toString(),
                                    mainNote = binding.notes.text.toString(),
                                    color = color
                                )
                            )
                            updateResult.flowOn(Dispatchers.IO).collect {
                                when (it) {
                                    is NoteHandler.Error -> showToast("${it.getMsg}")
                                    is NoteHandler.Success -> {
                                        if (it.getData > -1) {
                                            finish()
                                        }
                                    }
                                }
                            }
                        }
                        else if (binding.title.text.isNullOrEmpty()) {
                            showToast("Please Add Notes Title")
                        } else if (binding.notes.text.isNullOrEmpty()) {
                            showToast("Please Add Notes Content")
                        }

                    } else {
                        showToast("Error while Updating")
                    }
                }

            }
        }
        binding.colorRecyclerView.adapter = adapter

        adapter.submitList(getColorList())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        color = null
        insert = false
        update = false
        setColor = null
    }

    companion object {
        var color: String? = null
        var insert: Boolean = false
        var update: Boolean = false
        var notesId: Int = -1
        var setColor: String? = null
    }
}