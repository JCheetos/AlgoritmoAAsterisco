/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmoaasterisco;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Cheetos
 */
public final class AStar {
    // {{{ Variables

    private final Hashtable open = new Hashtable(500);
    private final Hashtable closed = new Hashtable(500);
    public int evaluated = 0;
    public int expanded = 0;
    public int bestTotal = 0;
    public boolean ready = false;
    private boolean newBest = false;
    private final Vector nodes = new Vector(); //sorted open node

    // }}}
    private synchronized void setBest(int value) {
        bestTotal = value;
        newBest = true;
        notify(); // All?
        Thread.currentThread().yield();  //for getNewBest
    }

    public synchronized int getNewBest() {
        while (!newBest) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        newBest = false;
        return bestTotal;
    }

// {{{ private  node search()
    private node search() {
        node best;
        Vector childStates;
        int childCosts;
        Vector children = new Vector();

        while (!(nodes.isEmpty())) {
            best = (node) nodes.firstElement();
            if (closed.get(best.state) != null) { //to avoid having to remove
                nodes.removeElementAt(0);          // improved nodes from nodes
                continue;
            }
            if (!(best.total == bestTotal)) {
                setBest(best.total);
            }
            if ((best.state).goalp()) {
                return best;
            }

            children.removeAllElements();
            childStates = (best.state).generateChildren();
            childCosts = 1 + best.costs;
            expanded++;

            for (int i = 0; i < childStates.size(); i++) {
                State childState = (State) childStates.elementAt(i);
                node closedNode = null;
                node openNode = null;
                node theNode = null;

                if ((closedNode = (node) closed.get(childState)) == null) {
                    openNode = (node) open.get(childState);
                }
                theNode = (openNode != null) ? openNode : closedNode;
                if (theNode != null) {
                    if (childCosts < theNode.costs) {
                        if (closedNode != null) {
                            open.put(childState, theNode);
                            closed.remove(childState);
                        } else {
                            int dist = theNode.distance;
                            theNode = new node(childState, best, childCosts, dist);
                            open.put(childState, theNode);
                            //nodes.removeElement(theNode); //get rid of this
                        }
                        theNode.costs = childCosts;
                        theNode.total = theNode.costs + theNode.distance;
                        theNode.parent = best;
                        children.addElement(theNode);

                    }
                } else {
                    int estimation;
                    node newNode;

                    estimation = childState.estimate();
                    newNode = new node(childState, best, childCosts, estimation);
                    open.put(childState, newNode);
                    evaluated++;
                    children.addElement(newNode);
                }
            }
            open.remove(best.state);
            closed.put(best.state, best);
            nodes.removeElementAt(0);
            addToNodes(children); // update nodes

        }
        return null; //no open nodes and no solution
    }

// }}}
    private int rbsearch(int l, int h, int tot, int costs) {
        if (l > h) {
            return l; //insert before l
        }
        int cur = (l + h) / 2;
        int ot = ((node) nodes.elementAt(cur)).total;
        if ((tot < ot)
                || (tot == ot && costs >= ((node) nodes.elementAt(cur)).costs)) {
            return rbsearch(l, cur - 1, tot, costs);
        }
        return rbsearch(cur + 1, h, tot, costs);
    }

    private int bsearch(int l, int h, int tot, int costs) {
        int lo = l;
        int hi = h;
        while (lo <= hi) {
            int cur = (lo + hi) / 2;
            int ot = ((node) nodes.elementAt(cur)).total;
            if ((tot < ot)
                    || (tot == ot && costs >= ((node) nodes.elementAt(cur)).costs)) {
                hi = cur - 1;
            } else {
                lo = cur + 1;
            }
        }
        return lo; //insert before lo
    }

// {{{ private  void addToNodes(Vector children)
    private void addToNodes(Vector children) {
        for (int i = 0; i < children.size(); i++) {
            node newNode = (node) children.elementAt(i);
            int newTotal = newNode.total;
            int newCosts = newNode.costs;
            boolean done = false;
            int idx = bsearch(0, nodes.size() - 1, newTotal, newCosts);
            nodes.insertElementAt(newNode, idx);

//       for (int j = 0; j < nodes.size(); j++) {
// int ot = ((node) nodes.elementAt(j)).total;
// if (newTotal <  ot) {
//   nodes.insertElementAt(newNode, j);
//   done = true;
//   break;
// }
// if (newTotal == ot) {
//   if (newNode.costs >= ((node) nodes.elementAt(j)).costs) {
//     nodes.insertElementAt(newNode, j);
//     done = true;
//     break;
//   }}
//       }
//       if (!done) nodes.addElement(newNode);
        }
    }

// }}}
// {{{ public final  Vector solve (State initialState)
    public final Vector solve(State initialState) {
        node solution;
        node firstNode;
        int estimation;

        expanded = 0;
        evaluated = 1;
        estimation = initialState.estimate();
        firstNode = new node(initialState, null, 0, estimation);

        open.put(initialState, firstNode);
        nodes.addElement(firstNode);

        solution = search();
        nodes.removeAllElements();
        open.clear();
        closed.clear();
        ready = true;
        setBest(bestTotal);
        return getSequence(solution);
    }

// }}}
// {{{ private  Vector getSequence(node n)
    private Vector getSequence(node n) {
        Vector result;
        if (n == null) {
            result = new Vector();
        } else {
            result = getSequence(n.parent);
            result.addElement(n.state);
        }
        return result;
    }

// }}}
}