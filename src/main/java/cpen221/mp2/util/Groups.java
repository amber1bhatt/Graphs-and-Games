package cpen221.mp2.util;

import cpen221.mp2.graph.Vertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This data type maintains collection of disjoint groups of elements.
 *
 * <p>One can add add a new element to this collection of groups,
 * and this creates a new group.</p>
 * <p>It is possible to determine whether two elements are in the same group
 * using the <tt>find</tt> operation.</p>
 * <p>The <tt>merge</tt> operation allows one to merge two groups.</p>
 * <p><strong>Example usage:</strong></p>
 * <ul>
 * <li>Creating a collection of groups: <tt>Groups&lt;SoundWave&gt; waveGroups = new Groups&lt;SoundWave&gt;();</tt></li>
 * <li>Adding an element: <tt>waveGroups.add(myWave);</tt></li>
 * <li>Finding the group that contains an element: <tt>SoundWave head = waveGroups.find(myWave);</tt></li>
 * <li>Merging two groups: <tt>waveGroups.merge(wave1, wave2);</tt></li>
 * </ul>
 *
 */

public class Groups<V extends Vertex> {

    private List<Group<V>> groups = new LinkedList<Group<V>>();

    /**
     * Add a new element to this collection of Groups.
     * If the element already exists in some group then nothing is changed,
     * otherwise a new group is created with the single element.
     *
     * @param elem is not null
     */
    public void add(V elem) {
        if (this.find(elem) == null) {
            groups.add(new Group<V>(elem));
        }
    }

    /**
     * Find the group that an element is in.
     * The group an element is in is represented by the head of that group.
     * If the element does not exist in any group then null is returned.
     *
     * @param elem is not null.
     */
    public Vertex find(V elem) {
        for (Group<V> g : groups) {
            if (g.contains(elem)) {
                return g.getHead();
            }
        }
        // if we reach this far then the document was not found
        return null; // this is not convenient but sufficient for now
    }

    /**
     *
     * Internal method for obtaining the group that contains an element.
     *
     * @param elem the element to search for.
     * @return the group that contains elem.
     * @throws NotFoundException if elem is in no group.
     */
    private Group<V> findGroup(V elem) throws NotFoundException {
        for (Group<V> g : groups) {
            if (g.contains(elem)) {
                return g;
            }
        }
        // if we reach this far then the document was not found
        throw new NotFoundException();
    }

    /**
     * Merge the groups that contain two elements.
     * If the elements do not exist in the collection then nothing is done.
     * Otherwise the group with e1 and the group with e2 are merged.
     *
     * @param e1 is not null
     * @param e2 is not null
     */
    public void merge(V e1, V e2) {
        try {
            Group<V> g1 = findGroup(e1);
            Group<V> g2 = findGroup(e2);
            if (g1 != g2) {
                g1.merge(g2);
                this.groups.remove(g2);
            }
        }
        catch (NotFoundException nfe) {
            // do nothing per spec
        }

    }

    /**
     * This is a helper type for maintaining a set of elements.
     */
    private class Group<V extends Vertex> {
        private V head;
        private Integer rank;
        private Set<V> eSet = new HashSet<V>();

        /**
         * Create a new group with a single element
         *
         * @param elem is not null
         */
        public Group(V elem) {
            head = elem;
            rank = 0;
            this.add(elem);
        }
        /**
         * Add a new element to this group.
         *
         * @param elem is not null
         *             and is not equal to another document in the collection of Groups.
         */
        public void add(V elem) {
            eSet.add(elem);
            if (rank.compareTo(this.rank) > 0) {
                head = elem;
            }
        }

        /**
         * Return the element that is the head (representative) for this group
         *
         * @return the head element for the group
         */
        public V getHead() {
            return head;
        }


        /**
         * Merge this group with another group,
         * changing the group and leaving the other group unchanged
         *
         * @param other is not null
         */
        public void merge(Group<V> other) {
            eSet.addAll(other.eSet);
            if (this.rank.compareTo(other.rank) > 0) {
                head = other.head;
            }
        }

        /**
         * Check if the group contains a particular element
         *
         * @param elem
         * @return true if elem is in the group and false otherwise
         */
        public boolean contains(V elem) {
            if (elem == null) {
                return false;
            }
            return eSet.contains(elem);
        }


    }

}
