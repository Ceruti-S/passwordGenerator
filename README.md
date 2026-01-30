# passwordGenerator
# Password Generator

A robust Java-based security tool focused on generating high-entropy passwords with professional-grade strength analysis.

---

## How to Use (Quick Start)

If you want to use the application immediately, follow these steps:

### 1. Requirements Check
This application is built with **Java 25**. You must have the Java Runtime Environment (JRE) or Java Development Kit (JDK) 25 installed to run the file.

* **Open your terminal** (Command Prompt or PowerShell).
* **Run the following command:**
    ```bash
    java -version
    ```
* **Note:** If you don't have version **25** or higher, or if the command is not recognized, please download it from [Oracle's Official Website](https://www.oracle.com/java/technologies/downloads/).

### 2. Download and Execute
1.  Locate the `PasswordGenerator.jar` file in this repository.
2.  **To launch:** Double-click the `.jar` file.
3.  **Alternative (Command Line):** ```bash
    java -jar PasswordGenerator.jar
    ```

---

## 💻 Source Code & Development

The full source code is available in the **master branch**. We encourage developers and security enthusiasts to explore the implementation.

### 🎨 Design Philosophy: Function over Form
Please note that the Graphical User Interface (GUI) follows a **minimalist approach**. The primary focus of this project was **functionality, security, and algorithmic precision** rather than aesthetic polish. 

We prioritized:
* **Real-time entropy calculation.**
* **Secure memory handling** (preventing sensitive data leaks).
* **Backend reliability and performance.**

### 📖 Detailed Documentation
For a deep dive into the internal logic, security protocols, and architecture of the application, please refer to the **`documentation.txt`** file included in the repository. It contains a comprehensive breakdown of how the generator operates under the hood, the libraries used (like `zxcvbn4j`), and the entropy logic.

---

## 🛠 Technical Highlights
* **Entropy Analysis:** Integrated with the `zxcvbn4j` library for realistic strength scoring against modern dictionary attacks.
* **Memory Safety:** Optimized to prevent sensitive data from lingering in the JVM heap unnecessarily.
* **Open Source:** Full transparency is available on the master branch for public audit and contributions.

---
*Developed with a focus on security and performance.*
