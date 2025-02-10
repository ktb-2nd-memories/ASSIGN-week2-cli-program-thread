package thread;

import service.TodoList;

import java.text.SimpleDateFormat;

public class DeadlineCheckerThread extends Thread {
    private final TodoList todoList;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DeadlineCheckerThread(TodoList todoList) {
        this.todoList = todoList;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                todoList.checkDeadlines();
                Thread.sleep(10000); // 10초마다 마감 기한 확인
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
