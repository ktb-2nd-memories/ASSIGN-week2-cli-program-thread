package util;

public class Messages {
    public static final String MENU = "\n========= TODO LIST =========\n 1. 할 일 추가\n 2. 마감 기한 할 일 추가\n 3. 할 일 목록 보기\n 4. 할 일 완료 체크\n 5. 할 일 삭제\n 6. 종료\n=============================";
    public static final String INPUT_PROMPT = " 선택 >> ";
    public static final String ENTER_TASK = "할 일을 입력하세요: ";
    public static final String ENTER_DUE_DATE = "마감일을 입력하세요 (예: 2025-01-31): ";
    public static final String ENTER_COMPLETE_ID = "완료할 할 일 번호를 입력하세요: ";
    public static final String ENTER_DELETE_ID = "삭제할 할 일 번호를 입력하세요: ";
    public static final String EXIT_PROGRAM = "프로그램을 종료합니다.";
    public static final String INVALID_INPUT = "🚫 올바른 번호를 입력하세요.";
    public static final String TASK_ADDED = "✔ 할 일이 추가되었습니다: ";
    public static final String TASK_ADDED_DEADLINE = "✔ 마감 기한이 있는 할 일이 추가되었습니다: ";
    public static final String INVALID_DATE = "🚫 올바른 날짜 형식이 아닙니다. (예: 2025-01-31)";
    public static final String NO_TASKS = "🚫 할 일이 없습니다.";
    public static final String TASK_LIST_HEADER = "-------------------- [할 일 목록] --------------------\n| ID  | 할 일                | 상태      | 마감일       |\n|-----|----------------------|----------|-------------|";
    public static final String TASK_LIST_FOOTER = "----------------------------------------------------";
    public static final String TASK_COMPLETED = "✔ 할 일이 완료되었습니다: ";
    public static final String TASK_NOT_FOUND = "🚫 해당 번호의 할 일을 찾을 수 없습니다.";
    public static final String TASK_DELETED = "❌ 할 일이 삭제되었습니다: ";
    public static final String PAST_DEADLINE = "🚨 마감 기한이 지난 할 일: ";
    public static final String INVALID_DATE_FORMAT = "⚠️ 잘못된 날짜 형식: ";
    public static final String REMINDER = "⏰ 아직 완료되지 않은 할 일: ";
}
