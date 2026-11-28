# GitHub 컨벤션


| 항목 | 규칙 |
| --- | --- |
| 브랜치 | `main` ← `feature/기능명` |
| 커밋 | `타입: 작업 내용 요약` (예: `feat: 회원가입 Controller 추가`) |
| 머지 | 기능 완성 후 `main`에 merge, 실행 확인 후 push |
| 금지 | `main`에서 직접 작업, DB 비밀번호 커밋, `Servers`·`build`·`target` 커밋 |
| 시작 전 | 항상 `git pull origin main`으로 최신 상태 맞추기 |

---

## 브랜치 전략

브랜치는 세 종류만 씁니다. 기능 하나를 브랜치 하나로 만들고, 완성되면 `main`에 합칩니다.

| 브랜치 | 역할 |
| --- | --- |
| `main` | 모든 기능이 모이는 브랜치, 항상 실행되는 상태 유지 |
| `feature/기능명` | 기능 하나 개발 |
| `fix/내용` | 버그 수정 |

**이름 규칙**

- 영문 소문자와 하이픈: `feature/member-signup`, `fix/login-null`
- `main`에 머지하기 전에 팀 채팅방에 어떤 기능을 머지하는지 한 줄 공유하기
- `main`이 깨진 것을 발견하면 바로 팀에 알리고, 원인이 된 기능의 담당자가 `fix/` 브랜치로 먼저 고치기

---

## 커밋 메시지

커밋 메시지는 `타입: 요약` 한 줄로 씁니다. 요약은 한글로 50자 안쪽, 마침표 없이 씁니다.

| 타입 | 언제 | 예시 |
| --- | --- | --- |
| `feat` | 새 기능 | `feat: 체험단 신청 기능 추가` |
| `fix` | 버그 수정 | `fix: 로그인 실패 시 NullPointerException 수정` |
| `refactor` | 동작 변화 없는 코드 개선 | `refactor: MemberDAO 중복 쿼리 정리` |
| `style` | 화면 CSS, 코드 포맷 | `style: 메인 JSP 레이아웃 정렬` |
| `db` | DDL, Mapper XML, SQL 변경 | `db: MEMBER 테이블에 score 컬럼 추가` |
| `chore` | pom.xml, 설정, .gitignore | `chore: Oracle JDBC 의존성 추가` |
| `docs` | README, 문서 | `docs: 실행 방법 README 작성` |

- 커밋 하나에는 변경 하나만 담기 (여러 기능을 한 번에 커밋하지 않기)
- 컴파일이 안 되는 상태로는 push하지 않기
- 설명이 더 필요하면 한 줄 띄고 본문에 이유를 적기

---

## 작업 순서

기능 하나를 개발할 때는 아래 순서를 따릅니다.

1. 최신 main에서 브랜치 만들기

   ```bash
   git checkout main
   git pull origin main
   git checkout -b feature/member-signup
   ```

2. 작업하면서 작은 단위로 커밋

   ```bash
   git add .
   git commit -m "feat: 회원가입 화면과 Controller 추가"
   ```

3. 작업 중간에도 브랜치를 원격에 올려 백업

   ```bash
   git push origin feature/member-signup
   ```

4. 기능이 완성되면 main 최신 내용을 내 브랜치에 먼저 반영하고, 충돌 해결 후 로컬 Tomcat에서 실행 확인

   ```bash
   git pull origin main
   ```

5. main에 머지하고 push

   ```bash
   git checkout main
   git pull origin main
   git merge feature/member-signup
   git push origin main
   ```


### 발표·제출 전 버전 표시

중간 발표나 최종 제출처럼 "이 시점의 코드"를 남겨야 할 때는 `main`에 태그를 붙입니다.
나중에 `main`이 깨져도 태그를 붙인 시점의 코드로 언제든 돌아갈 수 있습니다.

```bash
git checkout main
git pull origin main
git tag v1.0-midterm
git push origin v1.0-midterm
```
