package enums;

public enum StatusJogoEnum {

    NAO_INICIADO("Não iniciado"),
    INCOMPLETO("Incompleto"),
    COMPLETO("Completo");

    private String label;
    StatusJogoEnum(final String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
