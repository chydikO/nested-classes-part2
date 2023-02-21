package org.itstep;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

/*
Command line runner

>>> echo Hello World
Hello World
>>> calc 2+2
4
>>> time
19:50:30
>>> exit
Bye

Відкрити клас демо
Додавти нову команду calc, що дзволяє розрахувати аріфметичний вираз (наприклад, 2+2)
В методі getActinMap() зареєструвати всі action через анонімні класи
Reference materi
 */
public class Demo {
    public static void main(String[] args) {
        // Map: CommandName (key) -> Action
        //      "echo" -> ActionEcho
        //      "calc" -> ActionCalc
        // ...
        ActionMap actionMap = getActionMap();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(">>> ");
            String line = scanner.nextLine(); // echo Hello World
            if (!line.isBlank()) {
                String[] parts = line.split("\\s");
                String command = parts[0];   // echo
                Action action = actionMap.get(command);
                if(action == null) {
                    continue;
                }
                Object result;
                String[] arguments = (parts.length > 1) ?
                        Arrays.copyOfRange(parts, 1, parts.length)
                        : new String[0];
                result = action.execute(arguments); // Hello, World
                System.out.println(result);
            }
        }
    }

    private static ActionMap getActionMap() {
        ActionMapImpl actionMap = new ActionMapImpl();
        actionMap.put("echo", new Action() {
            @Override
            public Object execute(String... args) {
                    return String.join(" ", args);
                }
        });
        actionMap.put("help", new Action() {
            @Override
            public Object execute(String... args) {
                return """
                Command Line Runner:
                
                Support commands: help, time, echo, exit
                """;
            }
        });
        actionMap.put("calc", new Action() {
            /*@Override
            public Object execute(String... args) {
                String[] result = args[0].split("\\+");
                return Integer.parseInt(result[0]) + Integer.parseInt(result[1]);
            }*/

            @Override
            public Object execute(String... args) {
                String[] strings = args[0].replaceAll("\\s+", " ").split("\\W");
                String[] operator = args[0].replaceAll("\\s+", " ").split("\\w");

                int a = Integer.parseInt(strings[0]);
                int b = Integer.parseInt(strings[1]);

                String operators = operator[operator.length - 1];
                return operation(a, b, operators);
            }

            private int operation(int a, int b, String operators) {
                switch (operators) {
                    case "*":
                        return a * b;
                    case "+":
                        return a + b;
                    case "-":
                        return a - b;
                    case "/":
                        return a / b;
                    default:
                        return 0;
                }
            }
        });

        actionMap.put("time", new Action() {
            @Override
            public Object execute(String... args) {
                return LocalTime.now().toString();
            }
        });
        actionMap.put("exit", new Action() {
            @Override
            public Object execute(String... args) {
                System.out.println("Bye");
                System.exit(0);
                return null;
            }
        });

        /*ActionMapImpl actionMap = new ActionMapImpl();
        actionMap.put("echo", new EchoAction());
        actionMap.put("help", new HelpAction());
        actionMap.put("calk", new CalkAction());
        actionMap.put("time", new CurrentTimeAction());
        actionMap.put("exit", new ExitAction());*/
        return actionMap;
    }
}

interface Action {
    Object execute(String... args);
}

interface ActionMap {
    void put(String command, Action action);
    Action get(String command);
}

class ActionMapImpl implements ActionMap {

    private static class CommandAction {
        String command;
        Action action;

        public CommandAction(String command, Action action) {
            this.command = command;
            this.action = action;
        }
    }

    private CommandAction[] commandActions = new CommandAction[0];

    @Override
    public void put(String command, Action action) {
        commandActions = Arrays.copyOf(commandActions, commandActions.length + 1);
        commandActions[commandActions.length - 1] = new CommandAction(command, action);
    }

    @Override
    public Action get(String command) {
        Action action = null;
        for (CommandAction commandAction : commandActions) {
            if (command.equals(commandAction.command)) {
                action = commandAction.action;
                break;
            }
        }
        return action;
    }
}
/*
class EchoAction implements Action {
    @Override
    public Object execute(String... args) {
        return String.join(" ", args);
    }
}

class CurrentTimeAction implements Action {
    @Override
    public Object execute(String... args) {
        return LocalTime.now().toString();
    }
}

class CalkAction implements Action {
    @Override
    public Object execute(String... args) {
        String[] result = args[0].split("\\+");
        return Integer.parseInt(result[0]) + Integer.parseInt(result[1]);
    }
}

class ExitAction implements Action {
    @Override
    public Object execute(String... args) {
        System.out.println("Bye");
        System.exit(0);
        return null;
    }
}

class HelpAction implements Action {

    @Override
    public Object execute(String... args) {
        return """
                Command Line Runner:
                
                Support commands: help, time, echo, exit
                """;
    }
}

 */

