package com.example.fuelProject;

import javax.servlet.annotation.WebServlet;

import com.example.fuelProject.ui.GraphTab;
import com.example.fuelProject.ui.TableTab;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("firstTestTheme")
@Widgetset("com.example.fuelProject.MyAppWidgetset")
public class MyUI extends UI {
	private TableTab tableTab = null;
	private GraphTab graphTab = null;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        layout.addComponent(getTabSheet());
        
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    private Component getTabSheet() {
		TabSheet tabSheet = new TabSheet();
		
		tabSheet.addTab(getTableTab(), "Data");
		tabSheet.addTab(getGraphTab(), "Statistics");
		
		return tabSheet;
	}

	private Component getGraphTab() {
		if(graphTab == null){
			graphTab = new GraphTab();
		}
		return (Component) graphTab;
	}

	private Component getTableTab() {
		if(tableTab == null){
			tableTab = new TableTab();
		}
		return tableTab;
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
