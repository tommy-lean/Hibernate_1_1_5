package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Konstantin", "Tomilin", (byte) 25);
        service.saveUser("Evgenia", "Gavrilova", (byte) 23);
        service.saveUser("Ivan", "Kuznetsov", (byte) 27);
        service.saveUser("Tomas", "Shelby", (byte) 33);
        for (User user : service.getAllUsers()) {
            System.out.println(user);
        }
        service.removeUserById(3);
        service.cleanUsersTable();
        service.dropUsersTable();
        Util.closeSessionFactory();
    }
}
