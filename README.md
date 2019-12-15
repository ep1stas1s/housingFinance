# 주택 금융 서비스 API개발

### 주택금융 공급현황 분석 서비스

- 국내 주택금융 신용보증 기관으로부터 년도별 각 금융기관(은행)에서 신용보증한 금액에 대한 데이터(CSV)가 주어집니다.
- 이를 기반으로 아래 기능명세에 대한 API를 개발하고 각 기능별 Unit Test 코드를 개발하세요.

## 1. 개발 환경

- Java 1.8
- Gradle
- Spring boot, JPA
- H2
- JUnit5
- Asciidoctor, Lombok
- Window, Ubuntu16.04
- IntelliJ
- Git

## 2. 빌드 및 실행 방법

    $ git clone https://github.com/ep1stas1s/housingFinance
    $ cd housingFinance
    $ chmod +x gradlew
    $ ./gradlew clean build
    $ java -jar build/libs/housingfinance*.jar

- 실행 후, [http://localhost:8080/docs/guide.html](http://localhost:8080/docs/guide.html)에서 API를 조회

## 3. 기능 목록 및 문제해결 전략

### 3.1. 데이터 파일에서 각 레코드를 데이터베이스에 저장

- `/api/institute`
- 입력받은 file name을 `resources`에서 찾아서 database에 초기화
- `INSTITUTE` 테이블의 `CODE`는 `bnk0001`과 같이 생성
- `CSV`파일을 그대로 넣으면 확장성이 없다고 생각하여, `INSTITUTE`와 `MONTLY_CREDIT`으로 나누어 저장
- 파일을 직접 주지는 않지만, 데이터 추가가 있으므로 `POST`로 구현
- 성공 시, 결과로 status code(200)만 반환
- CSV parsing
    - `,`를 기준으로만 파싱할 경우, `"`로 감싸져있는 숫자는 바로 파싱이 되지 않음
    - Apache commons의 csv library 사용
    - Header parsing이 되지 않아, header와 각 row를 가지는 객체 생성
        - 모든 row가 숫자이므로, Integer 배열을 반환하는 객체(`IntegerRowCsvData`)로 구현

#### 3.1.1. Request curl

    $ curl 'http://localhost:8080/api/institutes/init' -i -X POST \
        -H 'Content-Type: application/json' \
        -d '{
              "fileName" : "data.csv"
            }'

#### 3.1.2. Response

- Status code 200 반환

    HTTP/1.1 200 OK
    Date: Sun, 15 Dec 2019 12:49:54 GMT

### 3.2. 주택금융 공급 금융기관(은행) 목록

- `/api/institutes`
- 금융기관의 모든 목록을 조회
- JpaRepository에서 기본적으로 제공하는 `findAll()` 사용
- 도메인을 DTO 객체로 변환 및 일급컬렉션 적용

#### 3.2.1. Request curl

    $ curl 'http://localhost:8080/api/institutes' -i -X GET \
        -H 'Accept: application/json;charset=UTF-8'

#### 3.2.2. Response body

    {
      "institutesInfos" : [ {
        "instituteName" : "주택도시기금1",
        "instituteCode" : "bnk0001"
      }, {
        "instituteName" : "국민은행",
        "instituteCode" : "bnk0002"
      }, 
    
      ...
    
      {
        "instituteName" : "기타은행",
        "instituteCode" : "bnk0009"
      } ]
    }

### 3.3. 연도별 각 금융기관의 지원금액 합계

- `/api/credits/total`
- Database(DB) 조회에 사용되는 resource를 줄이기 위해 DB 조회를 최소화
    - DB 조회를 최소화하기 위해, `JPQL`, `projection` 적용
    - `GROUP BY`와 `SUM()`을 사용해 월별 기록을 연도별 조회로 변환 
- `JPQL`로 조회한 데이터를 stream을 사용해 grouping

#### 3.3.1. Request curl

    $ curl 'http://localhost:8080/api/credits/total' -i -X GET \
        -H 'Accept: application/json;charset=UTF-8'

#### 3.3.2. Response body

    {
      "detailYearlyCreditInfoDtos" : [ {
        "year" : 2005,
        "totalAmount" : 48016,
        "detailAmount" : [ {
          "instituteName" : "주택도시기금1",
          "amount" : 22247
        }, 
    
        ...
    
        {
          "instituteName" : "외환은행",
          "amount" : 1732
        }, {
          "instituteName" : "기타은행",
          "amount" : 1376
        } ]
      },
    
      ...
    
      }, {
        "year" : 2017,
        "totalAmount" : 295126,
        "detailAmount" : [ {
          "instituteName" : "주택도시기금1",
          "amount" : 85409
        }, {
          "instituteName" : "국민은행",
          "amount" : 31480
        }, {
    
         ...
    
        }, {
          "instituteName" : "기타은행",
          "amount" : 36057
        } ]
      } ]
    }

### 3.4. 각 연도별 각 기관의 전체 지원금액 중에서 가장 큰 금액과 그 기관명 출력

- `/api/credits/top`
- 각 연도별(2005~2017년) 모든 월을 합한 지원금액이 가장 큰 금액, 기관명
- Database(DB) 조회에 사용되는 resource를 줄이기 위해 DB 조회를 최소화
    - DB 조회를 최소화하기 위해, `JPQL`, `projection` 적용
    - `GROUP BY`와 `SUM()`을 사용해 월별 기록을 연도별 조회로 변환 
- `JPQL`로 조회한 데이터를 stream을 사용해 grouping

#### 3.4.1. Request curl

    $ curl 'http://localhost:8080/api/credits/top' -i -X GET \
        -H 'Accept: application/json;charset=UTF-8'

#### 3.4.2. Response body

    {
      "year" : 2014,
      "instituteName" : "주택도시기금1"
    }

### 3.5. 전체 연도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액

- `/api/credits/{instituteId}/avg-min-max`
    - 확장성을 고려하여 각 기관의 `ID`를 path variable로 입력받아 조회 
        - 외환은행의 경우 `{instituteId}`에 `8` 기입
- `{instituteId}`에 해당하는 기관이 없을 경우 custom exception 발생

#### 3.5.1. Request curl

    $ curl 'http://localhost:8080/api/credits/8/avg-min-max' -i -X GET \
        -H 'Accept: application/json;charset=UTF-8'

#### 3.5.2. Response body

    {
      "instituteName" : "외환은행",
      "minAverage" : {
        "year" : 2008,
        "amount" : 78
      },
      "maxAverage" : {
        "year" : 2015,
        "amount" : 1702
      }
    }

    {
    	"bank": "외환은행",
    	"support_amount":
    	[
    		{"year": 2008, "amount": 78},
    		{"year": 2015, "amount": 1702}
    	]
    }

## 4. 제약사항

- API 기능명세에 나온 API를 개발
- `ORM`을 사용하여 각 `entity`를 정의하고 `repository`를 개발
    - `Enitiy` 디자인은 자유롭게
    - 주택금융 공급기관은 독립 `entity`(기관명, 기관코드)로 디자인
        - institute_name, institute_code
- 단위 테스트
- 모든 입/출력은 JSON 형태
- `README.md` 파일 추가
    - 개발 framework, 문제해결 전략, 빌드 및 실행 방법
- HTTP Method는 알아서 선택 (GET, POST, PUT, DEL)