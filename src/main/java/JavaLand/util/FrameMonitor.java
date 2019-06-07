/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaLand.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.prefs.Preferences;

/**
 *
 * @author aladar
 */
public class FrameMonitor {

    public static void registerFrame(JFrame frame, String frameUniqueId,
                                     int defaultX, int defaultY, int defaultW, int defaultH) {
        Preferences prefs = Preferences.userRoot()
                                       .node(FrameMonitor.class.getSimpleName() + "-" + frameUniqueId);
        frame.setLocation(getFrameLocation(prefs, defaultX, defaultY));
        frame.setSize(getFrameSize(prefs, defaultW, defaultH));

        // Store defaults
        prefs.putInt("default_x", defaultX);
        prefs.putInt("default_y", defaultY);
        prefs.putInt("default_w", defaultW);
        prefs.putInt("default_h", defaultH);
        
        CoalescedEventUpdater updater = new CoalescedEventUpdater(400,
                () -> updatePref(frame, prefs));

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updater.update();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                updater.update();
            }
        });
    }

    public static void setDefaults(JFrame frame, String frameUniqueId) {
        Preferences prefs = Preferences.userRoot()
                                       .node(FrameMonitor.class.getSimpleName() + "-" + frameUniqueId);

        int defaultX = prefs.getInt("default_x", 0);
        int defaultY = prefs.getInt("default_y", 0);
        int defaultW = prefs.getInt("default_w", 500);
        int defaultH = prefs.getInt("default_h", 400);

        prefs.putInt("x", defaultX);
        prefs.putInt("y", defaultY);
        prefs.putInt("w", defaultW);
        prefs.putInt("h", defaultH);

        frame.setLocation(getFrameLocation(prefs, defaultX, defaultY));
        frame.setSize(getFrameSize(prefs, defaultW, defaultH));
    }

    private static void updatePref(JFrame frame, Preferences prefs) {
        Point location = frame.getLocation();
        prefs.putInt("x", location.x);
        prefs.putInt("y", location.y);
        Dimension size = frame.getSize();
        prefs.putInt("w", size.width);
        prefs.putInt("h", size.height);
    }

    private static Dimension getFrameSize(Preferences pref, int defaultW, int defaultH) {
        int w = pref.getInt("w", defaultW);
        int h = pref.getInt("h", defaultH);
        return new Dimension(w, h);
    }

    private static Point getFrameLocation(Preferences pref, int defaultX, int defaultY) {
        int x = pref.getInt("x", defaultX);
        int y = pref.getInt("y", defaultY);
        return new Point(x, y);
    }
}
