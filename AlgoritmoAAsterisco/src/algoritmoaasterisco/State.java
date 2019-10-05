/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmoaasterisco;

import java.util.Vector;

/**
 *
 * @author Cheetos
 */
public abstract class State {

    public abstract Vector generateChildren();

    public abstract boolean goalp();

    public abstract int estimate();
}
