Requirements:

- The codebase uses Kotlin, Spring Boot.
- Uses the latest versions of the frameworks.
- All the code is formatted with KtLint.
- Code is idiomatic Kotlin with basic FP principles using Result for error handling.
  Business logic should avoid mutations and side effects whenever is possible.
  Decompose code from complex functions into smaller.

The codebase is structured in Vertical Slices with Clean Architecture.
Structure example from this project:

src/kotlin/com/example/yota_pay/application/features/sequence_processing/
├── domain/
│ ├── dna/
│ │ ├── ContentOperation.kt        
│ │ ├── DnaSequence.kt            
│ │ ├── MotifSearchOperation.kt   
│ │ ├── ReverseComplementOperation.kt
│ │ └── TransformOperation.kt     
│ ├── rna/
│ │ └── RnaSequence.kt            
│ └── SequenceError.kt              
├── request/
│ ├── FindMotifRequest.kt           
│ ├── GcContentRequest.kt           
│ ├── ReverseComplementRequest.kt   
│ ├── SequenceValidationRequest.kt
│ └── TransformSequenceRequest.kt   
├── response/
│ ├── FindMotifResponse.kt          
│ ├── GcContentResponse.kt          
│ ├── ReverseComplementResponse.kt  
│ ├── SequenceValidationResponse.kt
│ └── TransformSequenceResponse.kt  
├── service/
│ ├── FindMotifService.kt           
│ ├── GcContentService.kt           
│ ├── ReverseComplementService.kt   
│ ├── SequenceValidationService.kt  
│ └── TransformSequenceService.kt   
└── SequenceProcessingController.kt

Nuances:

Prefer to use BehaviorSpec and Kotest matchers.
Integration tests do HTTP calls using RestAssured Kotlin DSL
with Extract and Kotest matchers.
There are handy extensions in the `extensions` package of the test project.

