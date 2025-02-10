# CLI 프로그램 제작 (비동기 프로그램)

**주제** 📅 TodoList: 할 일을 관리하자!<br>
**Skills** JAVA 21, MacOS, IntelliJ, Git & GitHub
<br><br>
이 프로그램은 **스레드를 활용하여 비동기적으로 동작하는 할 일 관리 시스템**으로, 사용자가 할 일을 추가, 조회, 완료 처리, 삭제할 수 있으며, **마감 기한이 있는 할 일을 자동으로 관리**하는 기능을 포함하고 있다.
특히, Thread를 활용하여 **마감 기한이 지난 할 일을 자동으로 감지하고 알림을 제공하는 기능**을 구현하였다.

## 1. 클래스 다이어그램
![image](https://github.com/user-attachments/assets/730c3207-d88c-4dfd-a884-3c59e4ab9450)
### 핵심 클래스 설계
#### 1. Main (메인 실행 클래스)
  - 프로그램의 입력 루프를 담당하며, Scanner를 사용해 사용자의 입력을 처리한다.
  - TimeThread, DeadlineCheckerThread, ReminderThread를 실행하여 비동기적으로 마감 기한 감지 및 알림을 수행한다.

#### 2) TodoBase (할 일의 기본 클래스)
  - 모든 할 일 객체의 공통 속성을 정의하는 추상 클래스.
  - 하위 클래스인 TodoItem과 DeadlineTodoItem이 이를 상속받는다.

#### 3) TodoItem (일반 할 일 클래스)
  - TodoBase를 상속하며, 기본적인 할 일 항목을 나타낸다.
  - markDone() 메서드를 통해 완료 상태로 변경 가능.

#### 4) DeadlineTodoItem (마감 기한이 있는 할 일)
  - TodoItem을 상속하며, 추가적으로 마감 기한 속성(dueDate)을 가진다.
  - toString()을 오버라이딩하여 마감 기한을 포함한 출력 형식을 제공.

#### 5) TodoList (할 일 목록 관리)
  - 모든 할 일(TodoItem, DeadlineTodoItem)을 **ArrayList<TodoItem>**을 통해 관리한다.
  - 할 일 추가, 완료, 삭제, 마감 기한 검사를 수행하는 핵심 로직이 포함된 클래스.
  - 마감 기한 감지 (checkDeadlines())
    - DeadlineCheckerThread가 10초마다 실행하여, 마감 기한이 지난 할 일을 감지한다.
    - 초과된 할 일은 BlockingQueue에 추가하여 ReminderThread가 감지할 수 있도록 한다.
  - 마감 기한 조정 (adjustDeadlineToEndOfDay())
    - 할 일을 추가할 때, 마감 기한을 자동으로 00:07:59로 설정하여 초 단위 비교가 명확하게 이루어지도록 한다.

#### 6) DeadlineCheckerThread (마감 기한 자동 감지 스레드)
  - 10초마다 TodoList.checkDeadlines()를 실행하여 마감 기한을 검사한다.
  - 마감 기한이 초과된 경우 BlockingQueue를 통해 ReminderThread에 알림을 전달한다.

#### 7) ReminderThread (마감 기한 초과 알림 스레드)
  - BlockingQueue를 활용하여 마감 기한이 초과된 할 일이 감지될 때만 실행되도록 구성.
  - wait() & notify()를 사용하여 이벤트 기반 실행을 구현.

#### 8) TimeThread (현재 시간 출력 스레드)
  - 1분마다 현재 시간을 콘솔에 출력하여 사용자에게 정보를 제공.

## 2. 주요 기능
#### 1) 기본 할 일 관리 기능
1. 할 일 추가
    - 사용자가 새로운 할 일을 추가할 수 있다.
    - 마감 기한이 없는 일반 할 일과, 마감 기한이 있는 할 일을 따로 추가할 수 있다.
2. 할 일 목록 조회
    - 현재 등록된 할 일 목록을 테이블 형식으로 출력하여 가독성을 높였다.
3. 할 일 완료 처리
    - 특정 ID의 할 일을 완료 상태로 변경할 수 있다.
4. 할 일 삭제
    - 특정 ID의 할 일을 삭제할 수 있다.

#### 2) 스레드를 활용한 비동기 프로세스
1. 마감 기한 자동 감지 (DeadlineCheckerThread)
    - 10초마다 등록된 할 일 중 마감 기한이 지난 할 일을 검사하고, 초과된 경우 BlockingQueue를 통해 알림을 전달한다.
2. 마감 기한 초과 알림 (ReminderThread)
    - DeadlineCheckerThread가 감지한 마감 기한 초과 할 일을 BlockingQueue에 저장하고, ReminderThread가 이를 감지하여 콘솔에 경고 메시지를 출력한다.
    - 이벤트 기반 실행 (wait() & notify())을 활용하여, 필요할 때만 실행되도록 설계되었다.
3. 현재 시간 주기적 출력 (TimeThread)
    - 1분마다 현재 시간을 출력하여 사용자가 시스템의 진행 상태를 쉽게 파악할 수 있도록 구현했다.

#### 3) 사용자 입력 및 예외 처리
1. 메뉴 입력 검증
    - 존재하지 않는 메뉴 번호 입력 시 오류 메시지를 출력하고 다시 입력을 받는다.
2. 할 일 완료/삭제 시 ID 존재 여부 확인
    - 존재하지 않는 id를 입력하면 "해당 번호의 할 일을 찾을 수 없습니다." 메시지를 출력한다.
3. 마감 기한 입력 검증
    - 올바른 날짜 형식(YYYY-MM-DD)을 입력하도록 검증하고, 잘못된 형식일 경우 "올바른 날짜 형식이 아닙니다." 경고를 출력한다.

## 4. 프로그램 시연
https://github.com/user-attachments/assets/ff40e998-e695-45a1-b0b8-267070f94076
