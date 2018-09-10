package by.gsu.epamlab.model.impl;

public class ConstantsSQL {
    public static final String SELECT_USER = "select  password, roles.role" +
            " from users join roles on users.roleId = roles.id" +
            " where login=?";
    public static final int INDEX_ONE = 1;
    public static final int INDEX_TWO = 2;
    public static final int INDEX_THREE = 3;
    public static final int INDEX_FOUR = 4;
    public static final int INDEX_FIVE = 5;
    public static final int INDEX_SIX = 6;
    public static final String PARAM_ROLE = "role";
    public static final String PARAM_PASS = "password";
    public static final String ADD_USER = "insert into users (login, password) values (?, ?)";
    public static final String USER_ID = "select id from users where login=?";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "completionsDate";
    public static final String KEY_IS_FIXED = "isFixed";
    public static final String KEY_IS_DEL = "isDelMarked";
    public static final String KEY_ID = "id";
    public static final String ADD_TASK = "insert into tasks (description, completionsDate, userId) values (?, ?, ?);";
    public static final String DELETE_TASK = "update tasks set isDelMarked=true where id=?";
    public static final String UPDATE_TASK = "update tasks set description=?, completionsDate=? where userId=? AND id=";
    public static final String KEY_FILENAME = "fileName";
    public static final String SELECT_TASKS_HEADER = "select * from tasks where userId=? AND ";
    public static final String FIX_TASK = "update tasks set isFixed=true where id=?";
    public static final String RECOVER_TASK = "update tasks set isDelMarked=false, isFixed=false where id=?";
    public static final String REMOVE_TASK = "delete from tasks where id=?";
    public static final String DELETE_FILE = "update tasks set fileName=null where id=?";
}
