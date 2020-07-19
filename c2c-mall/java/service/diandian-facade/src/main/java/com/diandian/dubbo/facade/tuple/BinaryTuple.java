package com.diandian.dubbo.facade.tuple;

import java.io.Serializable;
import java.util.Objects;

/**
 * 二元组数据对象
 * @author cjunyuan
 * @date 2019/5/15 10:02
 */
public class BinaryTuple<T1 extends Serializable, T2 extends Serializable> implements Serializable {

    private static final long serialVersionUID = -3603250378614569047L;

    private T1  value1;

    private T2  value2;

    public BinaryTuple() {
    }

    public BinaryTuple(T1 value1, T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T1 getValue1() {
        return value1;
    }

    public void setValue1(T1 value1) {
        this.value1 = value1;
    }

    public T2 getValue2() {
        return value2;
    }

    public void setValue2(T2 value2) {
        this.value2 = value2;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.value1);
        hash = 19 * hash + Objects.hashCode(this.value2);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BinaryTuple<?, ?> other = (BinaryTuple<?, ?>) obj;
        if (!Objects.equals(this.value1, other.value1)) {
            return false;
        }
        if (!Objects.equals(this.value2, other.value2)) {
            return false;
        }
        return true;
    }
}
