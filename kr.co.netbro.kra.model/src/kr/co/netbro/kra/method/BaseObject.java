package kr.co.netbro.kra.method;

import java.io.Serializable;

public abstract class BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

    public boolean equals(Object obj) {
        return BeanUtils.equals(this, obj);
    }

    public int hashCode() {
        return BeanUtils.hashCode(this);
    }

    public String toString() {
        return BeanUtils.toString(this);
    }
    
}
