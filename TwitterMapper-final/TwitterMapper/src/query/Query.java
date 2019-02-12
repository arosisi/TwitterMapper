package query;

import filters.Filter;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import twitter4j.Status;
import ui.MapMarkerPretty;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static util.Util.imageFromURL;
import static util.Util.statusCoordinate;

/**
 * A query over the twitter stream.
 * TODO: Task 4: you are to complete this class.
 */
public class Query implements Observer {
    // The map on which to display markers when the query matches
    private final JMapViewer map;
    // Each query has its own "layer" so they can be turned on and off all at once
    private Layer layer;
    // The color of the outside area of the marker
    private final Color color;
    // The string representing the filter for this query
    private final String queryString;
    // The filter parsed from the queryString
    private final Filter filter;
    // The checkBox in the UI corresponding to this query (so we can turn it on and off and delete it)
    private JCheckBox checkBox;

    private List<MapMarkerCircle> markers;

    public Color getColor() {
        return color;
    }
    public String getQueryString() {
        return queryString;
    }
    public Filter getFilter() {
        return filter;
    }
    public Layer getLayer() {
        return layer;
    }
    public JCheckBox getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }
    public void setVisible(boolean visible) {
        layer.setVisible(visible);
    }
    public boolean getVisible() { return layer.isVisible(); }

    public Query(String queryString, Color color, JMapViewer map) {
        this.queryString = queryString;
        this.filter = Filter.parse(queryString);
        this.color = color;
        this.layer = new Layer(queryString);
        this.map = map;
        markers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Query: " + queryString;
    }

    /**
     * This query is no longer interesting, so terminate it and remove all traces of its existence.
     *
     * TODO: Implement this method
     */
    public void terminate() {
        for (MapMarkerCircle marker : markers) {
            map.removeMapMarker(marker);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Status status = (Status) arg;
        if (filter.matches(status)) {
            Coordinate coordinate = statusCoordinate(status);
            long ID = status.getId();
            String imageURL = status.getUser().getProfileImageURL();
            Image image = imageFromURL(imageURL);
            String text = status.getText();
            MapMarkerPretty marker = new MapMarkerPretty(layer, color, coordinate, ID, imageURL, image, text);
            markers.add(marker);
            map.addMapMarker(marker);
        }
    }
}

