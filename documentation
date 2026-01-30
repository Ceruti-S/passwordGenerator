# Technical Documentation - Password Generator 

This document provides a detailed breakdown of the internal architecture, class responsibilities, and technical implementation of the Password Generator application.

---

## Architecture Overview

The application follows a modular approach, separating the execution logic, the core engine, and the user interface.

1. **Entry Point:** `MainClass`
2. **Logic Engine:** `Generator` (Backend & Algorithm)
3. **User Interface:** `PasswordGeneratorGUI` (Swing-based GUI)

---

## Class Analysis: `Generator.java` (The Engine)

The `Generator` class is the core of the system. It handles the UI lifecycle, password generation, and security validation.

### 1. Application Initialization (`generateGUI`)
* **OS Native Look & Feel:** The method attempts to set the `UIManager` to the system's native look and feel to ensure the app doesn't look like an outdated Java window.
* **Thread Safety:** Uses `SwingUtilities.invokeLater` to ensure that the GUI is created and updated on the **Event Dispatch Thread (EDT)**, preventing race conditions or UI freezes.

### 2. Generation Algorithm (`generatePassword`)
This method implements a robust generation logic:
* **`SecureRandom` Implementation:** Uses `java.security.SecureRandom` instead of the standard `Random`. This is cryptographically strong and much harder to predict.
* **Collision Avoidance:** A `do-while` loop ensures that no two identical characters are generated consecutively (`nextChar == lastChar`), increasing the visual and structural randomness.
* **Pool Sanitization:** Dynamically builds a character pool based on user preferences (Uppercase, Numbers, Symbols).
* **Optimization for Long Passwords:** If a password is **≥ 128 characters**, the requirement validation is bypassed for performance, as entropy at that length is mathematically sufficient regardless of specific character distribution.

### 3. Advanced Strength Analysis (`isPasswordSafe`)
This is where the "intelligence" of the app resides. It combines the `zxcvbn4j` library with custom heuristic rules:
* **The "128-bit Ceiling":** The analysis is capped at 128 characters. This prevents the application from consuming excessive CPU resources for extremely long strings, as 128 characters provide more than enough data for a definitive security score.
* **Heuristic Scoring System:**
    * **Penalty for Low Variety:** Even if `zxcvbn` gives a high score, the app manually downgrades it to "Orange" if only lowercase letters are used.
    * **Penalty for Length:** Even with high variety, if the password is shorter than 10 characters, it is capped to "Yellow".
* **Color-Coded Feedback:** Maps the final score (0-4) to specific UI colors:
    - **0 (Red):** Very Weak
    - **1 (Orange):** Weak
    - **2 (Yellow):** Medium
    - **3 (Light Green):** Strong
    - **4 (Cyan):** Excellent

---

## Class Analysis: `PasswordGeneratorGUI.java` (The Interface)

The presentation layer captures user intent and displays results.
* **Reactive Feedback:** As the password is typed or generated, `isPasswordSafe` is called to provide instant visual feedback.
* **Component Connection:** Directly updates `resultField` background colors based on the `Generator` output.

---

## Security Implementations

### Entropy & Patterns
By integrating `zxcvbn4j`, the app detects:
* **Dictionary attacks:** Common words like "admin" or "password".
* **Sequence attacks:** "123456" or "qwerty".
* **Date patterns:** "1995" or "2024".

### Memory & Data Handling
The logic uses `StringBuilder` for password assembly and provides a clear separation between the generation pool and the final output. 

---

## Build & Dependencies
* **JDK:** 25
* **Libraries:** `zxcvbn4j` (for entropy analysis).
* **Randomness:** `SecureRandom` (CSPRNG).

---
*Documentation updated as of the 30th of January 2026.*
