/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmoaasterisco;

/**
 *
 * @author Cheetos
 */
public final class node {
  State state;
  int costs;
  int distance;
  int total;
  node parent;
  node(State theState, node theParent,  int theCosts, int theDistance) {
    state = theState;
    parent = theParent;
    costs = theCosts;
    distance = theDistance;
    total = theCosts + theDistance;
  };
}
