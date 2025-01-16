### Conventions

- Code formatting using KtLint
- Aider https://aider.chat/
- DeepSeek v3 via https://app.hyperbolic.xyz/models/deepseek-v3 API

## AI cooperation use cases:

### Planning the feature

The idea is to gather the information about the problem and throw it into the chat window
to brainstorm it resulting in a detailed tech plan with business logic implementation,
tests, nice to have features.

This allows you to have a look at the problem at another view as well as expanding your
solution with additional ideas or criticize your proposed solution pointing at
weak spots.

Doesn't require you to do it in a terminal session with Aider.

### Planning the technical implementation

To start planning the technical implementation after reviewing the idea you could
simply copy the LLM output from the chat window, navigate to the terminal with
Aider session and use `/paste`.

### Approach coding

At this point you will have a detailed technical plan with steps.
You can start generating the code by doing something like this:

> `/code let's start from step 1 following our plan`

The idea is to have an iterative process introducing changes in small portions
to preserve the context.

### Fixing Tests Loop

1. Run tests, see failures.
2. Use `/ask` to provide context about what has happened.
3. Receive an assumption.
4. Use a plain command to perform the action.

---

#### Step 1:

**Input:**

>
`/ask this line: service.validateSequence("").shouldBeFalse() produces: io.kotest.assertions.AssertionFailedError: expected:<false> but was:<true>`

**Output:**

Ah, I see the issue! The test expects an empty string to be invalid, but the current implementation in
`SequenceValidationService` returns `true` for empty strings. This is because the `all()` function returns `true` for
empty collections/sequences by default.

Let's fix the `SequenceValidationService` to explicitly handle empty strings:

`src/main/kotlin/com/example/yota_pay/application/features/sequence_processing/service/SequenceValidationService.kt`

```kotlin
package com.example.yota_pay.application.features.sequence_processing.service

import org.springframework.stereotype.Service

@Service
class SequenceValidationService {
    fun validateSequence(sequence: String): Boolean {
        if (sequence.isEmpty()) return false
        val validCharacters = setOf('A', 'T', 'C', 'G')
        return sequence.all { it in validCharacters }
    }
}
```

#### Step 2:

Input:

> make these changes

**Output:**

The changes ensure empty strings are properly handled as invalid DNA sequences while maintaining the existing validation
logic for non-empty strings.

Here’s the updated file with the changes applied:

`src/main/kotlin/com/example/yota_pay/application/features/sequence_processing/service/SequenceValidationService.kt`

```kotlin

package com.example.yota_pay.application.features.sequence_processing.service

import org.springframework.stereotype.Service

@Service
class SequenceValidationService {
    fun validateSequence(sequence: String): Boolean {
        +if (sequence.isEmpty()) return false
        val validCharacters = setOf('A', 'T', 'C', 'G')
        return sequence.all { it in validCharacters }
    }
}
```

Tokens: 3.5k sent, 135 received.

Applied edit to:
`src/main/kotlin/com/example/yota_pay/application/features/sequence_processing/service/SequenceValidationService.kt`

Commit: 7381ebd fix: handle empty strings as invalid DNA sequences in SequenceValidationService