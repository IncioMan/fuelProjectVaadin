package com.example.fuelProject.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.fuelProject.data.FuelData;
import com.example.fuelProject.data.FuelDataContainer;
import com.google.gwt.dom.client.TableCaptionElement;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.sass.internal.tree.ReturnNode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;

public class DataTable extends Table {
	BeanItemContainer<FuelData> container = new FuelDataContainer();
	ArrayList<Object> selectedItemIds = new ArrayList<Object>();
	private TableTab tableTab = null;

	public DataTable(TableTab l) {
		tableTab = l;
		
		setSizeFull();
		setSelectable(true);
		setImmediate(true);
		addItemClickListener((ItemClickListener) l);
		setContainerDataSource(container);
		
		addGeneratedColumn("checked", new ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final CheckBox cb = new CheckBox("", false);
		        cb.addValueChangeListener(new Property.ValueChangeListener() {

				@Override
		          public void valueChange(Property.ValueChangeEvent event) {
		            if(selectedItemIds.contains(itemId)){
		              selectedItemIds.remove(itemId);
		            } else {
		              selectedItemIds.add(itemId);
		            }
		            l.enableDeleteButton(!selectedItemIds.isEmpty());
		          }
		        });
		        return cb;
			}
		});
		
		setVisibleColumns(new Object[] {"date", "price", "distance", "ammountFuel", "type","checked"});
		setColumnHeaders(new String[] {"Date", "Price", "Distance", "Ammount of Fuel", "Type","Selected"});
		setPageLength(9);
	}
	
	@Override
	protected String formatPropertyValue(Object rowId, Object colId, Property property) {
		if("type".equals(colId)){
			if("0".equals(property.getValue().toString()))
					return "Metano";
			if("1".equals(property.getValue().toString()))
					return "Benzina";
		}
		if("date".equals(colId)){
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			return df.format((Date)property.getValue()); 
		}
		return super.formatPropertyValue(rowId, colId, property);
	}

	public void removeSelectedItems() {
		BeanItemContainer<FuelData> container = (BeanItemContainer<FuelData>) getContainerDataSource();
		for(Object itemId : selectedItemIds){
			container.removeItem(itemId);
		}
		selectedItemIds.clear();
		tableTab.enableDeleteButton(false);
	}
}
