import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by kawasaki on 2015/12/10.
 */
public class Hoge {

    @Test
    public void tet() {
        Optional<Integer> a = Optional.of(3);
        Optional<Integer> result = bai(a);
        assertThat(result.get(), is(9));

        result = bai2(a);
        assertThat(result.get(), is(9));
    }

    @Test
    public void testEmpty() {
        Optional<Integer> a = Optional.empty();
        Optional<Integer> result = bai2(a);
        assertThat(result.isPresent(), is(false));
    }

    private Optional<Integer> bai2(Optional<Integer> a) {
        return a.map(x -> x * x);
    }
    private Optional<Integer> bai(Optional<Integer> a) {
        Optional<Integer> result;
        if (a.isPresent()) {
            Integer x = a.get();
            result = Optional.of(x * x);
        } else {
            result = Optional.empty();
        }
        return result;
    }

    @Test
    public void testSqrt() {
        assertThat(safeSqrt(-1), is(Optional.empty()));
        assertThat(safeSqrt(0).get(), is(0d));
        assertThat(safeSqrt(1).get(), is(1d));

    }

    static Optional<Double> safeSqrt(double x) {
        if (x < 0.0) {
            return Optional.empty();
        }
        return Optional.of(Math.sqrt(x));
    }
}
