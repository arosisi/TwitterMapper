package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MapMarkerPretty extends MapMarkerCircle {
    private static final int defaultMarkerSize = 20;
    private final int borderSize = 5;
    private final Color color;
    private final Coordinate coordinate;
    private final long ID;
    private final String imageURL;
    private final Image image;
    private final String text;

    public MapMarkerPretty(Layer layer, Color color, Coordinate coordinate, long ID, String imageURL, Image image, String text) {
        super(layer, null, coordinate, defaultMarkerSize, STYLE.FIXED, getDefaultStyle());
        this.color = color;
        this.coordinate = coordinate;
        this.ID = ID;
        this.imageURL = imageURL;
        this.image = image;
        this.text = text;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getText() {
        return text;
    }

    @Override
    public void paint(Graphics g, Point position, int radius) {
        int sizeImage = defaultMarkerSize;
        int halfSizeImage = sizeImage / 2;
        int xImage = position.x - halfSizeImage;
        int yImage = position.y - halfSizeImage;

        int size = sizeImage + borderSize * 2;
        int x = xImage - borderSize;
        int y = yImage - borderSize;

        g.setClip(new Ellipse2D.Float(x, y, size, size));
        g.setColor(color);
        g.fillOval(x, y, size, size);

        g.setClip(new Ellipse2D.Float(xImage, yImage, sizeImage, sizeImage));
        g.drawImage(image, xImage, yImage, sizeImage, sizeImage, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapMarkerPretty)) return false;

        MapMarkerPretty that = (MapMarkerPretty) o;

        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return (int) (ID ^ (ID >>> 32));
    }
}
