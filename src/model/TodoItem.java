package model;

import util.ConsoleColor;

// 1차 상속: 기본 할 일
public class TodoItem extends TodoBase {
    private boolean isDone;

    public TodoItem(int id, String task) {
        super(id, task);
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        String status = isDone ? ConsoleColor.GREEN + "[✓] 완료" + ConsoleColor.RESET
                : ConsoleColor.RED + "[ ] 미완료" + ConsoleColor.RESET;
        return String.format("| %-3d | %-20s | %s |", getId(), getTask(), status);
    }
}
