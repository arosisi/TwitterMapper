package filters;

import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * A filter that represents the logical or of its child filters
 */
public class OrFilter implements Filter {
    private final Filter child1;
    private final Filter child2;

    public OrFilter(Filter child1, Filter child2) {
        this.child1 = child1;
        this.child2 = child2;
    }

    /**
     * An and filter matches when both of its children do, and vice versa
     * @param s     the tweet to check
     * @return      whether or not it matches
     */
    @Override
    public boolean matches(Status s) {
        return child1.matches(s) || child2.matches(s);
    }

    @Override
    public List<String> terms() {
        List<String> ans = new ArrayList<>(2);
        ans.add(child1.terms().get(0));
        ans.add(child2.terms().get(0));
        return ans;
    }

    public String toString() {
        return "(" + child1.toString() + " or " + child2.toString() + ")";
    }
}
