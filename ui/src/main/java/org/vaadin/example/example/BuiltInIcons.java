package org.vaadin.example.example;

import com.vaadin.cdi.CDIView;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.vaadin.example.AbstractView;
import org.vaadin.example.ViewMenuItem;
import org.vaadin.maddon.label.RichText;
import org.vaadin.maddon.layouts.MVerticalLayout;

@CDIView("icons")
@ViewMenuItem(title = "Awesome Font Icons", icon = FontAwesome.FLAG)
public class BuiltInIcons extends AbstractView<ExampleViewPresenter> implements
        ExampleView {

    @Inject
    private Instance<ExampleViewPresenter> presenterInstance;

    public BuiltInIcons() {
        setHeightUndefined();

        // All of FontAwesome
        StringBuilder sb = new StringBuilder();
        for (FontAwesome ic : FontAwesome.values()) {
            sb.append(String.format(FA_FORMAT, ic.getHtml(), ic.name()));
        }
        Label label = new Label(sb.toString(), ContentMode.HTML);

        setCompositionRoot(new MVerticalLayout(
                new RichText().withMarkDown(
                        "# All FontAwesome(ness) available in 7.2"),
                label
        ));
    }

    private static final String FA_FORMAT = 
            "<div style='display:inline-block; width:300px;'>%s %s</div>";

    @Override
    protected ExampleViewPresenter generatePresenter() {
        return presenterInstance.get();
    }

    @Override
    public void setMessage(String message) {
    }
}
