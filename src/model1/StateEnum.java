package model1;

import java.io.Serializable;

public enum StateEnum implements Serializable {
    CANNOT_BE_BURNED,
    HAS_NOT_IGNITED,
    BURNING,
    BURNT;

    private static final long serialVersionUID = 1L;

    public static StateEnum getStateyByName(final String name) {
        return StateEnum.valueOf(name);
    }

    public static long getIndexByName(final String name){
        return StateEnum.valueOf(name).ordinal();
    }

    public long getIndex() {
        return ordinal();
    }

    public String getName() {
        return name();
    }
}
