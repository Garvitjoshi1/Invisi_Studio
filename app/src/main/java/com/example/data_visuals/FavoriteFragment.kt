package com.example.data_visuals

import android.app.Activity
import android.content.Context
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
import java.io.File
import java.io.FileOutputStream

class FavoriteFragment : Fragment() {
    private val PICK_EXCEL_REQUEST_CODE = 101
    private lateinit var uploadedFiles: MutableList<Uri>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        uploadedFiles = mutableListOf()

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
                val fileName = getFileName(uri)
                val fileUri = saveFileLocally(requireContext(), uri, fileName)
                uploadedFiles.add(fileUri)
                displayUploadedFiles()
            }
        }
    }

    private fun displayUploadedFiles() {
        val filesLayout: ViewGroup = requireView().findViewById(R.id.filesLayout)
        filesLayout.removeAllViews()

        for (fileUri in uploadedFiles) {
            val fileName = File(fileUri.path).name
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

    private fun saveFileLocally(context: Context, uri: Uri, fileName: String): Uri {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputDir = File(context.filesDir, "uploaded_files")
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val outputFile = File(outputDir, fileName)
        val outputStream = FileOutputStream(outputFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return Uri.fromFile(outputFile)
    }
}