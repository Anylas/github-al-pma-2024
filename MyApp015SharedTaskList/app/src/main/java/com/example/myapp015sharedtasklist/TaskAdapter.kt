package com.example.myapp015sharedtasklist
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp015sharedtasklist.databinding.ItemTaskBinding
import com.example.myapp015sharedtasklist.Task

class TaskAdapter(
    private val tasks: List<Task>,
    private val onTaskChecked: (Task) -> Unit, // Callback pro změnu stavu úkolu
    private val onNameAssigned: (Task) -> Unit // Callback pro aktualizaci přezdívky
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            // Nastavení názvu úkolu
            binding.tvTaskName.text = task.name

            // Checkbox: dokončený úkol
            binding.cbTaskCompleted.isChecked = task.isCompleted
            binding.cbTaskCompleted.setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
                onTaskChecked(task) // Callback pro synchronizaci checkboxu
            }

            // Nastavení přezdívky (Assigned to)
            binding.tvAssignedTo.text = if (task.assignedTo.isNotEmpty()) {
                "Assigned to: ${task.assignedTo}"
            } else {
                "Assigned to: None"
            }

            // Kliknutí na "Assigned to" pro změnu přezdívky
            binding.tvAssignedTo.setOnClickListener {
                showAssignDialog(task)
            }
        }

        private fun showAssignDialog(task: Task) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Assign Task")

            // Vytvoření vstupního pole pro přezdívku
            val input = EditText(binding.root.context)
            input.hint = "Enter name or nickname"
            input.setText(task.assignedTo) // Předvyplní aktuální hodnotu
            builder.setView(input)

            // Dialog: tlačítka Uložit a Zrušit
            builder.setPositiveButton("Save") { _, _ ->
                task.assignedTo = input.text.toString()
                onNameAssigned(task) // Callback pro uložení změny
                notifyItemChanged(adapterPosition) // Aktualizace UI pro daný úkol
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}
