public class Cell {
    Boolean isVisible = false;
    String placeholder = ".";
    public String value = ".";

    public void enableVisibility(){
        isVisible = true;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        if(isVisible) {
            return value;
        } else {
            return placeholder;
        }
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
