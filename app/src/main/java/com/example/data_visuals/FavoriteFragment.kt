package com.example.data_visuals

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FavoriteFragment : Fragment() {private val PICK_EXCEL_REQUEST_CODE = 101
    private val uploadedFiles = mutableListOf<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val uploadExcelButton: Button = view.findViewById(R.id.uploadExcelButton)
        uploadExcelButton.setOnClickListener {
            openFilePicker()
        }

        return view
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/vnd.ms-excel"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Excel File"), PICK_EXCEL_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_EXCEL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                uploadedFiles.add(uri)
                displayUploadedFiles()
            }
        }
    }

    private fun displayUploadedFiles() {
        val filesLayout: ViewGroup = requireView().findViewById(R.id.filesLayout)
        filesLayout.removeAllViews()

        for (fileUri in uploadedFiles) {
            val fileName = getFileName(fileUri)
            val textView = TextView(requireContext()).apply {
                text = fileName
                setOnClickListener {
                    // Open the file for preview
                    openFilePreview(fileUri)
                }
            }
            filesLayout.addView(textView)
        }
    }

    private fun getFileName(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val fileNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                return it.getString(fileNameIndex)
            }
        }
        return "Unknown File"
    }

    private fun openFilePreview(uri: Uri) {
        // Implement file preview logic here
    }
}
