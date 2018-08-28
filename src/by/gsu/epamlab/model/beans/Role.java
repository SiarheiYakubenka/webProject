package by.gsu.epamlab.model.beans;

public enum Role {
    ADMIN, USER, VISITOR, FAKER;

    public static Role getRole(String password, String cmpPassword, Role role) {

        if(!cmpPassword.equals(password)) {
            return FAKER;
        }
        return role;
    }
    public static Role getRole(String password, String cmpPassword, String strRole) {
        return getRole(password, cmpPassword, valueOf(strRole.toUpperCase()));
    }
}
