package org.vaadin.example;

public abstract class AbstractPresenter<V extends ApplicationView> {

	private V view;

	protected abstract void onViewEnter();

	protected void setView(V view) {
		this.view = view;
	}

	protected V getView() {
		return view;
	}
}
