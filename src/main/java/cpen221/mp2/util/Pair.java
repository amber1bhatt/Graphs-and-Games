package cpen221.mp2.util;

/**
 * <p>Represents a pair of elements of the same type.
 *
 */
public class Pair<V> {
    private V elem1, elem2;
    /**
     * Create a new pair given two elements
     *
     * @param elem1 not null
     * @param elem2 not null
     */
    public Pair(V elem1, V elem2) {
        assert ((elem1 != null) && (elem2 != null));
        this.elem1 = elem1;
        this.elem2 = elem2;
    }

    /**
     * Compare two Pair objects for equality.
     *
     * @param obj is not null
     * @return true if this Pair and the other Pair represent
     * the same two elems and false otherwise.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Pair) {
            Pair other = (Pair) obj;
            return ((this.elem1.equals(other.elem1) && this.elem2.equals(other.elem2))
                    || (this.elem1.equals(other.elem2) && (this.elem2.equals(other.elem1))));
        } else {
            return false;
        }

    }

    /**
     * Determine whether an element exists within the Pair
     *
     * @param elem is not null
     * @return true if this Pair contains elem, false otherwise.
     */
    public boolean contains(V elem) {

        if (elem.equals(this.elem1) || elem.equals(this.elem2)) {
            return true;
        }

        return false;
    }

    /**
     * Compute the hashCode for a Pair
     *
     * @return the hashCode for this Pair
     */
    @Override
    public int hashCode() {
        return elem1.hashCode() + elem2.hashCode();
    }


}