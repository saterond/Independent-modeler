/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.indepmod.independentmodeler.workspace.graphcells;

/**
 *
 * @param <T>
 * @author Studio
 */
public interface ICellFactory<T extends Cell> {
    public T creta();
}
