package oop.analyticsapi.Enums;

public enum ActionEnum {
    Add,
    Remove,
    Increase;

    public static ActionEnum getActionFromString(String actionName) {
        for (ActionEnum action: ActionEnum.values()) {
            if (action.name().equalsIgnoreCase(actionName)) {
                return action;
            }
        }
        throw new IllegalArgumentException("Not a valid action");
    }
}


