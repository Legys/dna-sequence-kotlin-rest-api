Requirements:

- The codebase uses Kotlin, Spring Boot.
- Uses the latest versions of the frameworks.
- All the code is formatted with KtLint.
- Code is idiomatic Kotlin with basic FP principles using Result for error handling.
  Business logic should avoid mutations and side effects whenever is possible.
  Decompose code from complex functions into smaller.

The codebase is structured in Vertical Slices with Clean Architecture.
Structure example should be followed:

src/kotlin/com/wordy_app/wordy/application/features/
└── study/
├── domain/
│ └── StudyStage.kt
├── request/
│ └── UpdateStudyProgressRequest.kt
├── response/
│ ├── StudyDueResponse.kt
│ └── StudyProgressResponse.kt
├── repositories/
│ └── StudyProgressRepository.kt
├── services/
│ ├── CreateStudyProgressService.kt
│ ├── GetDueStudyService.kt
│ └── UpdateStudyProgressService.kt
└── StudyController.kt

Nuances:

Prefer to use BehaviorSpec and Kotest matchers.
Integration tests do HTTP calls using RestAssured Kotlin DSL
with Extract and Kotest matchers.
There are handy extensions in the `extensions` package of the test project.

