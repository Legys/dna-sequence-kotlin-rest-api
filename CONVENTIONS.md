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
│   ├── dna/
│   │   ├── ContentOperation.kt        # GC content calculations
│   │   ├── DnaSequence.kt            # DNA sequence value class
│   │   ├── MotifSearchOperation.kt   # Motif pattern searching
│   │   ├── ReverseComplementOperation.kt
│   │   └── TransformOperation.kt     # DNA to RNA transcription
│   ├── rna/
│   │   └── RnaSequence.kt            # RNA sequence value class
│   └── SequenceError.kt              # Domain error hierarchy
├── request/
│   ├── FindMotifRequest.kt           # Motif search request
│   ├── GcContentRequest.kt           # GC content calculation request
│   ├── ReverseComplementRequest.kt   # Reverse complement request
│   ├── SequenceValidationRequest.kt  # Sequence validation request
│   └── TransformSequenceRequest.kt   # DNA to RNA transform request
├── response/
│   ├── FindMotifResponse.kt          # Motif positions response
│   ├── GcContentResponse.kt          # GC content percentage response
│   ├── ReverseComplementResponse.kt  # Reverse complemented sequence
│   ├── SequenceValidationResponse.kt # Sequence validation result
│   └── TransformSequenceResponse.kt  # Transformed RNA sequence
├── service/
│   ├── FindMotifService.kt           # Motif search service
│   ├── GcContentService.kt           # GC content calculation service
│   ├── ReverseComplementService.kt   # Reverse complement service
│   ├── SequenceValidationService.kt  # Sequence validation service
│   └── TransformSequenceService.kt   # DNA to RNA transform service
└── SequenceProcessingController.kt   # REST API endpoints

Nuances:

Prefer to use BehaviorSpec and Kotest matchers.
Integration tests do HTTP calls using RestAssured Kotlin DSL
with Extract and Kotest matchers.
There are handy extensions in the `extensions` package of the test project.

