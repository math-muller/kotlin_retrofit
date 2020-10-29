package br.com.fiap.roomdatabase

import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.roomdatabase.task.Task
import br.com.fiap.roomdatabase.task.TaskAdapter
import br.com.fiap.roomdatabase.task.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView

    private val taskAdapter = TaskAdapter()

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editText = findViewById(R.id.editTextTarefa)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = GridLayoutManager(this,2)
        setupEditText()
        observeLiveDatas()
    }

    private fun observeLiveDatas() {
        viewModel.tasksLiveData.observe(this, Observer {
            taskAdapter.updateItems(it)
        })

        viewModel.newTaskLiveData.observe(this, Observer {
            taskAdapter.addTask(it)
        })
    }

    private fun setupEditText() {
        editText.setOnEditorActionListener { textView, actionId, keyEvent ->

            when(actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    insertTask()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun insertTask() {
        val item = Task(description = editText.text.toString())

        viewModel.newTask(item)
    }

}