# backend-server

## 📌 Branch 전략 ##
# Branch	종류
- main	배포용 브랜치 (항상 안정적인 상태 유지, 허가없이 건들지 말기)
- develop	기능 개발 통합 브랜치 (pull request하고 동료들에게 merge요청, 확인이 오래걸리면 스스로 merge)
- feature/{이슈번호}{간단한설명}	새로운 기능 개발 브랜치
- fix/{이슈번호}{간단한설명}	버그 수정 브랜치
- hotfix/{이슈번호}{간단한설명}	긴급 수정 브랜치
- refactor/{이슈번호}{간단한설명}	리팩토링 브랜치
- chore/{이슈번호}{간단한설명}	기타 설정, 패키지 변경 등
# Branch    설명
1. 기능개발이 완료된 브랜치는 develop브랜치에 merge합니다.
2. merge된 Branch는 삭제합니다.
</br></br>
✅ 예시
- feature/#12-login-api
- fix/#17-cors-error
- chore/#20-env-setting
</br></br>
✅ Git 사용 규칙
# 커밋 메시지 형식
- #이슈번호 <타입>: <변경 요약> 
</br>
- <타입> 종류</br>
태그 이름	설명</br>
[chore]	코드 수정, 내부 파일 수정</br>
[feat]	새로운 기능 구현</br>
[add]	FEAT 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 파일 생성</br>
[hotfix]	issue나 QA에서 급한 버그 수정에 사용</br>
[fix]	버그, 오류 해결</br>
[del]	쓸모 없는 코드 삭제</br>
[docs]	README나 WIKI 등의 문서 개정</br>
[correct]	주로 문법의 오류나 타입의 변경, 이름 변경에 사용</br>
[move]	프로젝트 내 파일이나 코드의 이동</br>
[rename]	파일 이름 변경이 있을 때 사용</br>
[improve]	향상이 있을 때 사용</br>
[refactor]	전면 수정이 있을 때 사용</br>
[test]	테스트 코드 추가 시 사용 </br>

