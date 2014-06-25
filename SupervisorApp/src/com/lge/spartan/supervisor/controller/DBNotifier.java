/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lge.spartan.supervisor.controller;

import com.lge.spartan.supervisor.view.SupervisorView;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author gina.du
 */
public class DBNotifier extends Observable{    
    private ArrayList<SupervisorView> observers = new ArrayList<SupervisorView>();   
        
    public DBNotifier() {
        super();
    }
    
    public void notifyObservers() {  
        
         for (SupervisorView ob : observers) {  
             ob.update(this, this);  
        }
    }  
  
    public void registerObserver(SupervisorView observer) {  
         observers.add(observer);   
    }  
  
    public void removeObserver(SupervisorView observer) {  
         observers.remove(observer);  
    }
}

