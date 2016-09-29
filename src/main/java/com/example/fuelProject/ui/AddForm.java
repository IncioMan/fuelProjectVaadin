package com.example.fuelProject.ui;

import com.example.fuelProject.data.FuelData;
import com.example.fuelProject.data.FuelService;
import com.example.fuelProject.data.Service;
import com.google.gwt.dom.client.Text;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddForm extends FormLayout {
	private DateField dateField = new DateField("Date");
	private TextField priceField = new TextField("Price");
	private TextField distanceField = new TextField("Distance");
	private TextField ammountField = new TextField("Ammount of Field");
	private OptionGroup typeSelctor = new OptionGroup("Type");
	private Button add = new Button("Add");
	private Button cancel = new Button("Cancel");
	private HorizontalLayout buttons = new HorizontalLayout();
	private FieldGroup binder = null;
	private Item currentItem = null;
	private BeanItemContainer<FuelData> container;
	private BeanItem<FuelData> item;

	public AddForm(){
		typeSelctor.addItems("Metano", "Benzina");
		buttons.addComponents((Component)add,(Component)cancel);
		buttons.setSpacing(true);
		
		priceField.addValidator(new NumberValidator());
		distanceField.addValidator(new NumberValidator());
		
		addComponent(dateField);
		addComponent(priceField);
		addComponent(distanceField);
		addComponent(ammountField);
		addComponent(typeSelctor);
		addComponent(buttons);
		
		add.addClickListener(l ->{
			Service service = new FuelService();
			service.addFuel((FuelData)item.getBean());
			
			if("Metano".equals(typeSelctor.getValue().toString()))
				item.getItemProperty("type").setValue(0);
			if("Benzina".equals(typeSelctor.getValue().toString()))
				item.getItemProperty("type").setValue(1);
			try {
				binder.commit();
				container.addItem(item.getBean());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Notification.show("Data added!", Notification.Type.HUMANIZED_MESSAGE);
		});
		
		cancel.addClickListener(l -> {
			binder.discard();
			this.setVisible(false);
		});
	}

	public void bind(BeanItemContainer<FuelData> container) {
		this.container = container;
		item = new BeanItem<FuelData>(new FuelData());

		binder = new FieldGroup(item);
		binder.bind(dateField, "date");
		binder.bind(priceField, "price");
		binder.bind(distanceField, "distance");
		binder.bind(ammountField, "ammountFuel");

		if ("0".equals(item.getItemProperty("type").getValue().toString()))
			typeSelctor.setValue("Metano");
		else
			typeSelctor.setValue("Benzina");
	}
}
