package com.example.data_visuals

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeFragment : Fragment() {
    private lateinit var lineChart: LineChart
    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize Line Chart
        lineChart = view.findViewById(R.id.lineChart)
        setupLineChart()

        // Initialize Bar Chart
        barChart = view.findViewById(R.id.barChart)
        setupBarChart()

        // Initialize Pie Chart
        pieChart = view.findViewById(R.id.pieChart)
        setupPieChart()

        return view
    }

    private fun setupLineChart() {
        val entries = mutableListOf<Entry>()
        for (i in 0 until 10) {
            entries.add(Entry(i.toFloat(), (Math.random() * 100).toFloat()))
        }

        val dataSet = LineDataSet(entries, "Line Chart")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        lineChart.invalidate()
    }

    private fun setupBarChart() {
        val entries = mutableListOf<BarEntry>()
        for (i in 0 until 10) {
            entries.add(BarEntry(i.toFloat(), (Math.random() * 100).toFloat()))
        }

        val dataSet = BarDataSet(entries, "Bar Chart")
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS.toList()) // Convert array to list
        dataSet.valueTextColor = Color.BLACK

        val barData = BarData(dataSet)
        barChart.data = barData

        barChart.invalidate()
    }

    private fun setupPieChart() {
        // Set up Pie Chart
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(android.R.color.transparent)
        pieChart.setTransparentCircleRadius(61f)
        pieChart.centerText = "Pie Chart"
        pieChart.setCenterTextSize(18f)

        val entries = mutableListOf<PieEntry>()
        for (i in 0 until 4) {
            entries.add(PieEntry((Math.random() * 100).toFloat(), "Entry $i"))
        }

        val dataSet = PieDataSet(entries, "Pie Chart")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextColor = Color.BLACK

        val pieData = PieData(dataSet)
        pieChart.data = pieData

        pieChart.invalidate()
    }
}
