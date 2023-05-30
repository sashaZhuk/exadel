package com.modsen.test.views.components;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

public class FileDownloadLink extends VerticalLayout {
    public static final String ANCHOR_DOWNLOAD_ATTRIBUTE_NAME = "download";

    private StreamResource resource;

    public FileDownloadLink() {
        setMargin(true);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    public void setResource(StreamResource resource) {
        this.resource = resource;
        refreshFileLink();
    }

    private void refreshFileLink() {
        removeAll();
        addLinkToFile(resource);
    }

    private void addLinkToFile(StreamResource resource) {
        Anchor link = new Anchor(resource, resource.getName());
        link.getElement().setAttribute(ANCHOR_DOWNLOAD_ATTRIBUTE_NAME, true);

        add(link);
    }
}
