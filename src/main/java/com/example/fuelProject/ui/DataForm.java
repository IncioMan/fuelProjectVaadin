package com.example.fuelProject.ui;

import com.google.gwt.dom.client.Text;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class DataForm extends FormLayout{
	private DateField dateField = new DateField("Date");
	private TextField priceField = new TextField("Price");
	private TextField distanceField = new TextField("Distance");
	private TextField ammountField = new TextField("Ammount of Field");
	private OptionGroup typeSelctor = new OptionGroup("Type");
	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");
	private HorizontalLayout buttons = new HorizontalLayout();
	private FieldGroup binder = null;
	private Item currentItem = null;
	
	public DataForm(){
		typeSelctor.addItems("Metano", "Benzina");
		buttons.addComponents((Component)save,(Component)cancel);
		buttons.setSpacing(true);
		
		priceField.addValidator(new NumberValidator());
		distanceField.addValidator(new NumberValidator());
		
		addComponent(dateField);
		addComponent(priceField);
		addComponent(distanceField);
		addComponent(ammountField);
		addComponent(typeSelctor);
		addComponent(buttons);
		
		save.addClickListener(l ->{
			try {
				binder.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if("Metano".equals(typeSelctor.getValue().toString()))
				currentItem.getItemProperty("type").setValue(0);
			if("Benzina".equals(typeSelctor.getValue().toString()))
				currentItem.getItemProperty("type").setValue(1);
			
			Notification.show("Data updated!", Notification.Type.HUMANIZED_MESSAGE);
		});
		
		cancel.addClickListener(l -> {
			binder.discard();
			this.setVisible(false);
		});
	}

	public void bind(Item item) {
		System.out.println("new Item" + item);
		currentItem = item;
		
		binder = new FieldGroup(item);
		binder.bind(dateField, "date");
		binder.bind(priceField, "price");
		binder.bind(distanceField, "distance");
		binder.bind(ammountField, "ammountFuel");
		
		if("0".equals(item.getItemProperty("type").getValue().toString()))
			typeSelctor.setValue("Metano");
		else
			typeSelctor.setValue("Benzina");
	}
}
