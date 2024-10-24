package com.example.myapp009asharedpreferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

class ListFragment : Fragment() {

    private lateinit var listView: ListView
    private val books = listOf(
        "Záhadná obět" to "Robert Brynza",
        "Hospůdka v Praze" to "Julie Caplinová",
        "Čarodějky" to "Karin Krajčo Babinská"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        listView = view.findViewById(R.id.listViewBooks)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            books.map { it.first }
        )
        listView.adapter = adapter

        // Při kliknutí na položku zavoláme metodu aktivity
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedBook = books[position]
            (activity as? MainActivity)?.onBookSelected(selectedBook.first, selectedBook.second)
        }
        return view
    }
}