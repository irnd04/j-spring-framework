# core 구현 기능
### Annotation
- `@Authwired`
- `@Bean`
- `@Component`
- `@Configuration`
- `@Controller`
- `@Order`
- `@PathVariable`
- `@Repository`
- `@RequestBody`
- `@RequestMapping`
- `@ResponseBody`
- `@Service`

### View
- HTML View
- JSON View

### Etc
- Interceptor
- Filter
- ExceptionHandler
- Converter

# app
- 가게 목록 조회
  ```
  curl localhost:8080/shops -X GET -H 'Content-Type: application/json'
  ```
- 가게 단건 조회
  ```
  curl localhost:8080/shops/1 -X GET -H 'Content-Type: application/json'
  ```
- 가게 추가
  ```
  curl localhost:8080/shops -X POST -H 'Content-Type: application/json' -d "{\"name\": \"우아한 가게\", \"address\": \"배민시 배민동 123-1\"}"
  ```
- 가게 수정
  ```
  curl localhost:8080/shops/1 -X PUT -H 'Content-Type: application/json' -d "{\"name\": \"우아한 가게\", \"address\": \"배민시 배민동 123-1\"}"
  ```
- 가게 삭제
  ```
  curl localhost:8080/shops/1 -X DELETE -H 'Content-Type: application/json'
  ```