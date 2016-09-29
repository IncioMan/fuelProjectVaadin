package com.example.fuelProject.ui;

import java.awt.List;

import com.example.fuelProject.data.FuelData;
import com.google.gwt.thirdparty.common.css.compiler.gssfunctions.GssFunctions.AddHsbToCssColor;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class TableTab extends VerticalLayout implements ItemClickListener {
	private HorizontalLayout hl = new HorizontalLayout();
	private HorizontalLayout buttons = new HorizontalLayout();
	private DataTable dataTable = null;
	private DataForm dataForm = null;
	private AddForm addForm = new AddForm();
	private Button addButton = new Button("Add");
	private Button deleteButton = new Button("Delete");

	public TableTab() {
		hl.addComponent(getTable());
		hl.addComponent(getForm());
		hl.setSpacing(true);
		dataForm.setVisible(false);

		hl.setExpandRatio(dataTable, 5.0f);
		hl.setExpandRatio(dataForm, 2.0f);
		hl.setSizeFull();

		addComponent(hl);
		buttons.addComponent(addButton);
		buttons.setComponentAlignment(addButton, Alignment.MIDDLE_CENTER);
		deleteButton.setEnabled(false);
		buttons.addComponent(deleteButton);
		buttons.setComponentAlignment(deleteButton, Alignment.MIDDLE_CENTER);
		buttons.setSpacing(true);

		addButton.addClickListener(listener -> {
			dataForm.setVisible(false);
			hl.removeComponent(addForm);
			addForm = new AddForm();
			hl.addComponent(addForm);
			hl.setExpandRatio(addForm, 2.0f);
			addForm.bind((BeanItemContainer<FuelData>) dataTable.getContainerDataSource());
		});

		deleteButton.addClickListener(l -> {
			dataTable.removeSelectedItems();
		});

		addComponent(buttons);
		setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);

		setSpacing(true);
		setMargin(true);
		setSizeFull();
	}

	private Component getForm() {
		if (dataForm == null) {
			dataForm = new DataForm();
		}
		return dataForm;
	}

	private Component getTable() {
		if (dataTable == null) {
			dataTable = new DataTable(this);
		}
		return dataTable;
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		if (event.isDoubleClick()) {
			dataForm.bind(event.getItem());
			addForm.setVisible(false);
			dataForm.setVisible(true);
		}
	}

	public void addForm() {
		dataForm.setVisible(true);
	}

	public void enableDeleteButton(boolean value) {
		deleteButton.setEnabled(value);
	}
}
