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
* **Note:** If you don't have version **25** or higher, please download it from [Oracle's Official Website](https://www.oracle.com/java/technologies/downloads/).

### 2. Download and Execute
1.  **Download the latest version:** Go to the [Releases](https://github.com/Ceruti-S/passwordGenerator/releases/tag/v1.0.0) page and download `PasswordGenerator.jar`.
2.  **To launch:** Double-click the `.jar` file.
3.  **Alternative (Command Line):**
    ```bash
    java -jar PasswordGenerator.jar
    ```

> [!TIP]
> **Windows Users:** If double-clicking doesn't work, ensure `.jar` files are set to open with "OpenJDK Platform binary".

---

## Source Code & Development

The full source code is available in the **master branch**. I encourage developers and security enthusiasts to explore the implementation.

### 🇮🇹 Language Note
Please note that parts of the source code (comments and some variable names) are written in **Italian**, as the author(me) is Italian. If you are an international developer and find some parts unclear, please refer to the **`documentation.md`** file, which provides a full explanation of the logic in English.

### Design Philosophy: Function over Form
Please note that the Graphical User Interface (GUI) follows a **minimalist approach**. The primary focus of this project was **functionality, security, and algorithmic precision** rather than aesthetic polish. 

I prioritized:
* **Real-time entropy calculation.**
* **Secure memory handling** (preventing sensitive data leaks).
* **Backend reliability and performance.**

### Detailed Documentation
For a deep dive into the internal logic, security protocols, and architecture of the application, please refer to the **`documentation.md`** file included in the repository. It contains a comprehensive breakdown of how the generator operates under the hood, the libraries used (like `zxcvbn4j`), and the entropy logic.

---

## Technical Highlights
* **Entropy Analysis:** Integrated with the `zxcvbn4j` library for realistic strength scoring against modern dictionary attacks.
* **Memory Safety:** Optimized to prevent sensitive data from lingering in the JVM heap unnecessarily.
* **Open Source:** Full transparency is available on the master branch for public audit and contributions.

---

## License & Ethics
This project is licensed under the **GNU General Public License v3.0**. 

**Personal Note:** While the license allows many uses, this software was created for educational and security purposes. **Commercial use or redistribution for profit is strongly discouraged** as a matter of respect for the author's work.

**Disclaimer:** The software is provided **"as is"**, without warranty of any kind, express or implied. The author shall not be liable for any claim, damages, or other liability. However, if you have any ideas, bug reports, or improvements, **suggestions are more than welcome!** Feel free to open an issue or a pull request.

---
*Developed with a focus on security and performance.*
