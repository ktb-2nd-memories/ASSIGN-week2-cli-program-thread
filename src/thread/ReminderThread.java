package thread;

import service.TodoList;
import util.ConsoleColor;

import java.util.concurrent.BlockingQueue;

public class ReminderThread extends Thread {
    private final BlockingQueue<String> overdueTasksQueue;

    public ReminderThread(TodoList todoList) {
        this.overdueTasksQueue = todoList.getOverdueTasksQueue();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                synchronized (overdueTasksQueue) {
                    if (overdueTasksQueue.isEmpty()) {
                        overdueTasksQueue.wait(); // 💡 새로운 데이터가 들어올 때까지 대기
                    }
                }

                String overdueTask = overdueTasksQueue.take(); // 💡 마감 기한 초과된 할 일 가져오기
                System.out.println(ConsoleColor.YELLOW + "⚠️ 미완료된 마감 기한 초과 할 일: " + overdueTask + ConsoleColor.RESET);

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
