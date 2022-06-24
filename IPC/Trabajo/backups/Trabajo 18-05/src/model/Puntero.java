/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.geometry.Point2D;

/**
 *
 * @author marci
 */
public class Puntero {

    private Point2D position;
    private String description;
    
    
    Puntero(double x, double y){
        position= new Point2D(x,y);
        
    }

    Puntero() {
         
    }

    /**
     * Get the value of position
     *
     * @return the value of position
     */
    public Point2D getPosition() {
        return position;
    }

    /**
     * Set the value of position
     *
     * @param position new value of position
     */
    public void setPosition(Point2D position) {
        this.position = position;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    

    @Override
    public String toString() {
        return  position.toString() + ", " + description ;
    }
    
}
