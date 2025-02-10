package service;

import model.DeadlineTodoItem;
import model.TodoItem;
import util.ConsoleColor;
import util.Messages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// 할 일 목록 관리
public class TodoList {
    private final ArrayList<TodoItem> todoItems;
    private int counter;
    private final BlockingQueue<String> overdueTasksQueue = new LinkedBlockingQueue<>();

    public TodoList() {
        this.todoItems = new ArrayList<>();
        this.counter = 1;
    }

//    public synchronized void checkDeadlines() {
//        Date now = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        for (TodoItem item : todoItems) {
//            if (item instanceof DeadlineTodoItem) {
//                DeadlineTodoItem deadlineItem = (DeadlineTodoItem) item;
//                try {
//                    Date dueDate = sdf.parse(deadlineItem.getDueDate());
//
//                    Calendar nowCal = Calendar.getInstance();
//                    Calendar dueCal = Calendar.getInstance();
//                    nowCal.setTime(now);
//                    dueCal.setTime(dueDate);
//
//                    boolean isSameDay =
//                            nowCal.get(Calendar.YEAR) == dueCal.get(Calendar.YEAR) &&
//                                    nowCal.get(Calendar.DAY_OF_YEAR) == dueCal.get(Calendar.DAY_OF_YEAR);
//
//                    if (!deadlineItem.isDone() && dueDate.before(now) && !isSameDay) {
//                        System.out.println(ConsoleColor.RED + Messages.PAST_DEADLINE + deadlineItem.getTask() + ConsoleColor.RESET);
//
//                        // 💡 공유 자원에 추가 (스레드 간 통신)
//                        overdueTasksQueue.put(deadlineItem.getTask());
//                        synchronized (overdueTasksQueue) {
//                            overdueTasksQueue.notify(); // 알림 전송
//                        }
//                    }
//                } catch (ParseException | InterruptedException e) {
//                    System.out.println(ConsoleColor.RED + Messages.INVALID_DATE_FORMAT + deadlineItem.getDueDate() + ConsoleColor.RESET);
//                }
//            }
//        }
//    }

    public synchronized void checkDeadlines() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (TodoItem item : todoItems) {
            if (item instanceof DeadlineTodoItem) {
                DeadlineTodoItem deadlineItem = (DeadlineTodoItem) item;
                try {
                    Date dueDate = sdf.parse(deadlineItem.getDueDate());

                    if (!deadlineItem.isDone() && dueDate.before(now)) {
                        System.out.println(ConsoleColor.RED + Messages.PAST_DEADLINE + deadlineItem.getTask() + ConsoleColor.RESET);

                        // 💡 공유 자원에 추가 (스레드 간 통신)
                        overdueTasksQueue.put(deadlineItem.getTask());
                        synchronized (overdueTasksQueue) {
                            overdueTasksQueue.notify(); // 알림 전송
                        }
                    }
                } catch (ParseException | InterruptedException e) {
                    System.out.println(ConsoleColor.RED + Messages.INVALID_DATE_FORMAT + deadlineItem.getDueDate() + ConsoleColor.RESET);
                }
            }
        }
    }

    public BlockingQueue<String> getOverdueTasksQueue() {
        return overdueTasksQueue;
    }

    public synchronized void sendReminders() {
        for (TodoItem item : todoItems) {
            if (!item.isDone()) {
                System.out.println("\n" + ConsoleColor.YELLOW + Messages.REMINDER + item.getTask() + ConsoleColor.RESET);
            }
        }
    }

    public void addTask(String task) {
        todoItems.add(new TodoItem(counter++, task));
        System.out.println(ConsoleColor.GREEN + Messages.TASK_ADDED + task + ConsoleColor.RESET);
    }

    public void addTaskWithDeadline(String task, String dueDate) {
        String adjustedDueDate = adjustDeadlineToEndOfDay(dueDate);
        if (!isValidDateFormat(adjustedDueDate)) {
            System.out.println(ConsoleColor.RED + Messages.INVALID_DATE + ConsoleColor.RESET);
            return;
        }
        todoItems.add(new DeadlineTodoItem(counter++, task, adjustedDueDate));
        System.out.println("\n" + ConsoleColor.GREEN + Messages.TASK_ADDED_DEADLINE + task + " (마감일: " + adjustedDueDate + ")" + ConsoleColor.RESET);
    }

    private String adjustDeadlineToEndOfDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0); // 12:07 AM 설정
            calendar.set(Calendar.MINUTE, 17);
            calendar.set(Calendar.SECOND, 59); // 💡 초 단위까지 설정
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            return date; // 유효하지 않은 날짜 입력 시 그대로 반환
        }
    }

    public void showTasks() {
        if (todoItems.isEmpty()) {
            System.out.println(ConsoleColor.RED + Messages.NO_TASKS + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.CYAN + Messages.TASK_LIST_HEADER + ConsoleColor.RESET);
            for (TodoItem item : todoItems) {
                System.out.println(item);
            }
            System.out.println(Messages.TASK_LIST_FOOTER);
        }
    }

    public void markTaskDone(int taskId) {
        for (TodoItem item : todoItems) {
            if (item.getId() == taskId) {
                item.markDone();
                System.out.println(ConsoleColor.GREEN + Messages.TASK_COMPLETED + item.getTask() + ConsoleColor.RESET);
                return;
            }
        }
        System.out.println(ConsoleColor.RED + Messages.TASK_NOT_FOUND + ConsoleColor.RESET);
    }

    public void deleteTask(int taskId) {
        if (todoItems.removeIf(item -> item.getId() == taskId)) {
            System.out.println(ConsoleColor.RED + Messages.TASK_DELETED + taskId + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.RED + Messages.TASK_NOT_FOUND + ConsoleColor.RESET);
        }
    }

    private boolean isValidDateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
