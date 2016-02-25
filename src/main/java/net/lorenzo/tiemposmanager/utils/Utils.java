/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.utils;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import org.controlsfx.control.PopOver;

/**
 *
 * @author Lorenzo
 */
public class Utils {

    public static void generatePopup(PopOver popToOpen, Region callingElement, Node elementToShow) {
        Scene scene = callingElement.getScene();

        Point2D windowCoord = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
        Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());
        Point2D nodeCoord = callingElement.localToScene(0.0, 0.0);
        double clickX = Math.round(windowCoord.getX() + sceneCoord.getY() + nodeCoord.getX());
        double clickY = Math.round(windowCoord.getY() + sceneCoord.getY() + nodeCoord.getY() + callingElement.getHeight());
        popToOpen.setContentNode(elementToShow);
        popToOpen.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
        popToOpen.setCornerRadius(4);
        popToOpen.setDetachable(false);
        popToOpen.autoHideProperty();
        popToOpen.show(callingElement.getParent(), clickX, clickY);
    }
}
