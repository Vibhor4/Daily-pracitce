package reflect.serializeImpl;

import com.google.common.base.Preconditions;

/**
 * Created by zzt on 4/4/17.
 * <p>
 * <h3></h3>
 */
public class TriChoice<F, S, T> extends Choice<F, S> {

    private T third;

    public T getThird() {
        Preconditions.checkNotNull(third);
        return third;
    }

    @Override
    public void setFirst(F f) {
        super.setFirst(f);
        third = null;
    }

    @Override
    public void setSecond(S s) {
        super.setSecond(s);
        third = null;
    }

    public void setThird(T third) {
        this.third = third;
        super.setFirst(null);
        super.setSecond(null);
    }

    public boolean isThird() {
        return third != null;
    }
}
