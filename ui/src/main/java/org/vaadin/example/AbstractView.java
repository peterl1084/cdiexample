package org.vaadin.example;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

public abstract class AbstractView<P extends AbstractPresenter> extends
		CustomComponent implements ApplicationView<P>, View {

	private static final long serialVersionUID = -1501252090846963713L;

	private P presenter;

	public AbstractView() {
		setSizeFull();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		presenter.onViewEnter();
	}

	protected void setPresenter(P presenter) {
		this.presenter = presenter;
		presenter.setView(this);
	}

	@PostConstruct
	protected void postConstruct() {
		setPresenter(generatePresenter());
	}

	protected abstract P generatePresenter();

	@Override
	public P getPresenter() {
		return presenter;
	}
}
