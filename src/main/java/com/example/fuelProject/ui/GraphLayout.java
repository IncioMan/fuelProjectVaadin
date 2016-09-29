package com.example.fuelProject.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import com.example.fuelProject.data.FuelData;
import com.example.fuelProject.data.MonthConverter;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

public class GraphLayout extends VerticalLayout implements ValueChangeListener{
	private Component graph = null;
	private HorizontalLayout optionsLayout = null;
	private OptionGroup type = new OptionGroup();
	private OptionGroup value = new OptionGroup();
	private OptionGroup period = new OptionGroup();
	
	public GraphLayout() {
		addComponent(getOptionsLayout());
		addComponent(getGraph(type.getValue(), value.getValue(), period.getValue()));
		
		setSpacing(true);
		setSizeFull();
	}
	
	private Component getGraph(Object type, Object value, Object period) {
		Chart chart = new Chart();
		Configuration conf = chart.getConfiguration();
		conf.setTitle("");
		conf.getChart().setType(ChartType.COLUMN);

		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");// formatter
																	// for date

		ArrayList<FuelData> tmp = null;
		try {
			tmp = FuelData.getDatas();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XAxis xAxis = conf.getxAxis();
		xAxis.setTitle(period.toString());
		YAxis yAxis = conf.getyAxis();
		yAxis.setTitle(value.toString());

		Style labelStyle = new Style();
		labelStyle.setColor(SolidColor.BLACK);
		// labelStyle.setFontWeight("bold");
		labelStyle.setFontFamily("Source Sans Pro");
		labelStyle.setFontSize("23px");
		xAxis.getLabels().setStyle(labelStyle);
		yAxis.getLabels().setStyle(labelStyle);

		ListSeries listSeries = new ListSeries(value.toString());

		boolean metano = ("Metano".equals(type.toString()));
		boolean benzina = ("Benzina".equals(type.toString()));
		boolean distance = ("Distance".equals(value.toString()));
		boolean price = ("Price").equals(value.toString());
		boolean ammountFuel = ("Ammount of Fuel".equals(type.toString()));
		boolean day = ("Day".equals(period.toString()));
		boolean month = ("Month").equals(period.toString());

		if (metano && price && day) {
			for (FuelData fData : tmp) {
				if (fData.getType() == FuelData.METANO) {
					System.out.println(fData.getDate());
					xAxis.addCategory(sm.format(fData.getDate()));
					listSeries.addData(fData.getPrice());
				}
			}
		}

		if (metano && distance && day) {
			for (FuelData fData : tmp) {
				if (fData.getType() == FuelData.METANO) {
					xAxis.addCategory(sm.format(fData.getDate()));
					listSeries.addData(fData.getDistance());
				}
			}
		}

		if (metano && distance && month) {
			Hashtable<Integer, Integer> hashtable;
			hashtable = FuelData.getAggregateData(FuelData.METANO, FuelData.DISTANCE, FuelData.MONTH);
			for (int i = 1; i < 13; i++) {
				Integer tmpInt = hashtable.get(i);
				if (tmpInt != null) {
					if (tmpInt > 0) {
						xAxis.addCategory(MonthConverter.nameOfMonth(i));
						listSeries.addData(tmpInt);
					}
				}
			}
		}

		if (metano && price && month) {
			Hashtable<Integer, Integer> hashtable;
			hashtable = FuelData.getAggregateData(FuelData.METANO, FuelData.PRICE, FuelData.MONTH);
			for (int i = 1; i < 13; i++) {
				Integer tmpInt = hashtable.get(i);
				if (tmpInt != null) {
					if (tmpInt > 0) {
						xAxis.addCategory(MonthConverter.nameOfMonth(i));
						listSeries.addData(tmpInt);
					}
				}
			}
		}

		if (benzina && price && day) {
			for (FuelData fData : tmp) {
				if (fData.getType() == FuelData.BENZINA) {
					xAxis.addCategory(sm.format(fData.getDate()));
					listSeries.addData(fData.getPrice());
				}
			}
		}

		if (benzina && distance && day) {
			for (FuelData fData : tmp) {
				if (fData.getType() == FuelData.BENZINA && fData.getDistance() > 0) {
					xAxis.addCategory(sm.format(fData.getDate()));
					listSeries.addData(fData.getDistance());
				}
			}
		}

		if (benzina && distance && month) {
			Hashtable<Integer, Integer> hashtable;
			hashtable = FuelData.getAggregateData(FuelData.BENZINA, FuelData.DISTANCE, FuelData.MONTH);
			for (int i = 1; i < 13; i++) {
				Integer tmpInt = hashtable.get(i);
				if (tmpInt != null) {
					if (tmpInt > 0) {
						xAxis.addCategory(MonthConverter.nameOfMonth(i));
						listSeries.addData(tmpInt);
					}
				}
			}
		}

		if (benzina && price && month) {
			Hashtable<Integer, Integer> hashtable;
			hashtable = FuelData.getAggregateData(FuelData.BENZINA, FuelData.PRICE, FuelData.MONTH);
			for (int i = 1; i < 13; i++) {
				Integer tmpInt = hashtable.get(i);
				if (tmpInt != null) {
					if (tmpInt > 0) {
						xAxis.addCategory(MonthConverter.nameOfMonth(i));
						listSeries.addData(tmpInt);
					}
				}
			}
		}

		PlotOptionsColumn plotOpt = new PlotOptionsColumn();
		plotOpt.setColor(SolidColor.GRAY);
		if (benzina)
			plotOpt.setColor(new SolidColor(152,223,88));
		if (metano)
			plotOpt.setColor(new SolidColor(36,220,212));
		listSeries.setPlotOptions(plotOpt);

		conf.addxAxis(xAxis);
		conf.setSeries(listSeries);

		Legend legend = new Legend();
		legend.setLayout(LayoutDirection.VERTICAL);
		legend.setBackgroundColor(new SolidColor("#B0BEC5"));
		legend.setAlign(HorizontalAlign.RIGHT);
		legend.setVerticalAlign(VerticalAlign.TOP);
		legend.setFloating(true);
		legend.setShadow(true);
		conf.setLegend(legend);

		conf.getChart().setBackgroundColor(new SolidColor("#B0BEC5"));

		graph = chart;
		return chart;
	}
	
	public HorizontalLayout getOptionsLayout() {
		if(optionsLayout == null){
			optionsLayout = new HorizontalLayout();

			type.addItems("Metano", "Benzina");
			type.setValue("Metano");
			type.addValueChangeListener(this);

			value.addItems("Price", "Distance");
			value.setValue("Price");
			value.addValueChangeListener(this);

			period.addItems("Day", "Month");
			period.setValue("Day");
			period.addValueChangeListener(this);

			optionsLayout.setSizeFull();
			optionsLayout.addComponents(type, value, period);
			optionsLayout.setComponentAlignment(type, Alignment.MIDDLE_CENTER);
			optionsLayout.setComponentAlignment(period, Alignment.MIDDLE_CENTER);
			optionsLayout.setComponentAlignment(value, Alignment.MIDDLE_CENTER);
		}
		return optionsLayout;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		removeComponent(graph);
		graph = getGraph(type.getValue(), value.getValue(), period.getValue());
		addComponent(graph);		
	}
}
