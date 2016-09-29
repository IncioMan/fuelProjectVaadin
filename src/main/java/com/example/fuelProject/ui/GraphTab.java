package com.example.fuelProject.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import org.atmosphere.cpr.Action.TYPE;

import com.example.fuelProject.data.FuelData;
import com.example.fuelProject.data.MonthConverter;
import com.google.gwt.user.cellview.client.Column;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Background;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.addon.charts.themes.ValoLightTheme;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

public class GraphTab extends VerticalLayout{
	private Component graph = null;
	private HorizontalLayout layout = null;
	private GraphLayout graphLayout = null;
	private Button viewPieChart = new Button("Pie Chart");
	private Button viewColoumnChart = new Button("Coloumn Chart");
	private static Color[] colors = new ValoLightTheme().getColors();
	private static Random rand = new Random(0);

	public GraphTab() {
		addComponent(getGraphLayout());
		addComponent(viewPieChart);
		setComponentAlignment(viewPieChart,  Alignment.MIDDLE_CENTER);

		
		viewPieChart.addClickListener(l -> {
			removeAllComponents();
			addComponent(getPieChart());
			addComponent(viewColoumnChart);
			setComponentAlignment(viewColoumnChart,  Alignment.MIDDLE_CENTER);
		});
		
		viewColoumnChart.addClickListener(l -> {
			removeAllComponents();
			addComponent(getGraphLayout());
			addComponent(viewPieChart);
			setComponentAlignment(viewPieChart,  Alignment.MIDDLE_CENTER);
		});

		setSpacing(true);
		setSizeFull();
	}

	

	private GraphLayout getGraphLayout() {
		if(graphLayout == null){
			graphLayout = new GraphLayout();
		}
		return graphLayout;
	}



	private Chart getPieChart() {
		Chart chart = new Chart(ChartType.PIE);

		Configuration configuration = chart.getConfiguration();
		configuration.setTitle("Metano/Benzina");

		YAxis yAxis = new YAxis();
		yAxis.setTitle("CiaoY");

		PlotOptionsPie pie = new PlotOptionsPie();
		pie.setShadow(false);
		configuration.setPlotOptions(pie);

		DataSeries innerSeries = new DataSeries();
		innerSeries.setName("Total cost");
		PlotOptionsPie innerPieOptions = new PlotOptionsPie();
		innerSeries.setPlotOptions(innerPieOptions);
		innerPieOptions.setSize("237px");
		innerPieOptions.setDataLabels(new DataLabels());
		innerPieOptions.getDataLabels().setFormatter("this.y > 5 ? this.point.name : null");
		innerPieOptions.getDataLabels().setColor(SolidColor.WHITE);
		innerPieOptions.getDataLabels().setDistance(-30);

		Color[] innerColors = Arrays.copyOf(colors, 5);
		
		Hashtable<Integer, Double> data = FuelData.getAggregateData(FuelData.METANOBENZINA,FuelData.TOTAL);
		innerSeries.add(new DataSeriesItem("Metano", data.get(FuelData.METANO),innerColors[4]));
		innerSeries.add(new DataSeriesItem("Benzina", data.get(FuelData.BENZINA),innerColors[2]));

		DataSeries outerSeries = new DataSeries();
		outerSeries.setName("%/Month");
		PlotOptionsPie outerSeriesOptions = new PlotOptionsPie();
		outerSeries.setPlotOptions(outerSeriesOptions);
		outerSeriesOptions.setInnerSize("237px");
		outerSeriesOptions.setSize("318px");
		outerSeriesOptions.setDataLabels(new DataLabels());
		outerSeriesOptions.getDataLabels()
				.setFormatter("this.y > 1 ? '<b>'+ this.point.name +':</b> '+ this.y +'%' : null");

		
		ArrayList<DataSeriesItem> dataSeriesItems = new ArrayList<>();
		
		data = FuelData.getAggregateData(FuelData.METANO, FuelData.TOTAL); //GET METANO PERCENTAGE ON THE TOTAL
		for (int i = 1; i < 13; i++) {
			Double tmpDouble = data.get(i);
			if (tmpDouble != null) {
				if (tmpDouble > 0) {
					dataSeriesItems.add(new DataSeriesItem(MonthConverter.nameOfMonth(i), tmpDouble, color(4)));
				}
			}
		}
		
		data = FuelData.getAggregateData(FuelData.BENZINA, FuelData.TOTAL); //GET METANO PERCENTAGE ON THE TOTAL
		for (int i = 1; i < 13; i++) {
			Double tmpDouble = data.get(i);
			if (tmpDouble != null) {
				if (tmpDouble > 0) {
					dataSeriesItems.add(new DataSeriesItem(MonthConverter.nameOfMonth(i), tmpDouble, color(2)));
				}
			}
		}

		outerSeries.setData(dataSeriesItems);
		configuration.setSeries(innerSeries, outerSeries);
		configuration.getChart().setBackgroundColor(new SolidColor("#B0BEC5"));
		chart.drawChart(configuration);

		return chart;
	}

	private static SolidColor color(int colorIndex) {
		SolidColor c = (SolidColor) colors[colorIndex];
		String cStr = c.toString().substring(1);

		int r = Integer.parseInt(cStr.substring(0, 2), 16);
		int g = Integer.parseInt(cStr.substring(2, 4), 16);
		int b = Integer.parseInt(cStr.substring(4, 6), 16);

		double opacity = (50 + rand.nextInt(95 - 50)) / 100.0;
		
		System.out.println("r: " + r + " b: "+ b +" g: " + g + " opacity: " + opacity);
		return new SolidColor(r, g, b, opacity);
	}
}
