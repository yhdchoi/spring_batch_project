# Spring Batch: API Cost Management System
## API usage cost management batch system
### Requirements
- 고객은 유료 API 사용한다
- 서비스팀은 유료 API 사용 이력을 남긴다
- 유료 API 사용 이력을 파일로 정산팀에 전달한다 (Random creation)
- 정산팀은 1일 단위로 정산을 한다
- 매주 금요일 1주일 치 1일 정산을 집계해서 DB에 저장하고 고객에게 email로 전달

### Functions
- API 호출 이력 파일 만드는 batch
- 일 단위 정산 batch
- 주단위 정산 batch
