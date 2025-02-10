package model;

import util.ConsoleColor;

// 2차 상속: 마감 기한이 있는 할 일
public class DeadlineTodoItem extends TodoItem {
    private final String dueDate;

    public DeadlineTodoItem(int id, String task, String dueDate) {
        super(id, task);
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        String status = isDone() ? ConsoleColor.GREEN + "[✓] 완료" + ConsoleColor.RESET
                : ConsoleColor.RED + "[ ] 미완료" + ConsoleColor.RESET;
        return String.format("| %-3d | %-20s | %s | " + ConsoleColor.YELLOW + "%-10s" + ConsoleColor.RESET + " |",
                getId(), getTask(), status, dueDate);
    }
}
