/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaLand.util;

import javax.swing.*;

/**
 *
 * @author aladar
 */
public class CoalescedEventUpdater {
    private Timer timer;

    public CoalescedEventUpdater(int delay, Runnable callback) {
        timer = new Timer(delay, evt -> {
            timer.stop();
            callback.run();
        });
    }

    public void update() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> {timer.restart();});
        } else {
            timer.restart();
        }
    }
}
