# StatusService

## 커밋 컨벤션

이 문서는 우리 프로젝트의 일관된 커밋 메시지 히스토리를 만들기 위한 규칙을 정의합니다. 우리는 Conventional Commits 명세를 기반으로 하며, 모든 커밋 메시지는 아래 형식과 규칙을 따라야 합니다.

## 커밋 메시지 구조
모든 커밋 메시지는 다음과 같은 구조를 가집니다.
```
type: subject
```
  - **type**: 커밋의 종류를 나타내며, 아래에 정의된 유형 중 하나여야 합니다. (필수)
  - **subject**: 커밋 변경 사항에 대한 간결한 요약입니다. (필수)

### 커밋 유형

| 유형 (Type) | 설명 |
| :--- | :--- |
| **feat** | ✨ 새로운 기능 추가 (a new feature) |
| **fix** | 🐛 버그 수정 (a bug fix) |
| **docs** | 📚 문서 수정 (documentation only changes) |
| **style** | 💎 코드 포맷팅, 세미콜론 누락 등 스타일 관련 수정 (no code change) |
| **refactor** | ♻️ 코드 리팩토링 (no bug fix, no new feature) |
| **test** | ✅ 테스트 코드 추가 또는 수정 |
| **perf** | ⚡️ 성능 개선 |
| **build** | 📦 빌드 관련 파일 수정 (e.g., webpack, vite, package.json) |
| **ci** | ⚙️ CI/CD 관련 설정 수정 |
| **chore** | 🧹 기타 자잘한 수정 (e.g., .gitignore, 주석) |
