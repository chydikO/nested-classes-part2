package org.itstep;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Application {
    int i;

    Comparator cmp = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return i;
        }
    };

    public static void main(String[] args) {
        // 1. Nested static classes
        Human.Cap cap = new Human.Cap();
//        cap.color= 'Red'; // no access

        // 2. Inner non-static classes
        Human human = new Human("Red Cap");
        System.out.println(human);
        Human.Heart heart = human.new Heart();
        System.out.println(heart);

        User user = new User("admin", "qwerty");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User.UserInfo userInfo = user.new UserInfo();  // user.getUserInfo();
        userInfo.authenticate(login, password);
        System.out.println("Login authenticated user: " + userInfo.getLogin());

        // 3. Local classes
        String message = "Hello World";
        class Point implements Serializable {
            int x;
            int y;
             int count; // static fields since jdk 16 static
             void initCount() { // static methods since jdk 16 static

            }
            Point(int x, int y) {
                this.x = x;
                this.y = y;
                System.out.println(message);
            }
        }
        //message = "test";
        Point point = new Point(10, 20);
        System.out.println("point.x = " + point.x);
        System.out.println("point.y = " + point.y);

        String[] strings = {"one", "two", "tree", "four", "five", "six", "seven"};
        class SortByLength implements Comparator {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof String && o2 instanceof String) {
                    return ((String)o1).length() - ((String)o2).length();
                }
                return 0;
            }
        }
        System.out.println(Arrays.toString(strings));
        Arrays.sort(strings, new SortByLength());
        System.out.println(Arrays.toString(strings));

        // 4. Anonymous classes
//        class SortStringDescending implements Comparator {
//            @Override
//            public int compare(Object o1, Object o2) {
//                String s1 = o1.toString();
//                String s2 = o2.toString();
//                return s2.compareTo(s1);
//            }
//        }
//        Arrays.sort(strings, new SortStringDescending());
        Arrays.sort(strings, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                String s1 = o1.toString();
                String s2 = o2.toString();
                return s2.compareTo(s1);
            }
        });
    }
}


class MathUtil {

    enum ActionType {
        ADD, MINUS
    }

    interface Action {

    }
}

class Human {
    private String name;
    private Cap cap;
    private Heart heart;

    static class Cap {
        private String color;

        @Override
        public String toString() {
            return "Cap{" +
                    "color='" + color + '\'' +
                    '}';
        }
    }

    class Heart {
         int count; // static fields since jdk 16 static

         void initCount() { // static methods since jdk 16 static

        }
        //String name;

        @Override
        public String toString() {
            return "I heart " + Human.this.name;
        }
    }

    Human(String name) {
        this.name = name;
        this.cap = new Human.Cap();
        this.cap.color = "Red";
        this.heart = this.new Heart();
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", cap=" + cap +
                ", heart=" + heart +
                '}';
    }
}

class User {
    private final String login;
    private final String password;
    private boolean isAuthenticate;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserInfo getUserInfo() {
        return new UserInfo();
    }

    public class UserInfo {
        public void authenticate(String login, String password) {

        }

        public String getLogin() {
            return isAuthenticate ? login : null;
        }
    }
}
