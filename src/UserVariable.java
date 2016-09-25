/**
 * Created by Giampiero on 9/19/2016.
 */
public class UserVariable {
    private String key;
    private int value;

    public UserVariable(String pKey, int pValue){
        key = pKey;
        value = pValue;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}

