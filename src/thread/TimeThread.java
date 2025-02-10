package thread;

import util.ConsoleColor;

import java.util.Date;

public class TimeThread extends Thread {
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println("\n" + ConsoleColor.YELLOW + "현재 시간: " + new Date() + ConsoleColor.RESET);
                Thread.sleep(60000); // 1분(60초)마다 시간 출력
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
