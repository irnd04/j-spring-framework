package j.spring.framework.core.testdata.web.handlermapping.annotation.resolver;

import java.util.List;

public class RequestBodyValue {

    private String value;
    private int i;
    private List<Long> li;

    public String getValue() {
        return value;
    }

    public int getI() {
        return i;
    }

    public List<Long> getLi() {
        return li;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setLi(List<Long> li) {
        this.li = li;
    }
}
